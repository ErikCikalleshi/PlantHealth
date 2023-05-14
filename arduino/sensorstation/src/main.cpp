#include <Arduino.h>
#include <ArduinoBLE.h>
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include "Adafruit_BME680.h"

#define SEALEVELPRESSURE_HPA (1013.25)

#define LIGHT_READING_INTERVAL 100 // 100 ms
#define HYGROMETER_READING_INTERVAL 100 // 100 ms
#define BME_READING_INTERVAL 1000 // 500 ms
#define SENDING_INTERVAL 10000 // 10 s
#define BLE_PAIRING_TIME 300000 // 5 min
#define BLINK_INTERVAL 500
#define BLINK_PAUSE 3000

BLEService airSensorService("181A");
BLEIntCharacteristic temperatureCharacteristic("2A6E", BLERead | BLENotify);
BLEUnsignedIntCharacteristic humidityCharacteristic("2A6F", BLERead | BLENotify);
BLEUnsignedIntCharacteristic pressureCharacteristic("2A6D", BLERead | BLENotify);
BLEFloatCharacteristic vocCharacteristic("2BD3", BLERead | BLENotify);

BLEService lightSensorService("8444401e-ffb9-424d-9dc1-c2bc273b47b5");
BLEUnsignedIntCharacteristic lightIntensityCharacteristic("4ab3244f-d156-4e76-a329-6de917bdc8f9", BLERead | BLENotify);

BLEService hygrometerService("f8cbfa9a-920e-4e31-ae5f-fca3b1cef4f7");
BLEUnsignedIntCharacteristic moistureCharacteristic("29c1083c-5166-433c-9b7c-98658c826968", BLERead | BLENotify);

BLEService ledService("f5a38368-9851-41cc-b49e-6ad0bba76f9b");
BLEByteCharacteristic ledFlagCharacteristic("eac630d2-9e86-4005-b7b9-6f6955f7ec10", BLERead | BLEWrite);

Adafruit_BME680 bme;

int dipSwitchPins[] = {D12, D11, D10, D9, D8, D7, D6, D5};
enum Color { YELLOW, RED, GREEN, PURPLE, BLUE, OFF };

int num_light_readings = 0;
int num_hygrometer_readings = 0;
int num_bme_readings = 0;

unsigned int light_readings = 0;
unsigned int hygrometer_readings = 0;
int temperature_readings = 0;
int humidity_readings = 0;
int pressure_readings = 0;
float gas_resistance_readings = 0.0;

unsigned long current_millis = 0;
unsigned long previous_light_reading_millis = 0;
unsigned long previous_hygrometer_reading_millis = 0;
unsigned long previous_bme_reading_millis = 0;
unsigned long previous_writing_millis = 0;

int pairing_mode = 0;
unsigned long pairing_mode_start = 0;
int led_on = 0;
int color = PURPLE;
int num_blinks = 0;
int current_blinks = 0;
int blink_on = 0;
int warning_on = 0;
int connected = 0;

unsigned long next_led_change_millis = 0;

void blePeripheralConnectHandler(BLEDevice central);

void blePeripheralDisconnectHandler(BLEDevice central);

void button_setup();
void sensor_setup();
void BLE_setup();

void read_sensors();
void read_light_sensor();
void read_hygrometer();
void read_bme();

void write_sensor_data();

void check_led_flag();
void update_led();

void stop_blink_handler();
void pairing_mode_handler();
void check_pairing_mode();
int get_ID();

// the setup function runs once when you press reset or power the board
void setup() {
  Serial.begin(9600);
  while(!Serial);
  Serial.println("Serial started");
  int ID = get_ID();
  Serial.println(ID);
  
  button_setup();
  BLE_setup();
  sensor_setup();
}

// the loop function runs over and over again forever
void loop() {
  current_millis = millis();
  BLE.poll();
  read_sensors();
  write_sensor_data();
  check_led_flag();
  update_led();
  check_pairing_mode();
}

