import datetime

import pymongo
import config
import requests
import os
import json
from Settings import Settings
import logging
import pandas as pd


def connect_to_db():
    settings = Settings()
    client = pymongo.MongoClient(settings.mongo_host, settings.mongo_port)
    db = client[settings.mongo_database]
    if settings.mongo_database not in client.list_database_names():
        logging.warning("Database does not exist. Creating...")
        db = client[settings.mongo_database]
        db.create_collection(settings.mongo_collection)
    if settings.mongo_collection not in db.list_collection_names():
        logging.warning("Collection does not exist. Creating...")
        db.create_collection(settings.mongo_collection)

    return db


def write_to_document_sensor(descriptor, value, sensor_type, greenhouse_id):
    settings = Settings()
    db = connect_to_db()
    if db is None:
        logging.error("Could not connect to database")
        return
    if settings.mongo_collection not in db.list_collection_names():
        logging.warning("Collection does not exist. Creating...")
        db.create_collection(settings.mongo_collection)
    collection = db[settings.mongo_collection]
    # get from config/config.json
    config_collection = db["config"]
    config = config_collection.find_one()

    date = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")

    collection.insert_one({"greenhouseID": greenhouse_id, "accesspointID": config["accessPointId"],
                           "value": value, "sensorType": sensor_type, "date": date})


if __name__ == "__main__":
    # generate random data
    import random

    for i in range(100):
        write_to_document_sensor("test", random.randint(20, 36), "LIGHT", 27)
        write_to_document_sensor("test", random.randint(20, 21), "TEMPERATURE", 27)

