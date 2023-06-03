import requests
from Settings import Settings
import db
from log_config import AuditLogger
import threading
import connect_arduino_service
import asyncio
from control_services_arduino import send_flag

logging = AuditLogger()

INTERVAL: int = 10


async def start_config_thread():
    global INTERVAL
    while True:
        try:
            await get_config()  # Starts the read config task
            await asyncio.sleep(INTERVAL)  # Pause for 10 seconds using asyncio.sleep
        except Exception as e:
            print(e)
            logging.error(f"An error occurred while reading config: {e}, restarting task...")
            await asyncio.sleep(INTERVAL)  # Wait for 10 seconds before restarting the task


async def get_config():

    settings = Settings()

    url = f"http://{settings.server_host}:{settings.server_port}/api/setting/{settings.access_point_id}"
    try:
        response = requests.get(url, auth=settings.auth)
    except requests.exceptions.ConnectionError:
        logging.error("api/setting/ API call failed")
        return

    if response.status_code != 200:
        logging.error("api/setting/ API call failed")
        return

    data = response.json()

    global INTERVAL
    INTERVAL = data["transmissionIntervalSeconds"]
    # write to db the config
    database = await db.connect_to_db()
    # clear collection config and insert new config
    collection = database["config"]
    collection.delete_many({})
    collection.insert_one(data)

    logging.info("Database updated and config inserted successfully")
    logging.info("api/setting/ API call successful")
    # check for every published
    for entry in connect_arduino_service.global_client:
        for greenhouse in data["greenhouses"]:
            id = greenhouse["id"]
            if entry["name"] == ("SensorStation " + str(id)) and not greenhouse["published"]:
                client = entry["client"]
                await send_flag(entry["name"], 1, "ble_disconnect")
                await client.disconnect()
                logging.info("BLE Connection disabled")
                break


# for debug purposes only
if __name__ == "__main__":
    asyncio.run(get_config())
