import requests

import db
from Settings import Settings

import asyncio
from auditlog_config import AuditLogger
from control_services_arduino import check_ble_connection

logging = AuditLogger()

INTERVAL: int = 5


async def start_config_thread():
    global INTERVAL
    while True:
        try:
            status = await get_settings_backend()  # Starts the read config task
            if status == 200:
                logging.info("Config read successfully")
            else:
                logging.warning("Could not read config")
            await asyncio.sleep(INTERVAL)  # Pause for 10 seconds using asyncio.sleep
        except Exception as e:
            logging.error(f"An error occurred while reading config: {e}, restarting task...")
            await asyncio.sleep(INTERVAL)  # Wait for 10 seconds before restarting the task


async def get_settings_backend() -> int:
    """
        Retrieves settings from a backend server and updates the local database with the received configuration.

        Returns:
            int: HTTP status code indicating the success or failure of the API call.
    """
    settings = Settings()

    url = f"http://{settings.server_host}:{settings.server_port}/api/setting/{settings.access_point_id}"
    
    try:
        response = requests.get(url, auth=settings.auth)
    except requests.exceptions.ConnectionError:
        logging.error("api/setting/ API call failed")
        return response.status_code

    if response.status_code != 200:
        logging.error("api/setting/ API call failed")
        return response.status_code
    
    logging.info("api/setting/ API call successful")

    data = response.json()

    # global INTERVAL
    # INTERVAL = data["transmissionIntervalSeconds"]
    
    # write to db the config
    database = await db.connect_to_db()

    # clear collection config and insert new config
    collection = database["config"]
    collection.delete_many({})
    collection.insert_one(data)

    logging.info("Database updated and config inserted successfully")
    logging.info("api/setting/ API call successful")
    await check_ble_connection(data)
    return response.status_code


# for debug purposes only
if __name__ == "__main__":
    asyncio.run(get_settings_backend())
