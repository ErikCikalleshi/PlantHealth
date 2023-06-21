import unittest
import asyncio
import sys

from accesspoint.Settings import Settings
from accesspoint.config import get_settings_backend

from unittest import IsolatedAsyncioTestCase
from http import HTTPStatus


class Test(IsolatedAsyncioTestCase):

    async def test_get_setting(self):
        settings = Settings()
        sett: dict = {
            "auth": {
                "user": "admin",
                "password": "passwd"
            },
            "server": {
                "host": "localhost",
                "port": 9000
            },
            "access_point_id": "1",
            "mongo": {
                "port": 27017,
                "host": "localhost",
                "database": "PlantHealth",
                "collection": "Measurements"
            }
        }

        settings.set_settings(sett)
        is_successful = await get_settings_backend()
        self.assertEquals(is_successful, HTTPStatus.OK)

    async def test_get_setting_fail_wrong_accesspoint_id(self):
        settings = Settings()
        sett: dict = {
            "auth": {
                "user": "admin",
                "password": "passwd"
            },
            "server": {
                "host": "localhost",
                "port": 9000
            },
            "access_point_id": "2",
            "mongo": {
                "port": 27017,
                "host": "localhost",
                "database": "PlantHealth",
                "collection": "Measurements"
            }
        }

        settings.set_settings(sett)
        is_successful = await get_settings_backend()
        self.assertEquals(is_successful, HTTPStatus.FORBIDDEN)

    async def test_get_setting_fail_wrong_accesspoint_not_found(self):
        settings = Settings()
        sett: dict = {
            "auth": {
                "user": "admin",
                "password": "passwd"
            },
            "server": {
                "host": "localhost",
                "port": 9000
            },
            "access_point_id": "151688",
            "mongo": {
                "port": 27017,
                "host": "localhost",
                "database": "PlantHealth",
                "collection": "Measurements"
            }
        }

        settings.set_settings(sett)
        is_successful = await get_settings_backend()
        self.assertEquals(is_successful, HTTPStatus.NOT_FOUND)


if __name__ == '__main__':
    unittest.main()
