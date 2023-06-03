import asyncio
import bleak
import connect_arduino_service
from log_config import AuditLogger

logging = AuditLogger()


async def send_flag(device_name, flag_value, operation):
    """
    Sends a flag to the Arduino.
    :param operation: specify the operation to be performed
    :param device_name: the name of the arduino
    :param flag_value: the value of the flag
    :return: boolean
    """

    client = None

    for entry in connect_arduino_service.global_client:
        if entry["name"] == device_name:
            client = entry["client"]
            break

    if client is None:
        logging.error("ERROR: Could not find device with name {0}".format(device_name))
        return

    if operation == "ble_disconnect":
        service_uuid = ""
        characteristic_uuid = ""
    elif operation == "led_flag":
        service_uuid = "f5a38368-9851-41cc-b49e-6ad0bba76f9b"
        characteristic_uuid = "eac630d2-9e86-4005-b7b9-6f6955f7ec10"

    for service in client.services:
        if service.uuid == service_uuid:
            for characteristic in service.characteristics:
                if characteristic.uuid == characteristic_uuid:
                    await client.write_gatt_char(characteristic, bytearray([flag_value]), True)
                    logging.info("Wrote led flag to arduino")
                    return True


'''
BLEService ledService("f5a38368-9851-41cc-b49e-6ad0bba76f9b");
BLEByteCharacteristic ledFlagCharacteristic("eac630d2-9e86-4005-b7b9-6f6955f7ec10", BLERead | BLEWrite);
'''

# async def main():
#     await send_flag("SensorStation 16", 128+2)
#
# asyncio.run(main())
