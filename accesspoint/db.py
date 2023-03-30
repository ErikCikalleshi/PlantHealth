import pymongo
import json


def read_settings():
    with open('settings.json', 'r') as data_file:
        data = json.load(data_file)
    return data


def connect_to_db():
    settings = read_settings()
    client = pymongo.MongoClient(settings['mongo']['host'], settings['mongo']['port'])
    db = None
    print(client.list_database_names())
    if settings['mongo']['database'] not in client.list_database_names():
        print("Database does not exist. Creating...")
        db = client[settings['mongo']['database']]
        db.create_collection(settings['mongo']['collection'])
    return db


if __name__ == "__main__":
    database = connect_to_db()
    pass
