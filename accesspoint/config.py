import json
import threading
import requests
import os
from Settings import Settings
import db

INTERVAL: int = 20  # seconds


def get_config(script_path):
    settings = Settings()

    url = f"http://{settings.server_host}:{settings.server_port}/api/setting/{1}"
    response = None
    try:
        response = requests.get(url, auth=settings.auth)
    except requests.exceptions.ConnectionError:
        print("Error: 'api/setting/' API call failed")
        return

    if response.status_code != 200:
        print("Error: 'api/setting/' API call failed")
        return

    data = response.json()
    # write to db the config
    database = db.connect_to_db()
    # clear collection config
    collection = database["config"]
    collection.drop()
    # insert new config
    collection.insert_one(data)

    threading.Timer(INTERVAL, get_config, args=[script_path]).start()
    print("INFO: 'api/setting/' API call successful")


# for debug purposes only
if __name__ == "__main__":
    # NOTE: put your own IP address, port, admin and password
    test_script_path = os.path.abspath(__file__)
    get_config(test_script_path)
