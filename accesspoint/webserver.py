import asyncio
import datetime
import json

from log_config import AuditLogger

logging = AuditLogger()
import threading

import pandas as pd
import requests

import db
from Settings import Settings
from control_services_arduino import send_flag

collection_deletion_event = asyncio.Event()


async def get_avg_measurements(database):
    settings = Settings()
    if database is None:
        logging.error("Could not connect to database")
        return

    if settings.mongo_collection not in database.list_collection_names():
        logging.warning("Collection does not exist. Nothing to make average from.")
        return

    collection = database[settings.mongo_collection]

    data = list(collection.find())
    if len(data) == 0:
        logging.warning("Collection is empty. Nothing to make average from.")
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
            limit = (sensors_greenhouse[sensors_greenhouse["sensorType"] == sensor_type]["limitUpper"].iloc[0],
                     sensors_greenhouse[sensors_greenhouse["sensorType"] == sensor_type]["limitLower"].iloc[0])

            limit_exceeded_by = 0
            sensor_blink_mappings = {
                "TEMPERATURE": 3,
                "HUMIDITY_AIR": 4,
                "AIR_PRESSURE": 5,
                "AIR_QUALITY": 6,
                "LIGHT": 1,
                "HUMIDITY_DIRT": 2,
            }
            thresholdSeconds = \
                sensors_greenhouse[sensors_greenhouse["sensorType"] == sensor_type]["limitThresholdMinutes"].iloc[
                    0] * 60
            if avg > limit[0]:
                # Start timer
                async def timer_callback():
                    await asyncio.sleep(5)  # TODO: Change to thresholdSeconds
                    # Re-check the limit after the timer is finished
                    avg_measurements = await get_avg_measurements(database)
                    if avg_measurements is not None:
                        for avg_measurement in avg_measurements:
                            if avg_measurement["sensorType"] == sensor_type and avg_measurement["greenhouseID"] == int(
                                    subset["greenhouseID"].iloc[0]):
                                avg2 = avg_measurement["avg"]
                                break
                        # Check if limit is still exceeded
                        if avg2 > limit[0]:
                            logging.info(
                                "Limit still exceeded. Sending " + (128 + sensor_blink_mappings[sensor_type]) + " flag")
                            await send_flag("SensorStation " + str(greenhouse),
                                            128 + sensor_blink_mappings[sensor_type], "led_flag")

                asyncio.create_task(timer_callback())
                logging.info(sensor_type + ": Upper Limit exceeded by: " + str(limit_exceeded_by))
                limit_exceeded_by = avg - limit[0]

            elif avg < limit[1]:
                async def timer_callback2():
                    await asyncio.sleep(5)  # TODO: Change to tresholdSeconds
                    # Re-check the limit after the timer is finished
                    avg_measurements = await get_avg_measurements(database)
                    if avg_measurements is not None:
                        # Check if limit is still exceeded
                        for avg_measurement in avg_measurements:
                            if avg_measurement["sensorType"] == sensor_type and avg_measurement["greenhouseID"] == int(
                                    subset["greenhouseID"].iloc[0]):
                                avg2 = avg_measurement["avg"]
                                break
                        if avg2 > limit[0]:
                            logging.info(
                                "Limit still exceeded. Sending " + (128 + sensor_blink_mappings[sensor_type]) + " flag")
                            await send_flag("SensorStation " + str(greenhouse), sensor_blink_mappings[sensor_type],
                                            "led_flag")

                asyncio.create_task(timer_callback2())
                limit_exceeded_by = limit[1] - avg
                await send_flag("SensorStation " + str(greenhouse),  sensor_blink_mappings[sensor_type])
                logging.info(sensor_type + ": Lower Limit exceeded by: " + str(limit_exceeded_by))

            date = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")
            data = {"greenhouseID": int(subset["greenhouseID"].iloc[0]),
                    "accesspointUUID": int(subset["accesspointID"].iloc[0]),
                    "value": avg,
                    "sensorType": sensor_type,
                    "date": date,
                    "limitExceededBy": limit_exceeded_by,
                    }

            json_arrays.append(json.dumps(data))
    logging.info("Average measurements created successfully")
    return json_arrays


async def send_measurements():
    settings = Settings()
    print("send measurements")
    url = f"http://{settings.server_host}:{settings.server_port}/api/measurements"
    database = await db.connect_to_db()
    # get transmissionIntervalSeconds from config
    config_collection = database["config"]
    config = config_collection.find_one()
    global INTERVAL
    INTERVAL = config["transmissionIntervalSeconds"]

    avg_measurements = await get_avg_measurements(database)
    if avg_measurements is None:
        logging.warning("Could not get average measurements")
        return

    auth = settings.auth

    headers = {"Content-Type": "application/json"}
    for avg_measurement in avg_measurements:
        response = requests.post(url, headers=headers, auth=auth, data=avg_measurement)
        if response.status_code == 200:
            logging.info("Measurements sent successfully to backend")

    # delete collection from db
    if database is None:
        logging.error("Could not connect to database")
        return
    if settings.mongo_collection not in database.list_collection_names():
        logging.warning("Collection does not exist. Nothing to delete.")
        return
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
            await send_measurements()  # Starts the read config task
            await asyncio.sleep(INTERVAL)  # Pause for 10 seconds using asyncio.sleep
        except Exception as e:
            print(e)
            logging.error(f"An error occurred while reading config: {e}, restarting task...")
            await asyncio.sleep(INTERVAL)  # Wait for 10 seconds before restarting the task


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
    send_measurements()
