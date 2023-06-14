#include "io.h"

#include <Arduino.h>
#include "global.h"

/**
  * setup function for the two buttons
  * 
  * It attaches interrupts to the buttons, meaning the 'handler' functions are
  * automatically called when the buttons are pressed.
 */
void button_setup() {
  attachInterrupt(digitalPinToInterrupt(WARNING_BUTTON), stop_blink_handler, WARNING_BUTTON_MODE);
  attachInterrupt(digitalPinToInterrupt(PAIRING_BUTTON), pairing_mode_handler, PAIRING_BUTTON_MODE);
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
  
  analogWrite(RED_LED, red_val);
  analogWrite(GREEN_LED, green_val);
  analogWrite(BLUE_LED, blue_val);
}

void stop_blink_handler() {
  if (warning_on) {
    blink_on = 0;
    color = GREEN;
    warning_on = 0;
    ledFlagCharacteristic.writeValue(0x00);
  }
}

void pairing_mode_handler() {
  if (!connected) {
    pairing_mode = 1;
    pairing_mode_start_millis = current_millis;
    color = BLUE;
    num_blinks = 50000;
    blink_on = 1;
  }
}
