import json
import threading
import requests
import os


def read_settings(file_path: str) -> dict:
    if not os.path.exists(file_path):
        os.makedirs(file_path, exist_ok=True)
        file_path = os.path.join(file_path, "settings.json")
        with open(file_path, "w") as f:
            json.dump({}, f)
    with open(file_path, "r") as data_file:
        data = json.load(data_file)
    return data


INTERVAL: int = 1  # seconds


def get_config(script_path):
    current_dir = os.path.dirname(os.path.abspath(script_path))
    sett = os.path.join(current_dir, "settings.json")
    settings = read_settings(sett)
    admin = settings["auth"]["user"]
    password = settings["auth"]["password"]
    ip = settings["server"]["host"]
    port = settings["server"]["port"]
    access_point_id = settings["access_point_id"]

    url = f"http://{ip}:{port}/api/setting/{access_point_id}"
    auth: tuple = (admin, password)
    response = None
    try:
        response = requests.get(url, auth=auth)
    except requests.exceptions.ConnectionError:
        print("Error: 'api/setting/' API call failed")
        return

    # if response.status_code != 200:
    #     print("Error: 'api/setting/' API call failed")
    #     return response.status_code
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
    script_path = os.path.abspath(__file__)
    get_config(script_path)
