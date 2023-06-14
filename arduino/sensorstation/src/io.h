#ifndef IO_H
#define IO_H

void button_setup();

void check_led_flag();
void update_led();
void set_led(int color);

void stop_blink_handler();
void pairing_mode_handler();
#endif