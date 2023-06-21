#include "bluetooth.h"
#include "global.h"
#include <Arduino.h>
#include <ArduinoBLE.h>
#include <stdio.h>

// function declarations for functions that are not declared in the header as
// they are not needed outside of this file
int get_ID();
void BLE_connect_handler(BLEDevice central);
void BLE_disconnect_handler(BLEDevice central);
void clean_disconnect_handler(BLEDevice central, BLECharacteristic characteristic);
void led_flag_handler(BLEDevice central, BLECharacteristic characteristic);

/**
 * setup function for Bluetooth LE functionalities
 * 
 * Name of device is set to "SensorStation <ID>", where ID is the binary value
 * of the DIP switch converted to decimal.
 * 
 * Connect and Disconnect handlers are attached to Connect and Dsiconnect events
*/
void BLE_setup() {
  if (!BLE.begin()) {
    Serial.println("Starting BLE failed!");

    return;
  }
  Serial.print("BLE started");

  sprintf(device_name, "SensorStation %d", get_ID());

  BLE.setLocalName(device_name);
  BLE.setDeviceName(device_name);

  Serial.print(", Device Name: ");
  Serial.println(device_name);

  BLE.setAdvertisedService(airSensorService);

  BLEDescriptor temperatureDescriptor("2901", "Temperature in degrees Celsius * 100");
  temperatureCharacteristic.addDescriptor(temperatureDescriptor);

  BLEDescriptor humidityDescriptor("2901", "Humidity between 0 (0%) and 10000 (100%)");
  humidityCharacteristic.addDescriptor(humidityDescriptor);

  BLEDescriptor pressureDescriptor("2901", "Pressure in dPa");
  pressureCharacteristic.addDescriptor(pressureDescriptor);

  BLEDescriptor vocDescriptor("2901", "Gas Resistance in kOhm");
  vocCharacteristic.addDescriptor(vocDescriptor);

  BLEDescriptor lightIntensityDescriptor("2901", "Light Intensity between 0 and 1023");
  lightIntensityCharacteristic.addDescriptor(lightIntensityDescriptor);

  BLEDescriptor moistureDescriptor("2901", "Moisture of Earth between 0 and 1023");
  moistureCharacteristic.addDescriptor(moistureDescriptor);

  airSensorService.addCharacteristic(temperatureCharacteristic);
  airSensorService.addCharacteristic(humidityCharacteristic);
  airSensorService.addCharacteristic(pressureCharacteristic);
  airSensorService.addCharacteristic(vocCharacteristic);
  BLE.addService(airSensorService);

  lightSensorService.addCharacteristic(lightIntensityCharacteristic);
  BLE.addService(lightSensorService);

  hygrometerService.addCharacteristic(moistureCharacteristic);
  BLE.addService(hygrometerService);

  ledService.addCharacteristic(ledFlagCharacteristic);
  BLE.addService(ledService);
  ledFlagCharacteristic.setEventHandler(BLEWritten, led_flag_handler);

  disconnectService.addCharacteristic(disconnectFlagCharacteristic);
  BLE.addService(disconnectService);
  disconnectFlagCharacteristic.setEventHandler(BLEWritten, clean_disconnect_handler);

  BLE.setEventHandler(BLEConnected, BLE_connect_handler);
  BLE.setEventHandler(BLEDisconnected, BLE_disconnect_handler);
}

/**
 * Getter Function for Device ID
 * 
 * returns the binary value of the DIP switch converted to decimal.
*/
int get_ID() {
  for (int i = 0; i < 8; i++) {
    pinMode(dipSwitchPins[i], INPUT_PULLDOWN);
  }
  int ID = 0;
  for (int i = 0; i < 8; i++) {
    ID += digitalRead(dipSwitchPins[i]) << i; 
  }
  return ID;
}

/**
 * function that handles advertising and stops pairing mode if time has expired
*/
void check_pairing_mode() {
  if (pairing_mode && (current_millis - pairing_mode_start_millis >= BLE_PAIRING_TIME)) {
    pairing_mode = 0;
    color = PURPLE;
    blink_on = 0;
    led_on = 1;
    disconnect_sound();
  }
  if ((pairing_mode || unclean_disconnect) && !connected) {
    BLE.advertise();
  } else {
    BLE.stopAdvertise();
  }
}

/**
 * handles new connections and is called whenever a new device connects.
*/
void BLE_connect_handler(BLEDevice central) {
  if (!pairing_mode && unclean_disconnect && (central.address() != last_connected)) {
    BLE.disconnect();
    return;
  }
  pairing_mode = 0;
  color = GREEN;
  blink_on = 0;
  led_on = 1;
  connected = 1;
  unclean_disconnect = 1;
  Serial.println("Connected event, central: ");
  Serial.println(central.address());
  last_connected = central.address();
  connect_sound();
}

/**
 * handles disconnection, and is called automatically when a device is disconnected
*/
void BLE_disconnect_handler(BLEDevice central) {
  color = PURPLE;
  blink_on = 0;
  led_on = 1;
  connected = 0;
  Serial.println("Disconnected event, central: ");
  Serial.println(central.address());
  disconnect_sound();
  if (unclean_disconnect) {
    BLE.advertise();
  }
}

/**
 * handles the clean disconnection of devices, i.e. when they are disconnected
 * over the WebApp. It is called when the corresponding characteristic is written
 * to.
*/
void clean_disconnect_handler(BLEDevice central, BLECharacteristic characteristic) {
  if (*characteristic.value() == 1) {
    Serial.println("Clean disconnect.");
    unclean_disconnect = 0;
    last_connected = "";
    disconnectFlagCharacteristic.writeValue(0x00);
  }
}

/**
 * handles the different blink codes, when limits are exceeded. It is called
 * when the ledFlagCharacteristic is written to.
*/
void led_flag_handler(BLEDevice central, BLECharacteristic characteristic) {
  byte flag = ledFlagCharacteristic.value();
  num_blinks = 127 & flag;
  if (num_blinks == 0) {
    color = GREEN;
    blink_on = 0;
    warning_on = 0;
  } else {
    color = flag >> 7;
    blink_on = 1;
    warning_on = 1;
  }
}