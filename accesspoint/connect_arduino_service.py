import asyncio
from asyncio import tasks

import pandas as pd
from bleak import BleakClient, BleakScanner, BleakError
import db
import logging

# Define the name of the sensor station you want to connect to
SENSORSTATION_NAME = "XHLN03H"
INTERVAL = 30

# Define a function to handle incoming data from the sensor station
def handle_data(sender, data):
    print("Received data from sensor station: ", data)
    # get the descriptor from the data
    descriptor = data[0]
    # get the value from the data
    value = data[1]
    # write the data to the database
    db.write_to_document_sensor(descriptor, value)



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
        data = pd.DataFrame(config["sensorSettings"])
        sensor_stations: list = data["greenhouseID"].unique()

        for name in sensor_stations:
            device = await BleakScanner.find_device_by_name(name)
            if device is None:
                print("ERROR: Could not find device with name {0}".format(name))
                continue

            async def read_single_sensor():
                while True:
                    try:
                        async with BleakClient(device) as client:
                            for service in client.services:
                                for characteristic in service.characteristics:
                                    await client.start_notify(characteristic, handle_data)
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


