import spidev
import time
import RPi.GPIO as GPIO


from picamera import PiCamera
from time import sleep
import urllib
import requests
import time
import paho.mqtt.client as paho

#url = "http://api.learn2crack.com/rpi/rpi_get.php"

DEV_ID = 1

GPIO.setmode(GPIO.BCM)
GPIO.setup(24, GPIO.IN, pull_up_down=GPIO.PUD_DOWN)


#a = [0,0,0,0,0,0,0,0,0,0]
count=0
image_count = 0
spi = spidev.SpiDev()
camera = PiCamera()
weight = 0
temp = 0

while True:

#        GPIO.wait_for_edge(24, GPIO.RISING,bouncetime=600)
#        sleep(1)

        spi.open(0,0)
#	spi.max_speed_hz = 100000
#	spi.mode = 0b01
        weight = 0
	sleep(0.1)
        temp = spi.readbytes(10)
        sleep(1)
        weight = spi.readbytes(10)
#        weight = int(weight[0])*256 + int(temp[0])
        spi.close()
#       print "spi closed"
        print "weight = ", weight,"temp=",temp
