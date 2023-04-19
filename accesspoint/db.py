import pymongo
import config
import requests


def connect_to_db():
    settings = config.read_settings()
    client = pymongo.MongoClient(settings["mongo"]["host"], settings["mongo"]["port"])
    db = client[settings["mongo"]["database"]]
    if settings["mongo"]["database"] not in client.list_database_names():
        print("Database does not exist. Creating...")
        db = client[settings["mongo"]["database"]]
        db.create_collection(settings["mongo"]["collection"])
    return db


def write_to_document(descriptor, value):
    settings = config.read_settings()
    db = connect_to_db()
    if db is None:
        print("ERROR: Could not connect to database")
        return
    if settings["mongo"]["collection"] not in db.list_collection_names():
        print("Collection does not exist. Creating...")
        db.create_collection(settings["mongo"]["collection"])
    collection = db[descriptor]
    # TODO: check if limits are exceeded
    # ___
    collection.insert_one(value)


if __name__ == "__main__":
    print("Test")
    database = connect_to_db()
    print(database.name)
    url = "https://10.0.0.62:9000/api/measurements"
    body = {
        "greenhouseID": 27,
        "accesspointUUID": 1,
        "value": 23.0,
        "sensorType": "TEMPERATURE",
        "date": "2023-03-20 12:00",
    }
    settings = config.read_settings()
    admin = settings["auth"]["user"]
    password = settings["auth"]["password"]
    auth = (admin, password)
    response = requests.request("POST", url, auth=auth, data=body)

    print(response.text)
