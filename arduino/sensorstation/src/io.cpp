#include "io.h"

#include <Arduino.h>
#include "global.h"

// function declarations for functions that are not declared in the header as
// they are not needed outside of this file
void set_led(int color);
void stop_blink_handler();
void pairing_mode_handler();

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

/**
 * function that checks current LED variable states, and changes it accordingly
*/
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

/**
 * function that sets LED to color given as argument
*/
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

/**
 * function that handles disabling of warnings. It is automatically called when
 * the "disable warning" button is pressed.
*/
void stop_blink_handler() {
  if (warning_on) {
    blink_on = 0;
    color = GREEN;
    warning_on = 0;
    ledFlagCharacteristic.writeValue(0x00);
  }
}

/**
 * funtion that starts pairing mode. It is called when the "pairing mode" button
 * is pressed
*/
void pairing_mode_handler() {
  if (!connected) {
    pairing_mode = 1;
    pairing_mode_start_millis = current_millis;
    color = BLUE;
    num_blinks = 50000;
    blink_on = 1;
  }
}
