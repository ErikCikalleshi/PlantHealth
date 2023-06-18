# This script sends random measurements for all sensors in the database until the user quits it
import time

import json
import aiohttp
import mysql.connector
import datetime
import random
import asyncio

# Connect to the MySQL database
db = mysql.connector.connect(host="localhost", user="springuser", passwd="passwd", db="db_planthealth")
cursor = db.cursor()

# Retrieve all the sensor data along with their respective greenhouse and access point information
cursor.execute(
    "SELECT sensor.id, greenhouse.id_in_cluster, access_point.uuid, sensor.limit_upper, sensor.limit_lower, sensor.sensor_type, greenhouse_uuid FROM sensor JOIN greenhouse ON sensor.greenhouse_uuid = greenhouse.uuid JOIN access_point ON greenhouse.accesspoint_uuid = access_point.uuid")
sensor_data = cursor.fetchall()
cursor.reset()

cursor.execute("SELECT MAX(measurement_date) FROM measurement")
result = cursor.fetchone()
if result is not None:
    measurement_date = result[0]
    if measurement_date is None:
        measurement_date = datetime.datetime.now()
else:
    measurement_date = datetime.datetime.now()

# Set the time interval between data sending in seconds
interval_seconds = 10

# Set the maximum percentage change allowed for sensor data
max_change_percent = 10.0

# Simulate accesspoint transmission interval
accesspointtransmissionintervalSeconds = 30

previous_sensor_values = {}

max_iteration = 10000

# Select random greenhouses to be offline
max_uuid_access_points = max(row[2] for row in sensor_data)
max_uuid_greenhouse = max(row[6] for row in sensor_data)
offlineAccessPoints = random.sample(range(1, max_uuid_access_points), 1)
offlineGreenhouses = random.sample(range(1, max_uuid_greenhouse), 5)
exceedLimitsSensor = random.sample(range(1, len(sensor_data)), 5)


async def send_data(measurement_data, greenhouse_uuid):
    async with aiohttp.ClientSession() as session:
        async with session.post("http://172.20.10.2:9000/api/measurements", auth=aiohttp.BasicAuth("admin", "passwd"),
                                json=measurement_data) as response:
            # print("Sending data: " + str(measurement_data))
            response_text = await response.text()

            if response.status != 200:
                # Add the greenhouse to the offline list if the request fails (e.g. greenhouse/access point is disabled)
                offlineGreenhouses.append(greenhouse_uuid)
            else:
                print("Measurement data: ", measurement_data)
                original_data = json.loads(json.dumps(measurement_data))
                response_data = json.loads(response_text)
                print("Original date: " + original_data["date"])
                print("Response date: " + response_data["date"])
                print("-"*50)

iteration = 0
# Infinite loop to send data continuously
while iteration < max_iteration:
    # Loop through all the sensor data
    tasks = []

    for row in sensor_data:
        sensor_id = row[0]
        greenhouse_id = row[1]
        access_point_uuid = row[2]
        limit_max = row[3]
        limit_min = row[4]
        sensorType = row[5]
        # Skip sensors that are in offline greenhouses or access points
        if row[6] in offlineGreenhouses or row[2] in offlineAccessPoints:
            continue

        avg = (limit_max + limit_min) / 2
        limit_max_random = limit_max + avg * 0.01
        limit_min_random = limit_min - avg * 0.01
        limitExceededBy = 0.0
        # Generate a random value for the sensor
        # Start anywhere within the limit + some wiggling room for errors
        # Save the value in a dictionary to calculate the maximum change allowed on the next iteration
        if sensor_id in previous_sensor_values:
            prev_value = previous_sensor_values[sensor_id]
            max_change = prev_value / 100 * max_change_percent
            if sensor_id in exceedLimitsSensor:
                min_value = max(prev_value - max_change, limit_min_random)
                max_value = min(prev_value + max_change, limit_max_random)
            else:
                min_value = max(prev_value - max_change, limit_min)
                max_value = min(prev_value + max_change, limit_max)
            value = random.uniform(min_value, max_value)
        else:
            value = random.uniform(limit_min, limit_max)
            # print("First value: " + str(value) + " for sensor: " + str(sensor_id) + "(limit_min: " + str(limit_min) + ", limit_max: " + str(limit_max) + ")" )

        previous_sensor_values[sensor_id] = value

        if value > limit_max:
            limitExceededBy = value - limit_max
        elif value < limit_min:
            limitExceededBy = value - limit_min

        if limitExceededBy > 0:
            print("Limit exceeded by: " + str(limitExceededBy) + " for sensor: " + str(sensor_id))

        new_measurement_date = (measurement_date + datetime.timedelta(
            seconds=accesspointtransmissionintervalSeconds * iteration)).isoformat()
        # Send the data to the API
        # date= datetime.datetime.utcnow().isoformat()
        date = datetime.datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%S.%f")[:-3] + "Z"
        # print("date:", date)
        measurement_data = {
            "greenhouseID": greenhouse_id,
            "accesspointUUID": access_point_uuid,
            "value": value,
            "sensorType": sensorType,
            "date": date,
            "limitExceededBy": limitExceededBy,
            "upperLimit": limit_max,
            "lowerLimit": limit_min
        }
        # print("date:", date)
        # print("Now ", datetime.datetime.now())
        # print("ISO ", datetime.datetime.now().isoformat())
        # print()
        # send_data(measurement_data, row[6])

        tasks.append(asyncio.ensure_future(send_data(measurement_data, row[6])))
        time.sleep(0.01)
        # while iteration>1 and len(tasks) > 40:
        #     print(len(tasks))
        #     time.sleep(0.1)
        #     asyncio.get_event_loop().run_until_complete(asyncio.gather(*tasks))

    loop = asyncio.get_event_loop()
    loop.run_until_complete(asyncio.gather(*tasks))

    print("Iteration: " + str(iteration) + " finished")
    # time.sleep(30)

    iteration += 1
