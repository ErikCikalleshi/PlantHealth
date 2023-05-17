import asyncio
import bleak


async def send_flag(device_name, UUID, flag_value):
    '''
    Sends a flag to the Arduino.
    :param arduino_address: the name of the arduino
    :param flag_value: the value of the flag
    :return: boolean
    '''
    # device = await bleak.BleakScanner.find_device_by_name(device_name, timeout=120)
    # if device is None:
    #     print("ERROR: Could not find device with name {0}".format(device_name))
    #     return
    async with bleak.BleakClient(device, timeout=120) as client:
        for service in client.services:
            # print("Service: {0}".format(service))
            if service.uuid == "f5a38368-9851-41cc-b49e-6ad0bba76f9b":
                print("Found service")
                for characteristic in service.characteristics:
                    # print("Characteristic: {0}".format(characteristic))
                    if characteristic.uuid == UUID:
                        print("Found characteristic")
                        await client.write_gatt_char(characteristic, bytearray([flag_value, flag_value, flag_value, flag_value]), True)
                        print("Wrote value")
                        return True

'''
BLEService ledService("f5a38368-9851-41cc-b49e-6ad0bba76f9b");
BLEByteCharacteristic ledFlagCharacteristic("eac630d2-9e86-4005-b7b9-6f6955f7ec10", BLERead | BLEWrite);
'''

# async def main():
#     await send_flag("SensorStation G2T4", "eac630d2-9e86-4005-b7b9-6f6955f7ec10", 3)

# asyncio.run(main())