import asyncio
import datetime
import sys

import pymongo

from accesspoint.Settings import Settings
from accesspoint.log_config import AuditLogger

logging = AuditLogger()


async def connect_to_db():
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
    logging.info("Connected to database")
    return db


async def get_config(db):
    config_collection = db["config"]
    config = config_collection.find_one()
    if config is None:
        logging.error("Could not find config in database")
        return None
    return config


async def insert_document(db, collection_name, document):
    collection = db[collection_name]
    date = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")
    document["date"] = date
    collection.insert_one(document)
    logging.info("Successfully written to database")


async def write_to_document_sensor(value, sensor_type, greenhouse_id):
    db = await connect_to_db()
    if db is None:
        logging.error("Could not connect to database")
        return
    config = await get_config(db)
    if config is None:
        return
    document = {
        "greenhouseID": greenhouse_id,
        "accesspointID": config["accessPointId"],
        "value": value,
        "sensorType": sensor_type,
    }
    logging.info("Writing to database...")
    await insert_document(db, config.mongo_collection, document)
    logging.info("Successfully written to database")

# async def main():
#     import random
#     for i in range(100):
#         await write_to_document_sensor(random.randint(20, 36), "LIGHT", 27)
#         await write_to_document_sensor(random.randint(20, 21), "TEMPERATURE", 27)


# asyncio.run(main())

