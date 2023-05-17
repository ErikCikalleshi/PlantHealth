import asyncio
import threading

from config import get_config
from connect_arduino_service import read_sensor_data
from log_config import AuditLogger
from webserver import send_measurements

logging = AuditLogger()


def main():
    threading.Timer(1, get_config).start()  # starts the read config thread
    send_measurements()  # starts the send measurements and inside it starts the thread

    asyncio.run(read_sensor_data())  # starts the read sensor data thread
    print("TEssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssst")
   
    print("TEsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssst")
    logging.info("Access point started all functions")


if __name__ == "__main__":
    main()
