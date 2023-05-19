import asyncio
from config import get_config
from connect_arduino_service import read_sensor_data
from log_config import AuditLogger
from webserver import send_measurements

logging = AuditLogger()


async def read_config_task():
    while True:
        try:
            await get_config()  # Starts the read config task
            await asyncio.sleep(10)  # Pause for 10 seconds using asyncio.sleep
        except Exception as e:
            logging.error(f"An error occurred while reading config: {e}, restarting task...")
            await asyncio.sleep(10)  # Wait for 10 seconds before restarting the task


async def send_measurements_task():
    while True:
        try:
            await send_measurements()  # Starts the send measurements task
            await asyncio.sleep(20)  # Pause for 60 seconds using asyncio.sleep
        except Exception as e:
            logging.error(f"An error occurred while sending measurements: {e}, restarting task...")
            await asyncio.sleep(20)  # Wait for 60 seconds before restarting the task


async def main():
    asyncio.create_task(read_sensor_data())  # Starts the read sensor data task
    asyncio.create_task(read_config_task())  # Starts the read config task
    asyncio.create_task(send_measurements_task())  # Starts the send measurements task

    while True:
        await asyncio.sleep(1)  # Add a small sleep to allow other tasks to execute


if __name__ == "__main__":
    asyncio.run(main())