void sensor_setup() {
  pinMode(A7, INPUT);
  pinMode(A6, INPUT);

  if (!bme.begin()) {
    Serial.println(F("Could not find a valid BME680 sensor, check wiring!"));
    return;
  }

  // Set up oversampling and filter initialization
  bme.setTemperatureOversampling(BME680_OS_8X);
  bme.setHumidityOversampling(BME680_OS_2X);
  bme.setPressureOversampling(BME680_OS_4X);
  bme.setIIRFilterSize(BME680_FILTER_SIZE_3);
  bme.setGasHeater(320, 150); // 320*C for 150 ms
}

void blePeripheralConnectHandler(BLEDevice central) {
  pairing_mode = 0;
  color = GREEN;
  blink_on = 0;
  led_on = 1;
  connected = 1;
  Serial.println("Connected event, central: ");
  Serial.println(central.address());
}

void blePeripheralDisconnectHandler(BLEDevice central) {
  color = PURPLE;
  blink_on = 0;
  led_on = 1;
  connected = 0;
  Serial.println("Disconnected event, central: ");
  Serial.println(central.address());
}

void button_setup() {
  attachInterrupt(digitalPinToInterrupt(D2), stop_blink_handler, FALLING);
  attachInterrupt(digitalPinToInterrupt(D3), pairing_mode_handler, FALLING);
}

void BLE_setup() {
  if (!BLE.begin()) {
    Serial.println("Starting BLE failed!");

    return;
  }
  Serial.println("BLE started");

  BLE.setLocalName("SensorStation G2T4");
  BLE.setDeviceName("SensorStation G2T4");

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

  BLE.setEventHandler(BLEConnected, blePeripheralConnectHandler);
  BLE.setEventHandler(BLEDisconnected, blePeripheralDisconnectHandler);

  // BLE.advertise();
}

void read_sensors() {
  read_light_sensor();
  read_hygrometer();
  read_bme();
}

void read_light_sensor() {
  if (current_millis - previous_light_reading_millis >= LIGHT_READING_INTERVAL) {
    unsigned int light = analogRead(A6);
    light_readings += light;
    num_light_readings++;
    previous_light_reading_millis = current_millis;
  }
}

void read_hygrometer() {
  if (current_millis - previous_hygrometer_reading_millis >= HYGROMETER_READING_INTERVAL) {
    unsigned int moisture = analogRead(A7);
    hygrometer_readings += moisture;
    num_hygrometer_readings++;
    previous_hygrometer_reading_millis = current_millis;
  }
}

void read_bme() {
  if (current_millis - previous_bme_reading_millis >= BME_READING_INTERVAL) {
    unsigned long endTime = bme.beginReading();
    if (endTime == 0) {
      Serial.println(F("Failed to begin reading :("));
      return;
    }

    if (!bme.endReading()) {
      Serial.println(F("Failed to complete reading :("));
      return;
    }

    int temperature = bme.temperature;
    int humidity = bme.humidity ;
    int pressure = bme.pressure;
    float gas_resistance = bme.gas_resistance / 1000.0;

    temperature_readings += temperature;
    humidity_readings += humidity;
    pressure_readings += pressure;
    gas_resistance_readings += gas_resistance;

    num_bme_readings++;

    previous_bme_reading_millis = current_millis;
  }
}

