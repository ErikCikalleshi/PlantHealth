import pymongo
import config
import requests
import os
import json
from Settings import Settings


def connect_to_db():
    settings = Settings()
    client = pymongo.MongoClient(settings.mongo_host, settings.mongo_port)
    db = client[settings.mongo_database]
    if settings.mongo_database not in client.list_database_names():
        print("Database does not exist. Creating...")
        db = client[settings.mongo_database]
        db.create_collection(settings.mongo_collection)
    return db


def write_to_document(descriptor, value):
    settings = Settings()
    db = connect_to_db()
    if db is None:
        print("ERROR: Could not connect to database")
        return
    if settings.mongo_collection not in db.list_collection_names():
        print("Collection does not exist. Creating...")
        db.create_collection(settings.mongo_collection)
    collection = db[descriptor]
    # TODO: check if limits are exceeded
    # ___
    collection.insert_one(value)


if __name__ == "__main__":
    # database = connect_to_db()
    # print(database.name)
    url = "http://10.0.0.62:9000/api/measurements"
    payload = {
        "greenhouseID": 27,
        "accesspointUUID": 1,
        "value": 23.0,
        "sensorType": "TEMPERATURE",
        "date": "2023-03-20 12:00",
    }

    current_dir = os.path.dirname(os.path.abspath(__file__))

    sett = Settings()

    auth = sett.auth

    headers = {"Content-Type": "application/json"}

    response = requests.post(url, headers=headers, auth=auth, data=json.dumps(payload))

    print(response.text)
