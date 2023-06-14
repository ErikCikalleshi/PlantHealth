#ifndef SENSOR_H
#define SENSOR_H

void sensor_setup();

void read_sensors();
void read_light_sensor();
void read_hygrometer();
void read_bme();

void write_sensor_data();

#endif