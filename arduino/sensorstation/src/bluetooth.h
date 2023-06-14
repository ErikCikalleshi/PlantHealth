#ifndef BLUETOOTH_H
#define BLUETOOTH_H

#include <ArduinoBLE.h>

void BLE_setup();
int get_ID();

void check_pairing_mode();

void BLE_connect_handler(BLEDevice central);
void BLE_disconnect_handler(BLEDevice central);
void clean_disconnect_handler(BLEDevice central, BLECharacteristic characteristic);

#endif