import asyncio
from bleak import BleakClient, BleakScanner
import db
import logging

# Define the name of the sensor station you want to connect to
SENSORSTATION_NAME = "sensorstationG2T4"


# Define a function to handle incoming data from the sensor station
def handle_data(sender, data):
    print("Received data from sensor station: ", data)
    # get the descriptor from the data
    descriptor = data[0]
    # get the value from the data
    value = data[1]
    # write the data to the database
    db.write_to_document_sensor(descriptor, value)


# Define a function to connect to the sensor station and start reading data
async def read_sensor_data():
    devices = await BleakScanner.discover(timeout=30.0)

    device = None
    for device in devices:
        if device.name == SENSORSTATION_NAME:
            device = device
            break

    if device:

        client = BleakClient(device.address)
        await client.connect()

        for service in client.services:
            for characteristic in service.characteristics:
                await client.start_notify(characteristic, handle_data)

        # # Keep reading data until the user interrupts the program
        # while True:
        #     await asyncio.sleep(1)

        # Stop notifications and disconnect from the device
        for service in client.services:
            for characteristic in service.characteristics:
                await client.stop_notify(characteristic)
        await client.disconnect()

    else:
        logging.warning("Could not find sensor station with name ", SENSORSTATION_NAME)


# Start reading data from the sensor station
asyncio.run(read_sensor_data())
