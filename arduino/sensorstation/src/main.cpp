#include <Arduino.h>
#include <ArduinoBLE.h>
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include "Adafruit_BME680.h"

#define SEALEVELPRESSURE_HPA (1013.25)

BLEService environmentalSensingService("181A");
BLECharacteristic temperatureCharacteristic("2A6E", BLERead | BLENotify, 4);
BLECharacteristic humidityCharacteristic("2A6F", BLERead | BLENotify, 4);
BLECharacteristic pressureCharacteristic("2A6D", BLERead | BLENotify, 4);
BLECharacteristic vocCharacteristic("FFD5", BLERead | BLENotify, 4);

Adafruit_BME680 bme;
int dipSwitchPins[] = {D10, D9, D8, D7, D6, D5, D4, D3};

void blePeripheralConnectHandler(BLEDevice central);

void blePeripheralDisconnectHandler(BLEDevice central);

void sensor_setup();

void BLE_setup();

void read_light_sensor(unsigned int *light);

void read_hygrometer(unsigned int *moisture);

int read_air_sensor(int *temperature, int *humidity, int *pressure, int *gas_resistance);

int get_ID();

// the setup function runs once when you press reset or power the board
void setup() {
  Serial.begin(9600);
  while(!Serial);
  Serial.println("Serial started");
  int ID = get_ID();
  Serial.println(ID);
  BLE_setup();
  
  sensor_setup();
}

// the loop function runs over and over again forever
void loop() {
  BLE.poll();
  unsigned int light;
  unsigned int moisture;
  int temperature;
  int humidity;
  int pressure;
  int gas_resistance;
  read_light_sensor(&light);
  read_hygrometer(&moisture);
  read_air_sensor(&temperature, &humidity, &pressure, &gas_resistance);
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

void blePeripheralConnectHandler(BLEDevice central) {
  Serial.println("Connected event, central: ");
  Serial.println(central.address());
}

void blePeripheralDisconnectHandler(BLEDevice central) {
  Serial.println("Disconnected event, central: ");
  Serial.println(central.address());
}

void BLE_setup() {
  if (!BLE.begin()) {
    Serial.println("Starting BLE failed!");

    return;
  }
  Serial.println("BLE started");

  BLE.setLocalName("SensorStation G2T4");
  BLE.setDeviceName("SensorStation G2T4");

  BLE.setAdvertisedService(environmentalSensingService);

  BLEDescriptor temperatureDescriptor("2901", "Temperature in degrees Celsius");
  temperatureCharacteristic.addDescriptor(temperatureDescriptor);

  BLEDescriptor humidityDescriptor("2901", "Humidity in percent");
  humidityCharacteristic.addDescriptor(humidityDescriptor);

  BLEDescriptor pressureDescriptor("2901", "Pressure in hPa");
  pressureCharacteristic.addDescriptor(pressureDescriptor);

  BLEDescriptor vocDescriptor("2901", "Gas Resistance in kOhm");
  vocCharacteristic.addDescriptor(vocDescriptor);

  environmentalSensingService.addCharacteristic(temperatureCharacteristic);
  environmentalSensingService.addCharacteristic(humidityCharacteristic);
  environmentalSensingService.addCharacteristic(pressureCharacteristic);
  environmentalSensingService.addCharacteristic(vocCharacteristic);
  BLE.addService(environmentalSensingService);

  BLE.setEventHandler(BLEConnected, blePeripheralConnectHandler);
  BLE.setEventHandler(BLEDisconnected, blePeripheralDisconnectHandler);

  BLE.advertise();

  /*
  BLEDevice central;
  while(!central) {
     central = BLE.central();
  }
  */
}

void read_light_sensor(unsigned int *light) {
  *light = analogRead(A6);
  Serial.print("Light: ");
  Serial.println(*light);
}

void read_hygrometer(unsigned int *moisture) {
  *moisture = analogRead(A7);
  Serial.print("Moisture: ");
  Serial.println(*moisture);
}

int read_air_sensor(int *temperature, int *humidity, int *pressure, int *gas_resistance) {
  unsigned long endTime = bme.beginReading();
  if (endTime == 0) {
    Serial.println(F("Failed to begin reading :("));
    return EXIT_FAILURE;
  }

  if (!bme.endReading()) {
    Serial.println(F("Failed to complete reading :("));
    return EXIT_FAILURE;
  }

  *temperature = bme.temperature * 100;
  *humidity = bme.humidity * 100;
  *pressure = bme.pressure * 10;
  *gas_resistance = bme.gas_resistance;

  // Update the BLE characteristics with the sensor values
  temperatureCharacteristic.writeValue((byte*)temperature, sizeof(*temperature));
  humidityCharacteristic.writeValue((byte*)humidity, sizeof(*humidity));
  pressureCharacteristic.writeValue((byte*)pressure, sizeof(*pressure));
  vocCharacteristic.writeValue((byte*)gas_resistance, sizeof(*gas_resistance));

  Serial.print(F("Temperature = "));
  Serial.print(*temperature);
  Serial.println(F(" *C"));

  Serial.print(F("Pressure = "));
  Serial.print(*pressure / 100.0);
  Serial.println(F(" hPa"));

  Serial.print(F("Humidity = "));
  Serial.print(*humidity);
  Serial.println(F(" %"));

  Serial.print(F("Gas = "));
  Serial.print(*gas_resistance / 1000.0);
  Serial.println(F(" KOhms"));

  return EXIT_SUCCESS;
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