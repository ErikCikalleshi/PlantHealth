import asyncio
import struct
from asyncio import tasks
import numpy as np
import pandas as pd
from bleak import BleakClient, BleakScanner, BleakError

import db
import webserver
from log_config import AuditLogger

logging = AuditLogger()
INTERVAL = 30

collection_deletion_event = asyncio.Event()


# async def notification_handler(sender, value):
#     from webserver import collection_deletion_event
#     if collection_deletion_event.is_set():
#         logging.warning("Collection deletion in progress. Skipping writing to the database.")
#
#     print(sender.uuid)
#     sensor_mappings = {
#         "00002a6e-0000-1000-8000-00805f9b34fb": ("TEMPERATURE", "<h", 100.0),
#         "00002a6f-0000-1000-8000-00805f9b34fb": ("HUMIDITY_AIR", "<H", 100.0),
#         "00002a6d-0000-1000-8000-00805f9b34fb": ("PRESSURE", "<I", 10.0),
#         "00002bd3-0000-1000-8000-00805f9b34fb": ("GAS_RESISTANCE", "<H", 1000.0),
#         "4ab3244f-d156-4e76-a329-6de917bdc8f9": ("LIGHT", "<H", 1.0),
#         "29c1083c-5166-433c-9b7c-98658c826968": ("HUMIDITY_DIRT", "<H", 1.0),
#     }
#
#     # sender.
#     for uuid, (sensor_type, unpack_format, scale_factor) in sensor_mappings.items():
#         if sender.uuid == uuid:
#             val = struct.unpack(unpack_format, value[:2])[0] / scale_factor
#             print("{0}: {1}".format(sensor_type, val))
#             await collection_deletion_event.wait()
#             sensor_id = greenhouses[greenhouses["sensors"] == sensor_type]["id"].iloc[0]
#             db.write_to_document_sensor(val, sensor_type, sensor_id)
#             logging.info("Wrote {0} to the database.".format(val))
#             break


async def read_sensor_data():
    """
    Continuously reads data from sensor stations based on the latest configuration.

    This function connects to sensor stations, starts data notifications, and continuously reads and handles incoming data.
    It periodically checks the configuration document in the database for updates.

    """
    database = db.connect_to_db()
    config_collection = database["config"]
    
    while True:
        # Retrieve the latest configuration from the database
        config = config_collection.find_one()
        data = pd.DataFrame(config["greenhouses"])
        data = data[data["published"] == True]
        data = data.assign(convention_name=lambda x: "SensorStation " + x["id"].astype(str))
        # data.at[data.index[0], "convention_name"] = "SensorStation 69"
        # data.at[data.index[0], "id"] = 69
       
        sensor_stations: list = data["convention_name"].unique()
       
        # TODO: make a name convention for the sensor stations
        # sensor_stations = ["SensorStation 69"]
        for idx, name in enumerate(sensor_stations):
            if name != "SensorStation 16":
                continue
            id = data[data["convention_name"] == name]["id"].iloc[0]                     
            greenhouse_idx = pd.DataFrame(data[data["id"] == id]["sensors"].iloc[0])
            logging.info("Looking for device with name {0}".format(name))
            device = await BleakScanner.find_device_by_name(name, timeout=120)
            print(name)
            if device is None:
                logging.error("Could not find device with name {0}".format(name))
                continue


            print("Test")
            
            print("Found device with name {0}".format(name))

            async def read_from_device():
                while True:
                    try:
                        async with BleakClient(device, timeout=120) as client:
                            logging.info("Connected to device {0}".format(name))

                            for service in client.services:
                                print("Service: {0}".format(service))

                                for characteristic in service.characteristics:
                                    print("Characteristic: {0} \n\twith properties: {1}".format(
                                        characteristic, ", ".join(characteristic.properties)))

                                    if characteristic.uuid == "00002a00-0000-1000-8000-00805f9b34fb" or \
                                            characteristic.uuid == "00002a01-0000-1000-8000-00805f9b34fb":
                                        continue

                                    async def notification_handler(sender, value):
                                        from webserver import collection_deletion_event
                                        if collection_deletion_event.is_set():
                                            logging.warning(
                                                "Collection deletion in progress. Skipping writing to the database.")
                                        # characteristic.uuid == "00002a05-0000-1000-8000-00805f9b34fb":
                                        sensor_mappings = {
                                            "00002a6e-0000-1000-8000-00805f9b34fb": ("TEMPERATURE", "<h", 100.0, 2),
                                            "00002a6f-0000-1000-8000-00805f9b34fb": ("HUMIDITY_AIR", "<H", 100.0, 2),
                                            "00002a6d-0000-1000-8000-00805f9b34fb": ("AIR_PRESSURE", "<L", 10.0, 4),
                                            "00002bd3-0000-1000-8000-00805f9b34fb": ("AIR_QUALITY", "<f", 1000.0, 4),
                                            "4ab3244f-d156-4e76-a329-6de917bdc8f9": ("LIGHT", "<I", 1.0, 4),
                                            "29c1083c-5166-433c-9b7c-98658c826968": ("HUMIDITY_DIRT", "<I", 1.0, 4),
                                            "00002a05-0000-1000-8000-00805f9b34fb": ("LED", "<c", 1.0, 1),
                                        }
                                        for uuid, (sensor_type, unpack_format, scale_factor, buffer) in sensor_mappings.items():
                                            if sender.uuid == uuid:
                                        
                                                val = struct.unpack(unpack_format, value[:buffer])[0] / scale_factor
                                                print("{0}: {1}".format(sensor_type, val))
                                                if sensor_type == "LED":
                                                    print("LED")
                                                    if val == 0:
                                                        print("LED")
                                                        webserver.button_disabled_pressed(greenhouse_id=16)

                                                #await collection_deletion_event.wait()
            
                                                
                                                sensor_id = \
                                                    greenhouse_idx[greenhouse_idx["sensorType"] == sensor_type]["id"].iloc[0]
                                                
                                                db.write_to_document_sensor(val, sensor_type, int(sensor_id))
                                                logging.info("Wrote {0} to the database.".format(val))
                                                break

                                    await client.start_notify(characteristic.uuid, notification_handler)

                            while True:
                                await asyncio.sleep(1)
                    except BleakError:
                        # Handle disconnection here
                        # Wait for a while before attempting to reconnect
                        logging.error(
                            "Disconnected from device {0}. Attempting to reconnect in 5 seconds.".format(name))
                        await asyncio.sleep(5)
                        # Cancel and close the task
                        #tasks.current_task().cancel()

            tasks.create_task(read_from_device())
            logging.info("Started reading data from device {0}".format(name))

        logging.info("Finished reading data from all devices. Checking for updates in 30 seconds.")
        await asyncio.sleep(INTERVAL)  # Wait for 30 seconds before checking again


# Start reading data from the sensor station
# asyncio.run(read_sensor_data())

if __name__ == "__main__":
    print("test")
    asyncio.run(read_sensor_data())
