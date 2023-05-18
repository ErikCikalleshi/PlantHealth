import asyncio


from config import get_config
from connect_arduino_service import read_sensor_data
from log_config import AuditLogger
from webserver import send_measurements
import time
logging = AuditLogger()


async def main():
    asyncio.create_task(read_sensor_data())  # Starts the read sensor data task
    print("Testtttttt")
    while True:
        try:
            await get_config()  # Starts the read config task
            await asyncio.sleep(5)  # Pause for 5 seconds using asyncio.sleep
            await send_measurements()  # Starts the send measurements task
        except:
            logging.error("An error occurred, restarting tasks...")

    


if __name__ == "__main__":
    asyncio.run(main())
