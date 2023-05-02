import asyncio
from asyncio import tasks
import struct
import pandas as pd
from bleak import BleakClient, BleakScanner, BleakError
import db
import logging

INTERVAL = 30


async def notification_handler(sender, data):
    with open("data.txt", "a") as f:
        if sender.uuid == "00002a6e-0000-1000-8000-00805f9b34fb":
            temperature = struct.unpack("<h", data[:2])[0] / 100.0
            print("Temperature: {0} °C".format(temperature))
            f.write("Temperature: {0}\n".format(temperature))
        elif sender.uuid == "00002a6f-0000-1000-8000-00805f9b34fb":
            humidity = struct.unpack("<H", data[:2])[0] / 100.0
            print("Humidity: {0}%".format(humidity))
            f.write("Humidity: {0}%\n".format(humidity))
        elif sender.uuid == "00002a6d-0000-1000-8000-00805f9b34fb":
            pressure = struct.unpack("<I", data[:4])[0] / 10.0
            print("Pressure: {0} Pa".format(pressure))
            f.write("Pressure: {0} Pa\n".format(pressure))
        elif sender.uuid == "00002bd3-0000-1000-8000-00805f9b34fb":
            gas_resistance = struct.unpack("<H", data[:2])[0] / 1000.0
            print("Gas Resistance: {0} kOhm".format(gas_resistance))
            f.write("Gas Resistance: {0} kOhm\n".format(gas_resistance))
        elif sender.uuid == "4ab3244f-d156-4e76-a329-6de917bdc8f9":
            light_intensity = struct.unpack("<H", data[:2])[0]
            print("Light Intensity: {0}".format(light_intensity))
            f.write("Light Intensity: {0}\n".format(light_intensity))
        elif sender.uuid == "29c1083c-5166-433c-9b7c-98658c826968":
            moisture = struct.unpack("<H", data[:2])[0]
            print("Moisture: {0}".format(moisture))
            f.write("Moisture: {0}\n".format(moisture))


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
            device = await BleakScanner.find_device_by_name(name, timeout=120)
            if device is None:
                print("ERROR: Could not find device with name {0}".format(name))
                continue

            async def read_single_sensor():
                while True:
                    try:
                        async with BleakClient(device, timeout=120) as client:
                            print("Connected to device {0}".format(name))

                            for service in client.services:
                                print("Service: {0}".format(service))

                                for characteristic in service.characteristics:
                                    print("Characteristic: {0} \n\twith properties: {1}".format(
                                        characteristic, ", ".join(characteristic.properties)))

                                    if characteristic.uuid == "00002a00-0000-1000-8000-00805f9b34fb" or \
                                            characteristic.uuid == "00002a01-0000-1000-8000-00805f9b34fb" or \
                                            characteristic.uuid == "00002a05-0000-1000-8000-00805f9b34fb":
                                        continue

                                    await client.start_notify(characteristic.uuid, notification_handler)
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