void write_sensor_data() {
  if (current_millis - previous_writing_millis >= SENDING_INTERVAL) {
    unsigned int light_value = light_readings / num_light_readings;
    unsigned int moisture_value = hygrometer_readings / num_hygrometer_readings;
    int temperature_value = temperature_readings / num_bme_readings;
    int humidity_value = humidity_readings / num_bme_readings;
    int pressure_value = pressure_readings / num_bme_readings;
    float gas_resistance_value = gas_resistance_readings / num_bme_readings;
    
    lightIntensityCharacteristic.writeValue(light_value);
    moistureCharacteristic.writeValue(moisture_value);
    temperatureCharacteristic.writeValue(temperature_value);
    humidityCharacteristic.writeValue(humidity_value);
    pressureCharacteristic.writeValue(pressure_value);
    vocCharacteristic.writeValue(gas_resistance_value);

    Serial.print("Light: ");
    Serial.println(light_value);

    Serial.print("Number of light readings: ");
    Serial.println(num_light_readings);

    Serial.print("Moisture: ");
    Serial.println(moisture_value);

    Serial.print("Number of hygrometer readings: ");
    Serial.println(num_hygrometer_readings);

    Serial.print(F("Temperature = "));
    Serial.print(temperature_value);
    Serial.println(F(" *C"));

    Serial.print(F("Pressure = "));
    Serial.print(pressure_value / 100.0);
    Serial.println(F(" hPa"));

    Serial.print(F("Humidity = "));
    Serial.print(humidity_value);
    Serial.println(F(" %"));

    Serial.print(F("Gas = "));
    Serial.print(gas_resistance_value);
    Serial.println(F(" KOhms"));

    Serial.print("Number of BME readings: ");
    Serial.println(num_bme_readings);

    previous_writing_millis = current_millis;

    light_readings = 0;
    hygrometer_readings = 0;
    temperature_readings = 0;
    pressure_readings = 0;
    humidity_readings = 0;
    gas_resistance_readings = 0;

    num_bme_readings = 0;
    num_hygrometer_readings = 0;
    num_light_readings = 0;
  }
}

void check_led_flag() {
  if (ledFlagCharacteristic.written()) {
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
}

void update_led() {
  if (blink_on) {
    if (current_millis >= next_led_change_millis) {
      if (led_on) {
        set_led(OFF);
        led_on = 0;
        current_blinks++;
        if (current_blinks >= num_blinks) {
          next_led_change_millis = current_millis + BLINK_PAUSE;
          current_blinks = 0;
        } else {
          next_led_change_millis = current_millis + BLINK_INTERVAL;
        }
      } else {
        set_led(color);
        led_on = 1;
        next_led_change_millis = current_millis + BLINK_INTERVAL;
      }
    }
  } else {
    set_led(color);
  }
}

void set_led(int color) {
  int red_val, green_val, blue_val;
  switch (color) {
    case RED:
      red_val = 255;
      green_val = 0;
      blue_val = 0;
      break;
    case YELLOW:
      red_val = 255;
      green_val = 40;
      blue_val = 10;
      break;
    case GREEN:
      red_val = 0;
      green_val = 255;
      blue_val = 0;
      break;
    case BLUE:
      red_val = 0;
      green_val = 0;
      blue_val = 255;
      break;
    case PURPLE:
      red_val = 255;
      green_val = 0;
      blue_val = 150;
      break;
    case OFF:
      red_val = 0;
      green_val = 0;
      blue_val = 0;
      break;
    default:
      red_val = 0;
      green_val = 0;
      blue_val = 0;
  }
  
  analogWrite(A2, red_val);
  analogWrite(A0, green_val);
  analogWrite(A1, blue_val);
}

void stop_blink_handler() {
  if (warning_on) {
    blink_on = 0;
    color = GREEN;
    warning_on = 0;
  }
}

void pairing_mode_handler() {
  if (!connected) {
    pairing_mode = 1;
    pairing_mode_start = current_millis;
    // BLE.advertise();
    color = BLUE;
    num_blinks = 50000;
    blink_on = 1;
  }
}

void check_pairing_mode() {
  if (pairing_mode && (current_millis - pairing_mode_start >= BLE_PAIRING_TIME)) {
    pairing_mode = 0;
    color = PURPLE;
    blink_on = 0;
    led_on = 1;
  }
  if (pairing_mode) {
    BLE.advertise();
  } else {
    BLE.stopAdvertise();
  }
}

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