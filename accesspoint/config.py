import json
import threading
import requests


def read_settings():
    with open('settings.json', 'r') as data_file:
        data = json.load(data_file)
    return data


INTERVAL: int = 10  # seconds


def get_config():
    settings: json = read_settings()
    admin = settings['auth']['user']
    password = settings['auth']['password']
    ip = settings['server']['host']
    port = settings['server']['port']
    access_point_id = settings['access_point_id']

    url = f"http://{ip}:{port}/api/setting/{access_point_id}"
    auth: tuple = (admin, password)
    response = None
    try:
        response = requests.get(url, auth=auth)
    except requests.exceptions.ConnectionError:
        print("Error: 'api/setting/' API call failed")
        return response.status_code

    if response.status_code != 200:
        print("Error: 'api/setting/' API call failed")
        return response.status_code
    data = response.json()
    with open("config/config.json", "w") as f:
        json.dump(data, f, indent=4)
    threading.Timer(INTERVAL, get_config).start()
    print("INFO: 'api/setting/' API call successful")
    return response.status_code


# for debug purposes only
if __name__ == "__main__":
    # NOTE: put your own IP address, port, admin and password
    get_config()
