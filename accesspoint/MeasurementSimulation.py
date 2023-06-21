# This script sends random measurements for all sensors in the database until the user quits it
import aiohttp
import mysql.connector
import datetime
import random
import time
import asyncio
import requests

# Connect to the MySQL database
db = mysql.connector.connect(host="localhost", user="springuser", passwd="passwd", db="db_planthealth")
cursor = db.cursor()

real_greenhouse_id = 16
enable_asynchronous = False

# Retrieve all the sensor data along with their respective greenhouse and access point information
cursor.execute(
    "SELECT sensor.id, greenhouse.id_in_cluster, access_point.uuid, sensor.limit_upper, sensor.limit_lower, sensor.sensor_type, greenhouse_uuid FROM sensor JOIN greenhouse ON sensor.greenhouse_uuid = greenhouse.uuid JOIN access_point ON greenhouse.accesspoint_uuid = access_point.uuid")
sensor_data = cursor.fetchall()

# Set the time interval between data sending in seconds
interval_seconds = 10

# Set the maximum percentage change allowed for sensor data
max_change_percent = 10.0

previous_sensor_values = {}

# Select random greenhouses to be offline
max_uuid_access_points = max(row[2] for row in sensor_data)
max_uuid_greenhouse = max(row[6] for row in sensor_data)
offlineAccessPoints = random.sample(range(2, max_uuid_access_points), 2)
offlineGreenhouses = random.sample(range(1, max_uuid_greenhouse), 10)

offlineGreenhouses.append(real_greenhouse_id)


# Define an asynchronous function to send requests
async def send_data_async(data, greenhouse_uuid):
    async with aiohttp.ClientSession() as session:
        async with session.post("http://localhost:9000/api/measurements", auth=aiohttp.BasicAuth("admin", "passwd"),
                                json=data) as response:
            response_text = await response.text()
            if response.status != 200:
                # Add the greenhouse to the offline list if the request fails (e.g. greenhouse/access point is disabled)
                offlineGreenhouses.append(greenhouse_uuid)


def send_data(data, greenhouse_uuid):
    respone = requests.post("http://localhost:9000/api/measurements", auth=("admin", "passwd"), json=data)
    if respone.status_code != 200:
        # Add the greenhouse to the offline list if the request fails (e.g. greenhouse/access point is disabled)
        offlineGreenhouses.append(greenhouse_uuid)


# Infinite loop to send data continuously
while True:
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
            max_change = prev_value * max_change_percent / 100
            min_value = max(prev_value - max_change, limit_min_random)
            max_value = min(prev_value + max_change, limit_max_random)
            value = random.uniform(min_value, max_value)
        else:
            value = random.uniform(limit_min_random, limit_max_random)

        previous_sensor_values[sensor_id] = value

        if value > limit_max:
            limitExceededBy = value - limit_max
        elif value < limit_min:
            limitExceededBy = value - limit_min

        # Send the data to the API
        date = datetime.datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%S.%f")[:-3] + "Z"
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

        if enable_asynchronous:
            tasks.append(asyncio.ensure_future(send_data_async(measurement_data, row[6])))
        else:
            send_data(measurement_data, row[6])

    if enable_asynchronous:
        loop = asyncio.get_event_loop()
        loop.run_until_complete(asyncio.gather(*tasks))

    # Wait for the specified interval before sending the next set of data
    time.sleep(interval_seconds)
