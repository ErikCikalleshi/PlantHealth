import asyncio
import struct
from asyncio import tasks
import pandas as pd
from bleak import BleakClient, BleakScanner, BleakError

import db

from auditlog_config import AuditLogger
from webserver import button_disabled_pressed

logging = AuditLogger()
INTERVAL = 5

collection_deletion_event = asyncio.Event()
global client
global_connections = []


async def read_sensor_data():
    """
    Continuously reads data from sensor stations based on the latest configuration.

    This function connects to sensor stations, starts data notifications, and continuously reads and handles incoming
    data. It periodically checks the configuration document in the database for updates.

    """
    database = await db.connect_to_db()

    while True:
        # Retrieve the latest configuration from the database
        config_collection = database["config"]

        config = config_collection.find_one()
        data = pd.DataFrame(config["greenhouses"])
        data = data[data["published"]]
        data = data.assign(convention_name=lambda x: "SensorStation " + x["id"].astype(str))

        sensor_stations: list = data["convention_name"].unique()
        available_sensor_stations = []
        devices = await BleakScanner.discover(timeout=10.0, return_adv=True)

        for k, v in devices.items():
            if v[1].local_name in sensor_stations:
                available_sensor_stations.append((v[1].local_name, v[0]))
                logging.info("Found device with name {0}".format(v[1].local_name))

        for idx, (name, device) in enumerate(available_sensor_stations):
            sensor_station_id = data[data["convention_name"] == name]["id"].iloc[0]
            logging.info("Looking for device with name {0}".format(name))

            if device is None:
                logging.error("Could not find device with name {0}".format(name))
                continue

            logging.info("Found device with name {0}".format(name))

            async def read_from_device(device_ble, station_name):
                """
                   Task for reading data from a specific sensor station device.

                   Args:
                       device_ble (str): BLE address of the device.
                       station_name (str): Name of the sensor station.

                """
                while True:
                    try:
                        print("Trying to connect to device")
                        async with BleakClient(device_ble, timeout=10) as bleak_client:

                            global_connections.append({"client": bleak_client, "name": station_name})
                            logging.info("Connected to device {0}".format(station_name))

                            for service in bleak_client.services:

                                for characteristic in service.characteristics:

                                    if characteristic.uuid == "00002a00-0000-1000-8000-00805f9b34fb" or \
                                            characteristic.uuid == "00002a01-0000-1000-8000-00805f9b34fb" or \
                                            characteristic.uuid == "00002a05-0000-1000-8000-00805f9b34fb":
                                        continue

                                    async def notification_handler(sender, value):
                                        """
                                           Handler for receiving notifications from a characteristic.

                                           Args:
                                               sender: The characteristic sender.
                                               value: The received value.
                                        """
                                        if collection_deletion_event.is_set():
                                            logging.warning(
                                                "Collection deletion in progress. Skipping writing to the database.")

                                        sensor_mappings = {
                                            "00002a6e-0000-1000-8000-00805f9b34fb": ("TEMPERATURE", "<h", 100.0, 2),
                                            "00002a6f-0000-1000-8000-00805f9b34fb": ("HUMIDITY_AIR", "<H", 100.0, 2),
                                            "00002a6d-0000-1000-8000-00805f9b34fb": ("AIR_PRESSURE", "<L", 1000.0, 4),
                                            "00002bd3-0000-1000-8000-00805f9b34fb": ("AIR_QUALITY", "<f", 10000.0, 4),
                                            "4ab3244f-d156-4e76-a329-6de917bdc8f9": ("LIGHT", "<I", 1.0, 4),
                                            "29c1083c-5166-433c-9b7c-98658c826968": ("HUMIDITY_DIRT", "<I", 10.24, 4),
                                            "eac630d2-9e86-4005-b7b9-6f6955f7ec10": ("LED", "<c", 1.0, 1),
                                        }

                                        for uuid, (
                                                sensor_type, unpack_format, scale_factor,
                                                buffer) in sensor_mappings.items():
                                            if sender.uuid == uuid:
                                                if sensor_type == "LED":

                                                    val = struct.unpack(unpack_format, value[:buffer])[0]

                                                    if val == b'\x00':
                                                        logging.info("Warnings disabled")
                                                        await button_disabled_pressed(
                                                            greenhouse_id=int(sensor_station_id))
                                                    continue
                                                val = struct.unpack(unpack_format, value[:buffer])[0] / scale_factor

                                                if sensor_type == "AIR_QUALITY":
                                                    val = 100 - val
                                                logging.info(
                                                    "Received from SensorStation {0}: {1}".format(sensor_station_id,
                                                                                                  val))
                                                await db.write_to_document_sensor(val, sensor_type,
                                                                                  int(sensor_station_id))
                                                logging.info("Wrote {0} to the database.".format(val))
                                                break

                                    logging.info("Starting notifications for {0}".format(characteristic.uuid))
                                    await bleak_client.start_notify(characteristic.uuid, notification_handler)

                            while True:
                                await asyncio.sleep(1)
                    except BleakError:
                        # Handle disconnection here
                        logging.error(
                            "Disconnected from device {0}. Attempting to reconnect in 5 seconds.".format(station_name))
                        await asyncio.sleep(5)
                        # Cancel and close the task
                        tasks.current_task().cancel()

            tasks.create_task(read_from_device(device, name))
            logging.info("Started reading data from device {0}".format(name))

        logging.info("Finished reading data from all devices. Checking for updates ...")
        await asyncio.sleep(INTERVAL)


if __name__ == "__main__":
    asyncio.run(read_sensor_data())
