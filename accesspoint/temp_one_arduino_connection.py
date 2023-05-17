import asyncio
import struct

from bleak import BleakClient, BleakScanner

from control_services_arduino import send_flag
from log_config import AuditLogger

logging = AuditLogger()


async def notification_handler(sender, data):
    # Process the received notification data here
    # This function will be called whenever a notification is received from the device
    # You can perform your desired actions with the received data
    # For example, you can parse and print the data:
    # write into a file
    print(sender.uuid)
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



async def read_sensor(device_name="SensorStation 69"):
    device = await BleakScanner.find_device_by_name(device_name, timeout=120)
    if device is None:
        print("ERROR: Could not find device with name {0}".format(device_name))
        return

    async with BleakClient(device, timeout=120) as client:
        # notify that we are connected

        print("Connected to device {0}".format(device_name))

        for service in client.services:
            print("Service: {0}".format(service))

            for characteristic in service.characteristics:
                print("Characteristic: {0} \n\twith properties: {1}".format(
                    characteristic, ", ".join(characteristic.properties)))

                if characteristic.uuid == "00002a00-0000-1000-8000-00805f9b34fb" or \
                        characteristic.uuid == "00002a01-0000-1000-8000-00805f9b34fb" or \
                        characteristic.uuid == "00002a05-0000-1000-8000-00805f9b34fb" or \
                        characteristic.uuid == "00002a04-0000-1000-8000-00805f9b34fb":
                    continue

                await client.start_notify(characteristic.uuid, notification_handler)



        while True:
            # The main code loop continues here
            # You can perform other tasks or wait for events while notifications are being received
            await asyncio.sleep(10)  # Add a delay or other tasks here if needed
            #
            # for descriptor in characteristic.descriptors:
            #     try:
            #         value = await client.read_gatt_descriptor(descriptor.handle)
            #         print("Descriptor {0} says: {1}".format(descriptor, value))
            #     except Exception as e:
            #         print("ERROR: reading descriptor {0}. Error is {1}".format(descriptor, e))

        print("INFO: Disconnecting from device {0} ...".format(device_name))
    print("INFO: Disconnected from device {0}".format(device_name))


asyncio.run(read_sensor())
