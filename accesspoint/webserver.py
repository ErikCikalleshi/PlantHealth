import logging
import requests
import threading
import db
from Settings import Settings
import pandas as pd
import json
import datetime


def get_avg_measurements(database):
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

            date = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")
            data = {"greenhouseID": int(subset["greenhouseID"].iloc[0]),
                    "accesspointUUID": int(subset["accesspointID"].iloc[0]),
                    "value": avg,
                    "sensorType": sensor_type,
                    "date": date}
            print(data)
            json_arrays.append(json.dumps(data))
    return data


def send_measurements():
    url = "http://localhost:9000/api/measurements"
    settings = Settings()

    database = db.connect_to_db()
    # get transmissionIntervalSeconds from config
    config_collection = database["config"]
    config = config_collection.find_one()
    # DEBUGGING: change transmissionIntervalSeconds to 4
    config["transmissionIntervalSeconds"] = 4

    avg_measurements = get_avg_measurements(database)
    if avg_measurements is None:
        logging.warning("Could not get average measurements")
        return
    headers = {"Content-Type": "application/json"}

    auth = settings.auth

    headers = {"Content-Type": "application/json"}

    response = requests.post(url, headers=headers, auth=auth, data=json.dumps(avg_measurements))

    print(response.status_code)
    if response.status_code == 200:
        logging.warning("Measurements sent successfully")

    # delete collection from db
    if database is None:
        logging.error("Could not connect to database")
        return
    if settings.mongo_collection not in database.list_collection_names():
        logging.warning("Collection does not exist. Nothing to delete.")
        return
    collection = database[settings.mongo_collection]
    # empty collection
    # collection.drop()
    # threading.Timer(config["transmissionIntervalSeconds"], send_measurements).start()
    logging.warning("Collection deleted successfully. Timer started.")


if __name__ == "__main__":
    send_measurements()
