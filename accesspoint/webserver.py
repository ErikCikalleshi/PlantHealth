import json
import logging
import requests
import config
import db
from Settings import Settings
import pandas as pd


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
    avg = df.groupby('sensorType')['value'].mean()

    print(avg)

    return avg.to_dict()


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
    response = requests.post(url, headers=headers, auth=settings.auth, json=avg_measurements)
    if response.status_code == 200:
        logging.info("Measurements sent successfully")

    # delete collection from db
    if database is None:
        logging.error("Could not connect to database")
        return
    if settings.mongo_collection not in database.list_collection_names():
        logging.warning("Collection does not exist. Nothing to delete.")
        return
    collection = database[settings.mongo_collection]
    collection.drop()


if __name__ == "__main__":
    send_measurements()
