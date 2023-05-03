import asyncio
from asyncio import tasks
import struct
import pandas as pd
from bleak import BleakClient, BleakScanner, BleakError
import db
import logging
import webserver

INTERVAL = 30

collection_deletion_event = asyncio.Event()


async def notification_handler(sender, value, greenhouses: pd.DataFrame):
    # with open("data.txt", "a") as f:
    #     if sender.uuid == "00002a6e-0000-1000-8000-00805f9b34fb":
    #         temperature = struct.unpack("<h", data[:2])[0] / 100.0
    #         print("Temperature: {0} °C".format(temperature))
    #         db.write_to_document_sensor(temperature, "TEMPERATURE")
    #         # f.write("Temperature: {0}\n".format(temperature))
    #     elif sender.uuid == "00002a6f-0000-1000-8000-00805f9b34fb":
    #         humidity = struct.unpack("<H", data[:2])[0] / 100.0
    #         print("Humidity: {0}%".format(humidity))
    #         db.write_to_document_sensor(humidity, "HUMIDITY_AIR")
    #         # f.write("Humidity: {0}%\n".format(humidity))
    #     elif sender.uuid == "00002a6d-0000-1000-8000-00805f9b34fb":
    #         pressure = struct.unpack("<I", data[:4])[0] / 10.0
    #         print("Pressure: {0} Pa".format(pressure))
    #         db.write_to_document_sensor(pressure, "PRESSURE")
    #         # f.write("Pressure: {0} Pa\n".format(pressure))
    #     elif sender.uuid == "00002bd3-0000-1000-8000-00805f9b34fb":
    #         gas_resistance = struct.unpack("<H", data[:2])[0] / 1000.0
    #         print("Gas Resistance: {0} kOhm".format(gas_resistance))
    #         db.write_to_document_sensor(gas_resistance, "GAS_RESISTANCE")
    #         # f.write("Gas Resistance: {0} kOhm\n".format(gas_resistance))
    #     elif sender.uuid == "4ab3244f-d156-4e76-a329-6de917bdc8f9":
    #         light_intensity = struct.unpack("<H", data[:2])[0]
    #         print("Light Intensity: {0}".format(light_intensity))
    #         db.write_to_document_sensor(light_intensity, "LIGHT")
    #         # f.write("Light Intensity: {0}\n".format(light_intensity))
    #     elif sender.uuid == "29c1083c-5166-433c-9b7c-98658c826968":
    #         moisture = struct.unpack("<H", data[:2])[0]
    #         print("Moisture: {0}".format(moisture))
    #         db.write_to_document_sensor(moisture, "HUMIDITY_DIRT")
    #         # f.write("Moisture: {0}\n".format(moisture))

    if webserver.collection_deletion_in_progress:
        logging.warning("Collection deletion in progress. Skipping writing to the database.")
        return

    sensor_mappings = {
        "00002a6e-0000-1000-8000-00805f9b34fb": ("TEMPERATURE", "<h", 100.0),
        "00002a6f-0000-1000-8000-00805f9b34fb": ("HUMIDITY_AIR", "<H", 100.0),
        "00002a6d-0000-1000-8000-00805f9b34fb": ("PRESSURE", "<I", 10.0),
        "00002bd3-0000-1000-8000-00805f9b34fb": ("GAS_RESISTANCE", "<H", 1000.0),
        "4ab3244f-d156-4e76-a329-6de917bdc8f9": ("LIGHT", "<H", 1.0),
        "29c1083c-5166-433c-9b7c-98658c826968": ("HUMIDITY_DIRT", "<H", 1.0),
    }
    from webserver import collection_deletion_event
    # sender.
    for uuid, (sensor_type, unpack_format, scale_factor) in sensor_mappings.items():
        if sender.uuid == uuid:
            val = struct.unpack(unpack_format, value[:2])[0] / scale_factor
            print("{0}: {1}".format(sensor_type, val))
            await collection_deletion_event.wait()
            sensor_id = greenhouses[greenhouses["sensors"] == sensor_type]["id"].iloc[0]
            db.write_to_document_sensor(val, sensor_type, sensor_id)
            break


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
        sensor_stations: list = data["id"].unique()
        # TODO: make a name convention for the sensor stations
        sensor_stations = ["SensorStation G2T4"]
        for name in sensor_stations:
            logging.warning("Looking for device with name {0}".format(name))
            device = await BleakScanner.find_device_by_name(name, timeout=120)
            if device is None:
                logging.error("Could not find device with name {0}".format(name))
                continue

            async def read_single_sensor():
                while True:
                    try:
                        async with BleakClient(device, timeout=120) as client:
                            logging.warning("Connected to device {0}".format(name))

                            for service in client.services:
                                print("Service: {0}".format(service))

                                for characteristic in service.characteristics:
                                    print("Characteristic: {0} \n\twith properties: {1}".format(
                                        characteristic, ", ".join(characteristic.properties)))

                                    if characteristic.uuid == "00002a00-0000-1000-8000-00805f9b34fb" or \
                                            characteristic.uuid == "00002a01-0000-1000-8000-00805f9b34fb" or \
                                            characteristic.uuid == "00002a05-0000-1000-8000-00805f9b34fb":
                                        continue

                                    await client.start_notify(characteristic.uuid,
                                                              lambda sender, value,
                                                                     greenhouses=data: notification_handler(sender,
                                                                                                            value,
                                                                                                            greenhouses)
                                                              )
                            while True:
                                await asyncio.sleep(1)
                    except BleakError:
                        # Handle disconnection here
                        # Wait for a while before attempting to reconnect
                        await asyncio.sleep(5)
                        # Cancel and close the task
                        tasks.current_task().cancel()

            tasks.create_task(read_single_sensor())

        await asyncio.sleep(INTERVAL)  # Wait for 30 seconds before checking again


# Start reading data from the sensor station
asyncio.run(read_sensor_data())
