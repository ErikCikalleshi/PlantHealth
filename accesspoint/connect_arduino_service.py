import asyncio
from bleak import BleakClient, BleakScanner
import db
# device_name = "SensorStation GxTy"
device_name = "XHLN03H"


async def main():
    device = await BleakScanner.find_device_by_name(device_name)  # could also have timeout
    if device is None:
        print("ERROR: Could not find device with name {0}".format(device_name))
        return

    # the naming convention is not intuitive imho
    async with BleakClient(device) as client:
        print("Connected to device {0}".format(device_name))

        # print all services and all characteristics provided by device
        for service in client.services:  # iterate all defined services on peripheral
            print("Serivce: {0}".format(service))

            for characteristic in service.characteristics:  # print the characteristics of the service
                print("Characteristic: {0} \n\twith properties: {1}".format(characteristic,
                                                                            ", ".join(characteristic.properties)))
                try:
                    value = await client.read_gatt_char(characteristic.uuid)
                    print("Value is: {0}".format(value))
                except Exception as e:
                    print("ERROR: reading characteristic {0}. Error is {1}".format(characteristic, e))

                for descriptor in characteristic.descriptors:
                    try:
                        value = await client.read_gatt_descriptor(descriptor.handle)
                        print("Descriptor {0} says: {1}".format(descriptor, value))
                        db.write_to_document(descriptor, value)
                    except Exception as e:
                        print("ERROR: reading descriptor {0}. Error is {1}".format(descriptor, e))
                print()
            print("================\n")

        print("INFO: Disconnecting from device {0} ...".format(device_name))
    print("INFO: Disconnected from device {0}".format(device_name))


asyncio.run(main())
