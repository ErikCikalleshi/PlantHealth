import requests
import config
import db
from Settings import Settings


def get_avg_measurements(database):
    settings = Settings()
    if database is None:
        print("ERROR: Could not connect to database")
        return
    if settings.mongo_collection not in database.list_collection_names():
        print("Collection does not exist. Nothing to make average from.")
        return

    collection = database[settings.mongo_collection]
    # TODO: make average from all measurements grouped by sensor_type
    return None


def send_measurements():
    url = "https://localhost:9000/api/measurements"
    settings = Settings()

    database = db.connect_to_db()
    avg_measurements = get_avg_measurements(database)
    if avg_measurements is None:
        print("ERROR: Could not get average measurments")
        return
    response = requests.post(url, auth=settings.auth, json=avg_measurements)
    if response.status_code != 200:
        print(
            "ERROR: Could not send measurements. Not deleting measurements. Status code: " + str(response.status_code))
        return
    # delete collection from db
    if database is None:
        print("ERROR: Could not connect to database")
        return
    if settings.mongo_collection not in database.list_collection_names():
        print("Collection does not exist. Nothing to delete.")
        return
    collection = database[settings.mongo_collection]
    collection.drop()



