# example taken from github.com/hbldh/bleak

import asyncio
from bleak import BleakScanner
from bleak.backends.scanner import AdvertisementData


async def main():
    devices = await BleakScanner.discover(timeout=30.0, return_adv=True)
    print(devices)
    for k, v in devices.items():
        print("Device id: {0}\nLocal name: {1}\n\n".format(k, v[1].local_name))


asyncio.run(main())
