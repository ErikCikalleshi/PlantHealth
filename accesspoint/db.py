import datetime
import sys

import pymongo

from Settings import Settings
from log_config import AuditLogger

logging = AuditLogger()


async def connect_to_db():
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
    logging.info("Connected to database")
    return db


async def write_to_document_sensor(value, sensor_type, greenhouse_id):
    settings = Settings()
    db = await connect_to_db()
    if db is None:
        logging.error("Could not connect to database")
        return
    if settings.mongo_collection not in db.list_collection_names():
        logging.warning("Collection does not exist. Creating...")
        db.create_collection(settings.mongo_collection)
        logging.warning("Collection created successfully")
    collection = db[settings.mongo_collection]
    # get from config/config.json
    config_collection = db["config"]
    config = config_collection.find_one()
    if config is None:
        logging.warning("Could not find config in database")
        return
    logging.warning("Writing to database...")

    date = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")

    collection.insert_one({"greenhouseID": greenhouse_id, "accesspointID": config["accessPointId"],
                           "value": value, "sensorType": sensor_type, "date": date})
    logging.info("Successfully written to database")


if __name__ == "__main__":
    # generate random data
    import random
    for i in range(100):
        write_to_document_sensor(random.randint(20, 36), "LIGHT", 27)
        write_to_document_sensor(random.randint(20, 21), "TEMPERATURE", 27)

