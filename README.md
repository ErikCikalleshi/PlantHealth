# g2t4


## Getting started

### Prerequisites

##### WebApp

Node.js (Version 18) and npm are required to run the application. To install them, follow the instructions on the [Node.js website](https://nodejs.org/en/download/).

MySQL (Version 8) is required to run the application. To install it, follow the instructions on the [MySQL website](https://dev.mysql.com/downloads/installer/). Run the database/init_database.sql script to create the user for this application and grant it all rights on the database. Be sure that the username/password + database name in the script match the ones in the application.properties file.

##### SensorStation

The SensorStation is wired according to the [circuit diagram](https://git.uibk.ac.at/informatik/qe/swess23/group2/g2t4/-/raw/main/Schaltplan_V3.png), only the connections for the DIP switch are opposite to the circuit diagram. So the left most pin of the DIP switch is actually connected to D5 and the right most pin to D12, not vice versa as indicated in the circuit diagram.

Alternatively, you can change the PIN Assignments in the `Pin Definitions` section at the start of the main.cpp file to make the program work with a different wiring. To guarantee the working of the buttons, it is important that they are connected to Pins D2 and D3, because they are the only ones that support `attachInterrupt()` (according to the Arduino Documentation). If you have these buttons wired any other way than with a pullup resistor, you can also change the mode of the interrupt in the `Pin Definitions` section. Also, the SDA and SCL Pins of the BME688 always need to be connected to Pins A4 and A5 respectively. Apart from that you can change the wiring how you like, as long as connections on digital pins stay on digital pins, and the same for analog pins.

To run the code on the Arduino, open the `arduino/sensorstation` directory as a PlatformIO project and compile/upload it from there. Alternatively if that doesn't work you can copy the files of the `arduino/sensorstation/src` directory into a new ArduinoIDE sketch, rename `main.cpp` to `<sketch name>.ino` and upload it using ArduinoIDE. (Make sure to install the required Libraries)

##### Accesspoint

First of all you need to have python installed. If you don't have it installed, you can download it from [here](https://www.python.org/downloads/). We are using MongoDB and the installation on the raspberry pi is not that trivial. Therefore we **recommend** to install MongoDB locally on your PC [here](https://www.mongodb.com/try/download/community). You can download MongoDB Compass from [here](https://www.mongodb.com/try/download/compass) and afterwards you can launch it. You can connect to your local MongoDB instance by providing the necessary connection details. By default, MongoDB runs on port 27017. If you decide to try it out on the raspberry pi, you can follow the instructions from [here](https://www.mongodb.com/developer/products/mongodb/mongodb-on-raspberry-pi/). Make sure that if you are planning to do it on the raspberry pi, you have installed a Ubuntu 20.04 -Raspberry Pi OS. If there are any problems with installing mongodb on the raspberry pi, you can visit [this page](https://www.donskytech.com/how-to-install-mongodb-on-raspberry-pi/).

To install the python packages, you can execute installation_script.sh, if you are on linux. If you are on windows, you can execute the installation_windows.bat. If something goes wrong with the installation, you can visit the readme.md and follow the instructions there.

Now we have to specifiy the settings for the accesspoint. This can be done by executing local_setup.py in the accesspoint directory. From there you can either perform a quick setup or a custom setup. The quick setup will ask you just for the IP-Address of the backend (Spring Application). The default port of the backend will be 9000. If you decide to perform the longer setup, than you will be asked to specify all the neccecary settings. The settings will be saved in a file called settings.json. If you want to change the settings, you can do it manually in the settings.json file.

Now you should be ready to execute the main.py file.

Note that the connection between the accesspoint and SensorStation could take longer. DO NOT FORGET TO TURN ON BLUETOOTH.

### Starting the application

##### WebApp

Before you start you need to change some settings in the config for it to work on your system:

backend/src/main/java/at/qe/backend/configs/WebMvcConfig.java\
insert your IPv4 local address\
`.allowedOriginPatterns("http://127.0.0.1:5173", "http://localhost:5173", "http://<your IPv4 local address>:5173")`

frontend/src/services/index.ts\
insert your IPv4 local address\
`export const API_BASE_URL: string = 'http://<your IPv4 local address>:9000';`

Run the backend application via mvn spring-boot:run in the backend folder. The application will be available at [http://localhost:9000](http://localhost:9000). (Port is defined in application.properties)

To start the frontend application, navigate to the frontend folder and run the following commands:

```
npm i
npm run dev
```

It should show two options in the console Local and Network where you can access the webpage, keep this in mind for later because it is relevant for the image upload.

##### SensorStation

Once the Arduino is connected to a power source, the program is running. It is best to press the little reset button on the board once at the beginning to reset the program and let the setup() function again. The SensorStation is ready when the startup sound plays and the LED is glowing pink. If you want to receive extra information about the 

To be able to connect to the SensorStation via Bluetooth LE, you need to put it into Pairing Mode by pressing the corresponding button. Pairing Mode is activated when the LED blinks blue. When it successfully connects, a short sound is played and the LED turns green.

### Integrating new Accesspoints and Sensorstations

##### Accesspoint

To add a new Accesspoint to the system, you first need to create the Accesspoint in the WebApp. For that you need to be logged in as Admin, then you can go to the Access Points Page and press "add Access Point". There, type in the necessary Data, check the `Publish` checkbox and press save.Yyou can see that the WebApp has given it an ID. Now make sure to set the ID in the settings.json file on the Accesspoint to that shown on the WebApp. Now, when running the main.py script on the Accesspoint it connects to the Webserver, which is shown by the `Online` flag in the WebApp.

##### Sensorstation

To add a new Sensorstation to the system, log in to the WebApp as Admin and click "add Sensorstation" on the page of the Accesspoint you want the Sensorstation to connect to. Type in the data, check the `Publish` checkbox and press save. Set the DIP switch on the Sensorstation to the ID the System has given it. Restart the Sensorstation by pressing the little button on the Arduino and start the Pairing Mode by pressing the Pairing Mode Button. If the Sensorstation is in reach of the Accesspoint, they should now connect. The LED on the Sensorstation should turn green, and a short sound is played. After a short time, the Measurements show up on the Webapp.
