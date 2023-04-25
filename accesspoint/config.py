import json
import threading
import requests
import os
from Settings import Settings

INTERVAL: int = 1  # seconds


def get_config(script_path):
    settings = Settings()

    url = f"http://{settings.server_host}:{settings.server_port}/api/setting/{1}"
    response = None
    try:
        response = requests.get(url, auth=settings.auth)
    except requests.exceptions.ConnectionError:
        print("Error: 'api/setting/' API call failed")
        return

    data = response.json()
    current_dir = os.path.dirname(os.path.abspath(script_path))
    config_dir_path = os.path.join(current_dir, "config")
    config_file_path = os.path.join(config_dir_path, "config.json")

    if not os.path.exists(config_dir_path):
        os.makedirs(config_dir_path)

    with open(config_file_path, "w+") as f:
        json.dump(data, f, indent=4)
    threading.Timer(INTERVAL, get_config, args=[script_path]).start()
    print("INFO: 'api/setting/' API call successful")


# for debug purposes only
if __name__ == "__main__":
    # NOTE: put your own IP address, port, admin and password
    test_script_path = os.path.abspath(__file__)
    get_config(test_script_path)
