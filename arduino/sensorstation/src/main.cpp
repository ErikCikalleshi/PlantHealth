#include <Arduino.h>
#include <ArduinoBLE.h>
#include "global.h"
#include "bluetooth.h"
#include "io.h"
#include "sensor.h"

// the setup function runs once when you press reset or power the board
void setup() {
  Serial.begin(9600);
  
  button_setup();
  BLE_setup();
  sensor_setup();

  startup_sound();
}

// the loop function runs over and over again forever
void loop() {
  current_millis = millis();
  BLE.poll();
  read_sensors();
  write_sensor_data();
  update_led();
  check_pairing_mode();
}
