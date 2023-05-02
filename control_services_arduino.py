import asyncio
import bleak


async def send_flag(arduino_address, UUID, flag_value):
    '''
    Sends a flag to the Arduino.
    :param arduino_address: the name of the arduino
    :param flag_value: the value of the flag
    :return: boolean
    '''
    async with bleak.BleakClient(arduino_address) as client:
        await client.write_gatt_char(UUID, bytearray([flag_value]))
        return True

