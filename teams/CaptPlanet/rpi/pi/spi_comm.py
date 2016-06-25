import spidev
import time

spi = spidev.SpiDev()
spi.open(0,0)
while True:
    resp = spi.xfer2([0x00])
    print resp[0]
    time.sleep(1)
