import asyncio
import struct

from bleak import BleakClient, BleakScanner


async def main(device_name="SensorStation G2T4"):
    device = await BleakScanner.find_device_by_name(device_name, timeout=120)
    if device is None:
        print("ERROR: Could not find device with name {0}".format(device_name))
        return

    async with BleakClient(device, timeout=120) as client:
        print("Connected to device {0}".format(device_name))
        while True:
            for service in client.services:
                print("Service: {0}".format(service))

                for characteristic in service.characteristics:
                    print("Characteristic: {0} \n\twith properties: {1}".format(
                        characteristic, ", ".join(characteristic.properties)))
                    try:
                        value = await client.read_gatt_char(characteristic.uuid)

                        if characteristic.uuid == "00002a00-0000-1000-8000-00805f9b34fb":
                            device_name = value.decode("utf-8")
                            print("Device Name: {0}".format(device_name))
                        elif characteristic.uuid == "00002a01-0000-1000-8000-00805f9b34fb":
                            appearance = struct.unpack("<H", value[:2])[0]
                            print("Appearance: {0}".format(appearance))
                        elif characteristic.uuid == "00002a6e-0000-1000-8000-00805f9b34fb":
                            temperature = struct.unpack("<h", value[:2])[0] / 100.0
                            print("Temperature: {0} °C".format(temperature))
                        elif characteristic.uuid == "00002a6f-0000-1000-8000-00805f9b34fb":
                            humidity = struct.unpack("<H", value[:2])[0] / 100.0
                            print("Humidity: {0}%".format(humidity))
                        elif characteristic.uuid == "00002a6d-0000-1000-8000-00805f9b34fb":
                            pressure = struct.unpack("<I", value[:4])[0] / 10.0
                            print("Pressure: {0} Pa".format(pressure))
                        elif characteristic.uuid == "00002bd3-0000-1000-8000-00805f9b34fb":
                            gas_resistance = struct.unpack("<H", value[:2])[0] / 1000.0
                            print("Gas Resistance: {0} kOhm".format(gas_resistance))
                        elif characteristic.uuid == "4ab3244f-d156-4e76-a329-6de917bdc8f9":
                            light_intensity = struct.unpack("<H", value[:2])[0]
                            print("Light Intensity: {0}".format(light_intensity))
                        elif characteristic.uuid == "29c1083c-5166-433c-9b7c-98658c826968":
                            moisture = struct.unpack("<H", value[:2])[0]
                            print("Moisture: {0}".format(moisture))
                    except Exception as e:
                        print("ERROR: reading characteristic {0}. Error is {1}".format(characteristic, e))
                        #
                        # for descriptor in characteristic.descriptors:
                        #     try:
                        #         value = await client.read_gatt_descriptor(descriptor.handle)
                        #         print("Descriptor {0} says: {1}".format(descriptor, value))
                        #     except Exception as e:
                        #         print("ERROR: reading descriptor {0}. Error is {1}".format(descriptor, e))

        print("INFO: Disconnecting from device {0} ...".format(device_name))
    print("INFO: Disconnected from device {0}".format(device_name))


asyncio.run(main())
