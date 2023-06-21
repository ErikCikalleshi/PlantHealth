#include "global.h"
#include <Arduino.h>
#include <ArduinoBLE.h>

// -----------------BLE Services + Characteristics Definitions------------------
BLEService airSensorService("181A");
BLEShortCharacteristic temperatureCharacteristic("2A6E", BLERead | BLENotify);
BLEUnsignedShortCharacteristic humidityCharacteristic("2A6F", BLERead | BLENotify);
BLEUnsignedLongCharacteristic pressureCharacteristic("2A6D", BLERead | BLENotify);
BLEFloatCharacteristic vocCharacteristic("2BD3", BLERead | BLENotify);

BLEService lightSensorService("8444401e-ffb9-424d-9dc1-c2bc273b47b5");
BLEUnsignedIntCharacteristic lightIntensityCharacteristic("4ab3244f-d156-4e76-a329-6de917bdc8f9", BLERead | BLENotify);

BLEService hygrometerService("f8cbfa9a-920e-4e31-ae5f-fca3b1cef4f7");
BLEUnsignedIntCharacteristic moistureCharacteristic("29c1083c-5166-433c-9b7c-98658c826968", BLERead | BLENotify);

BLEService ledService("f5a38368-9851-41cc-b49e-6ad0bba76f9b");
BLEByteCharacteristic ledFlagCharacteristic("eac630d2-9e86-4005-b7b9-6f6955f7ec10", BLERead | BLEWrite | BLENotify);

BLEService disconnectService("8b038794-1cfa-472c-a1f7-4e2d7b6ad2a4");
BLEByteCharacteristic disconnectFlagCharacteristic("5434aa28-4300-4d09-9ab2-79b70ba8ef5d", BLERead | BLEWrite | BLENotify);
// -----------------------------------------------------------------------------

// ------------------------------Global Variables-------------------------------
int num_light_readings = 0;
int num_hygrometer_readings = 0;
int num_bme_readings = 0;

unsigned int light_readings = 0;
unsigned int hygrometer_readings = 0;
float temperature_readings = 0.0;
float humidity_readings = 0.0;
unsigned long pressure_readings = 0;
unsigned long gas_resistance_readings = 0;

unsigned long current_millis = 0;
unsigned long previous_light_reading_millis = 0;
unsigned long previous_hygrometer_reading_millis = 0;
unsigned long previous_bme_reading_millis = 0;
unsigned long previous_writing_millis = 0;
unsigned long pairing_mode_start_millis = 0;
unsigned long next_led_change_millis = 0;

int pairing_mode = 0;
int connected = 0;
int warning_on = 0;
int unclean_disconnect = 0;
String last_connected;

int color = PURPLE;

int led_on = 0;
int blink_on = 0;
int num_blinks = 0;
int current_blinks = 0;

char device_name[18];

int dipSwitchPins[] = {D12, D11, D10, D9, D8, D7, D6, D5};
// -----------------------------------------------------------------------------

Adafruit_BME680 bme;

void startup_sound() {
  tone(PIEZO_BUZZER, 220, 300);
  delay(350);
  tone(PIEZO_BUZZER, 440, 300);
  delay(350);
  tone(PIEZO_BUZZER, 880, 300);
  delay(350);
}

void connect_sound() {
  tone(PIEZO_BUZZER, 440, 200);
  delay(240);
  tone(PIEZO_BUZZER, 880, 350);
  delay(400);
}

void disconnect_sound() {
  tone(PIEZO_BUZZER, 880, 200);
  delay(240);
  tone(PIEZO_BUZZER, 440, 350);
  delay(400);
}
