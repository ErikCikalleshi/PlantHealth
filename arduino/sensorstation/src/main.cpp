#include <Arduino.h>
#include <ArduinoBLE.h>
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include "Adafruit_BME680.h"

#define SEALEVELPRESSURE_HPA (1013.25)


Adafruit_BME680 bme;
int dipSwitchPins[] = {D10, D9, D8, D7, D6, D5, D4, D3};


void sensor_setup();

void BLE_setup();

unsigned int read_light_sensor();

unsigned int read_hygrometer();

void read_air_sensor();

int get_UUID();

// the setup function runs once when you press reset or power the board
void setup() {
  Serial.begin(9600);
  while(!Serial);
  Serial.println("Serial started");
  int UUID = get_UUID();
  Serial.println(UUID);
  BLE_setup();
  
  sensor_setup();
}

// the loop function runs over and over again forever
void loop() {
  read_light_sensor();
  read_hygrometer();
  delay(2000);
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

void BLE_setup() {
  if (!BLE.begin()) {
    Serial.println("Starting BLE failed!");

    return;
  }
  Serial.println("BLE started");

  BLE.setLocalName("SensorStation G2T4");
  BLE.setDeviceName("SensorStation G2T4");

  BLE.advertise();
}

unsigned int read_light_sensor() {
  unsigned int light = analogRead(A6);
  Serial.print("Light: ");
  Serial.println(light);
  return light;
}

unsigned int read_hygrometer() {
  unsigned int audio = analogRead(A7);
  Serial.print("Hygrometer: ");
  Serial.println(audio);
  return audio;
}

void read_air_sensor() {
  unsigned long endTime = bme.beginReading();
  if (endTime == 0) {
    Serial.println(F("Failed to begin reading :("));
    return;
  }
  Serial.print(F("Reading started at "));
  Serial.print(millis());
  Serial.print(F(" and will finish at "));
  Serial.println(endTime);

  if (!bme.endReading()) {
    Serial.println(F("Failed to complete reading :("));
    return;
  }
  Serial.print(F("Reading completed at "));
  Serial.println(millis());

  Serial.print(F("Temperature = "));
  Serial.print(bme.temperature);
  Serial.println(F(" *C"));

  Serial.print(F("Pressure = "));
  Serial.print(bme.pressure / 100.0);
  Serial.println(F(" hPa"));

  Serial.print(F("Humidity = "));
  Serial.print(bme.humidity);
  Serial.println(F(" %"));

  Serial.print(F("Gas = "));
  Serial.print(bme.gas_resistance / 1000.0);
  Serial.println(F(" KOhms"));

  Serial.print(F("Approx. Altitude = "));
  Serial.print(bme.readAltitude(SEALEVELPRESSURE_HPA));
  Serial.println(F(" m"));
}

int get_UUID() {
  for (int i = 0; i < 8; i++) {
    pinMode(dipSwitchPins[i], INPUT_PULLDOWN);
  }
  int UUID = 0;
  for (int i = 0; i < 8; i++) {
    UUID += digitalRead(dipSwitchPins[i]) << i;
  }
  return UUID;
}