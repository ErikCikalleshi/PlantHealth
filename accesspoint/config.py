import requests
import json


def get_config(ip: str, port: str, admin: str, password: str):
    url = f"http://{ip}:{port}/api/setting/1"
    auth = (admin, password)
    try:
        response = requests.get(url, auth=auth)
    except requests.exceptions.ConnectionError:
        print("Error: 'api/setting/' API call failed")
        return response.status_code

    data = response.json()
    with open("config/config.json", "w") as f:
        # save as pretty
        json.dump(data, f, indent=4)
    return response.status_code


# for debug purposes only
if __name__ == "__main__":
    # NOTE: put your own IP address
    get_config("10.0.0.62", "9000", "admin", "passwd")
