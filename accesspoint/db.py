import datetime

import pymongo
import config
import requests
import os
import json
from Settings import Settings
import logging


def connect_to_db():
    settings = Settings()
    client = pymongo.MongoClient(settings.mongo_host, settings.mongo_port)
    db = client[settings.mongo_database]
    if settings.mongo_database not in client.list_database_names():
        print("Database does not exist. Creating...")
        db = client[settings.mongo_database]
        db.create_collection(settings.mongo_collection)
    if settings.mongo_collection not in db.list_collection_names():
        print("Collection does not exist. Creating...")
        db.create_collection(settings.mongo_collection)

    return db


def write_to_document_sensor(descriptor, value, sensor_type, greenhouse_id):
    settings = Settings()
    db = connect_to_db()
    if db is None:
        print("ERROR: Could not connect to database")
        return
    if settings.mongo_collection not in db.list_collection_names():
        print("Collection does not exist. Creating...")
        db.create_collection(settings.mongo_collection)
    collection = db[settings.mongo_collection]
    #get from config/config.json
    config_collection = db["config"]
    config = config_collection.find_one()
    # for x in config:
    #     if x == "sensorSettings":
    #         for y in config[x]:
    #             if y["sensorType"] == sensor_type:
    #                 print(y)
    #                 if value < y["limitUpper"] or value > y["limitUpper"]:
    #                     logging.error("Value out of range")
    '''
    {
    "greenhouseID": 27,
    "accesspointID": 1,
    "value": 23.0,
    "sensorType": "TEMPERATURE",
    "date": "2023-03-20 12:00"
    }
    '''
    # crete actual time with this format 2023-03-20 12:00
    date = datetime.datetime.now()
    date = date.strftime("%Y-%m-%d %H:%M")

    collection.insert_one({"greenhouseID": greenhouse_id, "accesspointID": config["accessPointId"],
                           "value": value, "sensorType": sensor_type, "date": date})


if __name__ == "__main__":
    write_to_document_sensor("Test", 23.0, "TEMPERATURE", 27)
    # database = connect_to_db()
    # print(database.name)
    # url = "http://10.0.0.62:9000/api/measurements"
    # payload = {
    #     "greenhouseID": 27,
    #     "accesspointUUID": 1,
    #     "value": 23.0,
    #     "sensorType": "TEMPERATURE",
    #     "date": "2023-03-20 12:00",
    # }
    #
    # current_dir = os.path.dirname(os.path.abspath(__file__))
    #
    # sett = Settings()
    #
    # auth = sett.auth
    #
    # headers = {"Content-Type": "application/json"}
    #
    # response = requests.post(url, headers=headers, auth=auth, data=json.dumps(payload))
    #
    # print(response.text)
