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
            if avg > limit[0]:
                print("Test" + sensor_type)
                limit_exceeded_by = avg - limit[0]
                # loop = asyncio.get_event_loop()  # returns the event loop object associated with the current thread

                await send_flag("SensorStation " + str(greenhouse), 128 + sensor_blink_mappings[sensor_type])

                logging.error("Upper Limit exceeded by: " + str(limit_exceeded_by))
            elif avg < limit[1]:
                print("Test" + sensor_type)
                limit_exceeded_by = limit[1] - avg
                await send_flag("SensorStation " + str(greenhouse),  sensor_blink_mappings[sensor_type])
                logging.error("Lower Limit exceeded by: " + str(limit_exceeded_by))

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
    print("dataBaseeeeeeeeeeeeeeeeeeeeeee")
    # get transmissionIntervalSeconds from config
    config_collection = database["config"]
    config = config_collection.find_one()
    # DEBUGGING: change transmissionIntervalSeconds to 4
    config["transmissionIntervalSeconds"] = 11

    avg_measurements = await get_avg_measurements(database)
    if avg_measurements is None:
        logging.warning("Could not get average measurements")
        # threading.Timer(config["transmissionIntervalSeconds"], send_measurements).start()
        return

    auth = settings.auth

    headers = {"Content-Type": "application/json"}
    for avg_measurement in avg_measurements:
        print(avg_measurement)
        response = requests.post(url, headers=headers, auth=auth, data=avg_measurement)
        if response.status_code == 200:
            print("Measurements sent successfully")

    # delete collection from db
    if database is None:
        logging.error("Could not connect to database")
        # threading.Timer(config["transmissionIntervalSeconds"], send_measurements).start()
        return
    if settings.mongo_collection not in database.list_collection_names():
        logging.warning("Collection does not exist. Nothing to delete.")
        # threading.Timer(config["transmissionIntervalSeconds"], send_measurements).start()
        return
    collection = database[settings.mongo_collection]

    # empty collection
    collection_deletion_event.set()
    collection.delete_many({})
    collection_deletion_event.clear()
    logging.warning("Collection with measurements deleted successfully")
    # threading.Timer(config["transmissionIntervalSeconds"], send_measurements).start()
    print("Collection deleted successfully. Timer started.")


async def button_disabled_pressed(greenhouse_id: int):
    settings = Settings()
    url = f"http://{settings.server_host}:{settings.server_port}/api/setting/{1}"
    auth = settings.auth
    headers = {"Content-Type": "application/json"}
    response = requests.post(url, headers=headers, auth=auth, data=json.dumps({"greenhouseID": greenhouse_id}))


if __name__ == "__main__":
    send_measurements()
