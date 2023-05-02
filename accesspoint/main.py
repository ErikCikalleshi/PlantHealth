import asyncio
import threading

from config import get_config
from webserver import send_measurements
from connect_arduino_service import read_sensor_data


def main():
    threading.Timer(5, get_config).start()  # starts the read config thread
    asyncio.run(read_sensor_data())  # starts the read sensor data thread
    send_measurements()  # starts the send measurements and inside it starts the thread


if __name__ == "__main__":
    main()
