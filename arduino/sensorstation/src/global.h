#ifndef GLOBAL_H
#define GLOBAL_H

#include <ArduinoBLE.h>
#include "Adafruit_BME680.h"

// ----------------------------Interval Definitions-----------------------------
#define LIGHT_READING_INTERVAL 100      // 100 ms
#define HYGROMETER_READING_INTERVAL 100 // 100 ms
#define BME_READING_INTERVAL 1000       // 500 ms
#define SENDING_INTERVAL 10000          // 10 s
#define BLE_PAIRING_TIME 300000         // 5 min
#define BLINK_INTERVAL 500              // 500 ms
#define BLINK_PAUSE 3000                // 3 s
// -----------------------------------------------------------------------------


// ------------------------------Pin Definitions--------------------------------
#define GREEN_LED A0
#define BLUE_LED A1
#define RED_LED A2

#define PHOTOTRANSISTOR A6
#define HYGROMETER A7

#define WARNING_BUTTON D2
#define WARNING_BUTTON_MODE FALLING

#define PAIRING_BUTTON D3
#define PAIRING_BUTTON_MODE FALLING

#define PIEZO_BUZZER D4

extern int dipSwitchPins[];
// -----------------------------------------------------------------------------

// -----------------BLE Services + Characteristics Definitions------------------
extern BLEService airSensorService;
extern BLEShortCharacteristic temperatureCharacteristic;
extern BLEUnsignedShortCharacteristic humidityCharacteristic;
extern BLEUnsignedLongCharacteristic pressureCharacteristic;
extern BLEFloatCharacteristic vocCharacteristic;

extern BLEService lightSensorService;
extern BLEUnsignedIntCharacteristic lightIntensityCharacteristic;

extern BLEService hygrometerService;
extern BLEUnsignedIntCharacteristic moistureCharacteristic;

extern BLEService ledService;
extern BLEByteCharacteristic ledFlagCharacteristic;

extern BLEService disconnectService;
extern BLEByteCharacteristic disconnectFlagCharacteristic;
// -----------------------------------------------------------------------------

extern Adafruit_BME680 bme;

enum Color { YELLOW, RED, GREEN, PURPLE, BLUE, OFF };

// ------------------------------Global Variables-------------------------------
extern int num_light_readings;
extern int num_hygrometer_readings;
extern int num_bme_readings;

extern unsigned int light_readings;
extern unsigned int hygrometer_readings;
extern float temperature_readings;
extern float humidity_readings;
extern unsigned long pressure_readings;
extern unsigned long gas_resistance_readings;

extern unsigned long current_millis;
extern unsigned long previous_light_reading_millis;
extern unsigned long previous_hygrometer_reading_millis;
extern unsigned long previous_bme_reading_millis;
extern unsigned long previous_writing_millis;
extern unsigned long pairing_mode_start_millis;
extern unsigned long next_led_change_millis;

extern int pairing_mode;
extern int connected;
extern int warning_on;
extern int unclean_disconnect;
extern String last_connected;

extern int color;

extern int led_on;
extern int blink_on;
extern int num_blinks;
extern int current_blinks;

extern char device_name[18];
// -----------------------------------------------------------------------------

void startup_sound();
void connect_sound();
void disconnect_sound();

#endif