import asyncio
import datetime
import json

from accesspoint import db
from accesspoint.Settings import Settings
from accesspoint.control_services_arduino import send_flag
from accesspoint.log_config import AuditLogger

logging = AuditLogger()

import pandas as pd
import requests

collection_deletion_event = asyncio.Event()

INTERVAL = None


async def get_avg_measurements(database):
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
            sensor_blink_mappings = {
                "TEMPERATURE": 3,
                "HUMIDITY_AIR": 4,
                "AIR_PRESSURE": 5,
                "AIR_QUALITY": 6,
                "LIGHT": 1,
                "HUMIDITY_DIRT": 2,
            }
            if avg > limit[0]:
                # check if seconds_timer_upper is not None
                if "seconds_timer_upper" in subset.columns and subset["seconds_timer_upper"].iloc[0] is not None:
                    # get thresholdMinutes from sensors_greenhouse
                    threshold_minutes = \
                        sensors_greenhouse[sensors_greenhouse["sensorType"] == sensor_type][
                            "limitThresholdMinutes"].iloc[0]
                    # check if the time from the minutues of seconds_timer_upper is bigger than threshold_minutes
                    if (datetime.datetime.now() - datetime.datetime.strptime(
                            subset["seconds_timer_upper"].iloc[0],
                            "%Y-%m-%d %H:%M:%S")).total_seconds() / 60 > threshold_minutes:
                        await send_flag("SensorStation " + str(greenhouse), 128 + sensor_blink_mappings[sensor_type],
                                        "led_flag")
                        subset.loc[:, "seconds_timer_upper"] = None
                # add new column to df on the sensor_type based on the id with the time now
                subset.loc[:, "seconds_timer_upper"] = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                limit_exceeded_by = avg - limit[0]
                logging.error("Upper Limit exceeded by: " + str(limit_exceeded_by))
                logging.info(sensor_type + ": Upper Limit exceeded by: " + str(limit_exceeded_by))
            elif avg < limit[1]:
                # check if seconds_timer_lower is not None
                if "seconds_timer_lower" in subset.columns and subset["seconds_timer_lower"].iloc[0] is not None:
                    # get thresholdMinutes from sensors_greenhouse
                    threshold_minutes = \
                        sensors_greenhouse[sensors_greenhouse["sensorType"] == sensor_type][
                            "limitThresholdMinutes"].iloc[0]
                    # check if the time from the minutues of seconds_timer_lower is bigger than threshold_minutes
                    if (datetime.datetime.now() - datetime.datetime.strptime(
                            subset["seconds_timer_lower"].iloc[0],
                            "%Y-%m-%d %H:%M:%S")).total_seconds() / 60 > threshold_minutes:
                        await send_flag("SensorStation " + str(greenhouse), sensor_blink_mappings[sensor_type],
                                        "led_flag")
                        subset.loc[:, "seconds_timer_lower"] = None
                # add new column to df on the sensor_type based on the id with the time now
                subset.loc[:, "seconds_timer_lower"] = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                limit_exceeded_by = limit[1] - avg
                logging.error("Lower Limit exceeded by: " + str(limit_exceeded_by))
                logging.info(sensor_type + ": Lower Limit exceeded by: " + str(limit_exceeded_by))

            date = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f")
            data = {"greenhouseID": int(subset["greenhouseID"].iloc[0]),
                    "accesspointUUID": int(subset["accesspointID"].iloc[0]),
                    "value": avg,
                    "sensorType": sensor_type,
                    "date": date,
                    "limitExceededBy": limit_exceeded_by,
                    "upperLimit": 34.8,
                    "lowerLimit": 25.8,
                    }

            json_arrays.append(json.dumps(data))

    logging.info("Average measurements created successfully")
    return json_arrays


async def send_post_request(data):
    settings = Settings()
    auth = settings.auth
    headers = {"Content-Type": "application/json"}
    url = f"http://{settings.server_host}:{settings.server_port}/api/measurements"
    response = requests.post(url, headers=headers, auth=auth, data=data)
    if response.status_code == 200:
        logging.info("Measurements sent successfully to backend")
    else:
        logging.warning("Could not send measurements to backend")
    return response.status_code


async def send_measurements() -> int:
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
            print(e)
            logging.error(f"An error occurred while reading config: {e}, restarting the task...")
            await asyncio.sleep(INTERVAL)


async def button_disabled_pressed(greenhouse_id: int):
    settings = Settings()
    url = f"http://{settings.server_host}:{settings.server_port}/api/disabled"
    auth = settings.auth
    headers = {"Content-Type": "application/json"}
    payload = {"greenhouse": greenhouse_id}
    response = requests.post(url, headers=headers, auth=auth, json=payload)
    if response.status_code == 200:
        logging.info("Button disabled pressed successfully")
    else:
        logging.error("Button disabled pressed failed")


if __name__ == "__main__":
    asyncio.run(send_measurements())
