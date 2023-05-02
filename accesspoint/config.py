import json
import threading
import requests
import os
from Settings import Settings
import db
import logging

INTERVAL: int = 5  # seconds


def get_config():
    settings = Settings()

    url = f"http://{settings.server_host}:{settings.server_port}/api/setting/{1}"

    try:
        response = requests.get(url, auth=settings.auth)
    except requests.exceptions.ConnectionError:
        logging.error("'api/setting/' API call failed")
        return

    if response.status_code != 200:
        logging.error("'api/setting/' API call failed")
        return

    data = response.json()
    # write to db the config
    database = db.connect_to_db()
    # clear collection config and insert new config
    collection = database["config"]
    collection.delete_many({})
    collection.insert_one(data)

    logging.warning("'api/setting/' API call successful")


# for debug purposes only
if __name__ == "__main__":
    get_config()
