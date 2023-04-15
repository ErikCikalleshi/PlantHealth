import requests
import config
import db


def get_avg_measurements(database, settings):
    if database is None:
        print("ERROR: Could not connect to database")
        return
    if settings['mongo']['collection'] not in database.list_collection_names():
        print("Collection does not exist. Nothing to make average from.")
        return

    collection = database[settings['mongo']['collection']]
    # TODO: make average from all measurements grouped by sensor_type
    return None


def send_measurements():
    url = "https://localhost:9000/api/measurements"
    settings = config.read_settings()
    admin = settings['auth']['user']
    password = settings['auth']['password']
    auth = (admin, password)
    database = db.connect_to_db()
    avg_measurements = get_avg_measurements(settings, database)
    if avg_measurements is None:
        print("ERROR: Could not get average measurments")
        return
    response = requests.post(url, auth=auth, json=avg_measurements)
    if response.status_code != 200:
        print(
            "ERROR: Could not send measurements. Not deleting measurements. Status code: " + str(response.status_code))
        return
    # delete collection from db
    if database is None:
        print("ERROR: Could not connect to database")
        return
    if settings['mongo']['collection'] not in database.list_collection_names():
        print("Collection does not exist. Nothing to delete.")
        return
    collection = database[settings['mongo']['collection']]
    collection.drop()



