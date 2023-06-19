import asyncio

from connect_arduino_service import global_connections
from auditlog_config import AuditLogger

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

    for entry in global_connections:
        if entry["name"] == device_name:
            client = entry["client"]
            break

    if client is None:
        logging.error("ERROR: Could not find device with name {0}".format(device_name))
        return

    service_uuid = None
    characteristic_uuid = None

    if operation == "ble_disconnect":
        service_uuid = "8b038794-1cfa-472c-a1f7-4e2d7b6ad2a4"
        characteristic_uuid = "5434aa28-4300-4d09-9ab2-79b70ba8ef5d"
    elif operation == "led_flag":
        service_uuid = "f5a38368-9851-41cc-b49e-6ad0bba76f9b"
        characteristic_uuid = "eac630d2-9e86-4005-b7b9-6f6955f7ec10"

    if service_uuid is None or characteristic_uuid is None:
        logging.error("ERROR: Could not find service or characteristic for operation {0}".format(operation))
        return

    for service in client.services:
        if service.uuid == service_uuid:
            for characteristic in service.characteristics:
                if characteristic.uuid == characteristic_uuid:
                    await client.write_gatt_char(characteristic, bytearray([flag_value]), True)
                    logging.info("Wrote " + operation + " flag to arduino")
                    if operation == "ble_disconnect":
                        await client.disconnect()
                    return


async def check_ble_connection(data):
    """
    Checks the BLE connection status for each published sensor station.

    This function iterates over the global connections and the provided data to check if any sensor station
    has been marked as unpublished. If a sensor station is found to be unpublished, the function disconnects
    the corresponding BLE client and removes it from the global connections list.

    Args:
        data (dict): The data containing the sensor station information.

    """
    # check for every published
    for entry in global_connections:
        for greenhouse in data["greenhouses"]:
            greenhouse_id = greenhouse["id"]

            if entry["name"] == ("SensorStation " + str(greenhouse_id)) and not greenhouse["published"]:
                connected_client = entry["client"]
                await send_flag(entry["name"], 1, "ble_disconnect")
                await asyncio.sleep(1)
                await connected_client.disconnect()
                global_connections.remove(entry)
                logging.info("BLE Connection disabled")
                break