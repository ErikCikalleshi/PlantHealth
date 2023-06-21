#include "sensor.h"

#include "global.h"
#include <Arduino.h>
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BME680.h>

// function declarations for functions that are not declared in the header as
// they are not needed outside of this file
void read_light_sensor();
void read_hygrometer();
void read_bme();

/**
 * setup function for Hygrometer, Phototransistor and BME688 Air Sensor
 * 
 * For the BME688, oversampling and filtering is set
*/
void sensor_setup() {
  pinMode(HYGROMETER, INPUT);
  pinMode(PHOTOTRANSISTOR, INPUT);

  if (!bme.begin()) {
    Serial.println(F("Could not find a valid BME680 sensor, check wiring!"));
    return;
  }

  bme.setTemperatureOversampling(BME680_OS_8X);
  bme.setHumidityOversampling(BME680_OS_2X);
  bme.setPressureOversampling(BME680_OS_4X);
  bme.setIIRFilterSize(BME680_FILTER_SIZE_3);
  bme.setGasHeater(320, 150); // 320*C for 150 ms
}


/**
 * function that calls the read functions of all sensors
*/
void read_sensors() {
  read_light_sensor();
  read_hygrometer();
  read_bme();
}

/**
 * function that reads light sensor value, if the last time it has been read was
 * more than LIGHT_READING_INTERVAL milliseconds ago.
*/
void read_light_sensor() {
  if (current_millis - previous_light_reading_millis >= LIGHT_READING_INTERVAL) {
    unsigned int light = analogRead(PHOTOTRANSISTOR);
    light_readings += light;
    num_light_readings++;
    previous_light_reading_millis = current_millis;
  }
}

/**
 * function that reads hygrometer value, if the last time it has been read was
 * more than HYGROMETER_READING_INTERVAL milliseconds ago.
*/
void read_hygrometer() {
  if (current_millis - previous_hygrometer_reading_millis >= HYGROMETER_READING_INTERVAL) {
    unsigned int moisture = analogRead(HYGROMETER);
    hygrometer_readings += moisture;
    num_hygrometer_readings++;
    previous_hygrometer_reading_millis = current_millis;
  }
}

/**
 * function that reads air sensor values, if the last time it has been read was
 * more than BME_READING_INTERVAL milliseconds ago.
*/
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

    float temperature = bme.temperature;
    float humidity = bme.humidity ;
    unsigned long pressure = bme.pressure;
    unsigned long gas_resistance = bme.gas_resistance;

    temperature_readings += temperature;
    humidity_readings += humidity;
    pressure_readings += pressure;
    gas_resistance_readings += gas_resistance;

    num_bme_readings++;

    previous_bme_reading_millis = current_millis;
  }
}

/**
 * function that calculates the average of the accumulated sensor readings and
 * writes it to the corresponding characteristics, if the last time was more
 * than SENDING_INTERVAL milliseconds ago.
*/
void write_sensor_data() {
  if (current_millis - previous_writing_millis >= SENDING_INTERVAL) {
    unsigned int light_value = light_readings / num_light_readings;
    unsigned int moisture_value = hygrometer_readings / num_hygrometer_readings;
    short temperature_value = temperature_readings * 100 / num_bme_readings;
    unsigned short humidity_value = humidity_readings * 100 / num_bme_readings;
    unsigned long pressure_value = pressure_readings * 10 / num_bme_readings;
    float gas_resistance_value = gas_resistance_readings / num_bme_readings;
    
    lightIntensityCharacteristic.writeValue(light_value);
    moistureCharacteristic.writeValue(moisture_value);
    temperatureCharacteristic.writeValue(temperature_value);
    humidityCharacteristic.writeValue(humidity_value);
    pressureCharacteristic.writeValue(pressure_value);
    vocCharacteristic.writeValue(gas_resistance_value);

    Serial.println();
    Serial.println("----Data Written To Characteristics-----");

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
    Serial.println(F("  *0.01 *C"));

    Serial.print(F("Pressure = "));
    Serial.print(pressure_value);
    Serial.println(F(" * 0.1 Pa"));

    Serial.print(F("Humidity = "));
    Serial.print(humidity_value);
    Serial.println(F(" *0.01 %"));

    Serial.print(F("Gas = "));
    Serial.print(gas_resistance_value);
    Serial.println(F(" KOhms"));

    Serial.print("Number of BME readings: ");
    Serial.println(num_bme_readings);

    Serial.println("----------------------------------------");
    Serial.println();

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