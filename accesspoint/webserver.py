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
from threading import Timer

collection_deletion_event = asyncio.Event()

INTERVAL = None
# t = None  # Initialize the 't' variable
#
#
# def api_call():
#     global t
#     t = None  # Reset the timer variable
#
#
# def newTimer():
#     global t
#     t = Timer(10.0, api_call)


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
                # if t is None:  # Check if t is None before calling cancel()
                #     newTimer()
                # else:
                #     t.cancel()  # Cancel the existing timer
                #     newTimer()
                # t.start()
                # print("start" + sensor_type)
                # t.join()  # Block until the timer has completed

                # Check if the timer is still None, indicating no limit was exceeded during the block
                # if t is not None:
                limit_exceeded_by = avg - limit[0]
                await send_flag("SensorStation " + str(greenhouse), 128 + sensor_blink_mappings[sensor_type],
                                "led_flag")
                logging.error("Upper Limit exceeded by: " + str(limit_exceeded_by))
                logging.info(sensor_type + ": Upper Limit exceeded by: " + str(limit_exceeded_by))
            elif avg < limit[1]:
                # if t is None:  # Check if t is None before calling cancel()
                #     newTimer()
                # else:
                #     t.cancel()  # Cancel the existing timer
                #     newTimer()
                # t.start()
                # print("start" + sensor_type)
                # t.join()  # Block until the timer has completed
                #
                # # Check if the timer is still None, indicating no limit was exceeded during the block
                # if t is not None:
                limit_exceeded_by = limit[1] - avg
                await send_flag("SensorStation " + str(greenhouse), sensor_blink_mappings[sensor_type], "led_flag")
                logging.error("Lower Limit exceeded by: " + str(limit_exceeded_by))
                logging.info(sensor_type + ": Lower Limit exceeded by: " + str(limit_exceeded_by))

            # if t is not None:
            #     t.cancel()
            #     print("cancel" + sensor_type)

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
    url = f"http://{settings.server_host}:{settings.server_port}/api/measurements"
    database = await db.connect_to_db()

    config_collection = database["config"]
    config = config_collection.find_one()
    global INTERVAL
    #INTERVAL = config["transmissionIntervalSeconds"]
    INTERVAL = 10
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
