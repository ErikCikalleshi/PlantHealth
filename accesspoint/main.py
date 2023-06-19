import asyncio
from config import start_config_thread
from connect_arduino_service import read_sensor_data
from auditlog_config import AuditLogger
from webserver import send_measurements_task

logging = AuditLogger()


async def main():
    asyncio.create_task(start_config_thread())  # Starts the read config task
    asyncio.create_task(read_sensor_data())  # Starts the read sensor data task
    asyncio.create_task(send_measurements_task())  # Starts the send measurements task

    while True:
        await asyncio.sleep(1)  # Add a small sleep to allow other tasks to execute


if __name__ == "__main__":
    asyncio.run(main())
