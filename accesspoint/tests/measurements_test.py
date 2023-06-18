import json
import time
import unittest
# TODO: DO NOT DELETE THIS IMPORT
import asyncio
import pandas
from accesspoint import db
from accesspoint.Settings import Settings
from unittest import IsolatedAsyncioTestCase
from http import HTTPStatus
from accesspoint.db import write_to_document_sensor
from accesspoint.webserver import get_avg_measurements, send_post_request


class Test(IsolatedAsyncioTestCase):

    async def setUp(self) -> None:
        self.measurements = []

    async def test_send_post_request(self):
        settings = Settings()
        database = await db.connect_to_db()
        if database is None:
            return
        # if collection is empty, write to it
        if database[settings.mongo_collection].count_documents({}) == 0:
            for i in range(10):
                await write_to_document_sensor(i + 10, "LIGHT", 20)
                print("LIGHT")
                # await write_to_document_sensor(i, "TEMPERATURE", 27)
                time.sleep(0.5)

        avg_measurements = await get_avg_measurements(database)

        for avg_measurement in avg_measurements:
            print(avg_measurement)
            status_code = await send_post_request(avg_measurement)
            self.assertEqual(HTTPStatus.OK, status_code)

    async def test_get_avg_measurements(self):
        settings = Settings()
        database = await db.connect_to_db()
        if database is None:
            return

        database[settings.mongo_collection].delete_many({})
        database = await db.connect_to_db()

        df = pandas.DataFrame(columns=["value"])
        for i in range(10):
            await write_to_document_sensor(i, "LIGHT", 20)
            df.loc[i] = i
            # await write_to_document_sensor(i, "TEMPERATURE", 27)
            time.sleep(0.5)
        avg = float(df.mean())
        avg_measurements = await get_avg_measurements(database)
        value = json.loads(avg_measurements[0])["value"]
        self.assertIsNotNone(avg_measurements)
        self.assertEqual(value, avg)


if __name__ == '__main__':
    unittest.main()
