import asyncio
import datetime
import json

import db
from Settings import Settings

from auditlog_config import AuditLogger

logging = AuditLogger()

import pandas as pd
import requests

collection_deletion_event = asyncio.Event()

INTERVAL = None

sensor_exceeded_date = {}


async def handle_limit_exceeded(subset, sensor_type, greenhouse, sensors_greenhouse, type_limit):
    """
     Handles the case when a sensor value exceeds the upper or lower limit.

     This function handles the behavior when a sensor value exceeds the upper or lower limit for a given sensor type
     and greenhouse. It performs the necessary actions such as sending flags to the SensorStation.

     Args:
         subset (DataFrame): Subset of measurements for a specific sensor type and greenhouse.
         sensor_type (str): Type of the sensor.
         greenhouse (int): ID of the greenhouse.
         sensors_greenhouse (DataFrame): DataFrame containing sensor configurations for the greenhouse.
         type_limit (str): Type of limit exceeded ("seconds_timer_upper" or "seconds_timer_lower").

     Returns:
         DataFrame: Updated subset of measurements after handling the limit exceeded case.

     """
    from control_services_arduino import send_flag
    # deep copy of the subset
    subset = subset.copy()
    sensor_blink_mappings = {
        "TEMPERATURE": 3,
        "HUMIDITY_AIR": 4,
        "AIR_PRESSURE": 5,
        "AIR_QUALITY": 6,
        "LIGHT": 1,
        "HUMIDITY_DIRT": 2,
    }

    if sensor_type not in sensor_exceeded_date:
        sensor_exceeded_date.update({sensor_type: datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")})

    # check if seconds_timer_upper is not None
    if sensor_type in sensor_exceeded_date:
        # get thresholdMinutes from sensors_greenhouse
        threshold_minutes = \
            sensors_greenhouse[sensors_greenhouse["sensorType"] == sensor_type][
                "limitThresholdMinutes"].iloc[0]
        # check if the time from the minutes of seconds_timer_upper is bigger than threshold_minutes

        if (datetime.datetime.now() - datetime.datetime.strptime(
                sensor_exceeded_date[sensor_type],
                "%Y-%m-%d %H:%M:%S")).total_seconds() / 60 > threshold_minutes:
            if type_limit == "seconds_timer_upper":
                await send_flag("SensorStation " + str(greenhouse), 128 + sensor_blink_mappings[sensor_type],
                                "led_flag")

            elif type_limit == "seconds_timer_lower":
                await send_flag("SensorStation " + str(greenhouse), sensor_blink_mappings[sensor_type], "led_flag")
            sensor_exceeded_date.pop(sensor_type)
    return subset


async def get_avg_measurements(database):
    """
    Retrieves average measurements for each sensor type and greenhouse from the database.

    This function retrieves average measurements for each sensor type and greenhouse from the specified database.
    It calculates the average, checks for limit exceeded, and prepares the data for sending to the backend server.

    Args:
        database: The MongoDB database object.

    Returns:
        list: List of JSON-encoded measurement data.

    """
    settings = Settings()
    if database is None:
        logging.error("Could not connect to the database")
        return

    if settings.mongo_collection not in database.list_collection_names():
        logging.warning("Collection does not exist. Nothing to make an average from.")
        return

    collection = database[settings.mongo_collection]

    data = list(collection.find())
    if len(data) == 0:
        logging.warning("Collection is empty. Nothing to make an average from.")
        return

    df = pd.DataFrame(data)

    sensor_types = df['sensorType'].unique()

    json_arrays = []

    for sensor_type in sensor_types:
        subset = df[df['sensorType'] == sensor_type]
        # iterate over all greenhouses
        greenhouses = subset['greenhouseID'].unique()
        for greenhouse in greenhouses:
            subset = df[(df['sensorType'] == sensor_type) & (df['greenhouseID'] == greenhouse)]
            avg = float(subset['value'].mean())
            config = database["config"].find_one()
            data = pd.DataFrame(config["greenhouses"])
            sensors_greenhouse = pd.DataFrame(
                pd.DataFrame(data[data["id"] == greenhouse]["sensors"]).iloc[0]['sensors'])
            limit = (
                sensors_greenhouse[sensors_greenhouse["sensorType"] == sensor_type]["limitUpper"].iloc[0],
                sensors_greenhouse[sensors_greenhouse["sensorType"] == sensor_type]["limitLower"].iloc[0]
            )

            limit_exceeded_by = 0

            if avg > limit[0]:
                subset = await handle_limit_exceeded(subset, sensor_type, greenhouse, sensors_greenhouse,
                                                     "seconds_timer_upper")
                limit_exceeded_by = avg - limit[0]
                logging.info(sensor_type + ": Upper Limit exceeded by: " + str(limit_exceeded_by))

            elif avg < limit[1]:
                subset = await handle_limit_exceeded(subset, sensor_type, greenhouse, sensors_greenhouse,
                                                     "seconds_timer_lower")
                limit_exceeded_by = limit[1] - avg
                logging.info(sensor_type + ": Lower Limit exceeded by: " + str(limit_exceeded_by))

            date = datetime.datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%S.%f")[:-3] + "Z"

            data = {"greenhouseID": int(subset["greenhouseID"].iloc[0]),
                    "accesspointUUID": int(subset["accesspointID"].iloc[0]),
                    "value": avg,
                    "sensorType": sensor_type,
                    "date": date,
                    "limitExceededBy": limit_exceeded_by,
                    "upperLimit": limit[0],
                    "lowerLimit": limit[1],
                    }

            json_arrays.append(json.dumps(data))

    logging.info("Average measurements created successfully")
    return json_arrays


async def send_post_request(data):
    """
    Sends a POST request to the backend server to submit the measurements.

    This function sends a POST request to the specified backend server to submit the measurements data.

    Args:
        data (str): JSON-encoded measurement data.

    Returns:
        int: HTTP status code of the response.

    """
    settings = Settings()
    auth = settings.auth
    headers = {"Content-Type": "application/json"}
    try:
        url = f"http://{settings.server_host}:{settings.server_port}/api/measurements"
        response = requests.post(url, headers=headers, auth=auth, data=data)
    except Exception as e:
        logging.error("Could not send measurements to backend " + str(e))
        return -1

    if response.status_code == 200:
        logging.info("Measurements sent successfully to backend")

    return response.status_code


async def send_measurements() -> int:
    """
    Sends measurements data to the backend server.

    This function retrieves average measurements, sends them to the backend server,
    and deletes the collection of measurements from the database.

    Returns:
        int: HTTP status code of the response.

    """
    settings = Settings()

    database = await db.connect_to_db()

    config_collection = database["config"]

    global INTERVAL
    # INTERVAL = config["transmissionIntervalSeconds"]
    INTERVAL = 10
    avg_measurements = await get_avg_measurements(database)
    if avg_measurements is None:
        logging.warning("Could not get average measurements")
        return -1

    for avg_measurement in avg_measurements:
        await send_post_request(avg_measurement)

    # delete collection from db
    if database is None:
        logging.error("Could not connect to database")
        return -1
    if settings.mongo_collection not in database.list_collection_names():
        logging.warning("Collection does not exist. Nothing to delete.")
        return -1
    collection = database[settings.mongo_collection]

    # empty collection
    collection_deletion_event.set()
    collection.delete_many({})
    collection_deletion_event.clear()
    logging.info("Collection with measurements deleted successfully")


async def send_measurements_task():
    global INTERVAL
    while True:
        try:
            await send_measurements()
            await asyncio.sleep(INTERVAL)
        except Exception as e:
            logging.error(f"An error occurred while sending_measurements: {e}, restarting the task...")
            await asyncio.sleep(INTERVAL)


async def button_disabled_pressed(greenhouse_id: int):
    """
    Sends a POST request to the backend server to indicate a button disabled press.

    This function sends a POST request to the backend server to indicate that the button for disabling a greenhouse
    has been pressed.

    Args:
        greenhouse_id (int): ID of the greenhouse.

    """
    settings = Settings()
    url = f"http://{settings.server_host}:{settings.server_port}/disabled"
    auth = settings.auth
    headers = {"Content-Type": "application/json"}
    payload = {"greenhouse": greenhouse_id}
    response = requests.post(url, headers=headers, auth=auth, json=payload)
    if response.status_code == 200:
        print("Button disabled pressed successfully")
        logging.info("Button disabled pressed successfully")
    else:
        print("Button disabled pressed pressed failed")
        logging.error("Button disabled pressed failed")


if __name__ == "__main__":
    asyncio.run(button_disabled_pressed(2))
