import requests
from Settings import Settings
import db
from log_config import AuditLogger
import threading
logging = AuditLogger()

INTERVAL: int = 5  # seconds


async def get_config():
    settings = Settings()
    url = f"http://{settings.server_host}:{settings.server_port}/api/setting/{1}"
    try:
        response = requests.get(url, auth=settings.auth)
    except requests.exceptions.ConnectionError:
        logging.error("api/setting/ API call failed")
        return

    if response.status_code != 200:
        logging.error("api/setting/ API call failed")
        return

    data = response.json()
    # write to db the config
    database = await db.connect_to_db()
    # clear collection config and insert new config
    collection = database["config"]
    collection.delete_many({})
    collection.insert_one(data)
    logging.info("Database cleared and config inserted successfully")
    logging.info("api/setting/ API call successful")
    
    #threading.Timer(1, lambda: get_config).start()


# for debug purposes only
if __name__ == "__main__":
    get_config()
