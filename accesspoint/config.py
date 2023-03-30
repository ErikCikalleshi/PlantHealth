import json
import threading
import requests


def get_config(ip: str, port: str, admin: str, password: str, accesspoint_id: int):
    url = f"http://{ip}:{port}/api/setting/{accesspoint_id}"
    auth = (admin, password)
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
    threading.Timer(10, get_config).start()
    return response.status_code


# for debug purposes only
if __name__ == "__main__":
    # NOTE: put your own IP address, port, admin and password
    get_config("10.0.0.62", "9000", "admin", "passwd", accesspoint_id=1)
