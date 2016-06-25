#!/usr/bin/python
import sys
import socket
import Adafruit_DHT
import time
from datetime import datetime
from thingspeak_handler import thingspeak_interface
from azure_handler import azure_interface 

# Rpi specific constants.
pin = 18
sensor = Adafruit_DHT.DHT11

# Azure specific constants.
name_space = 'iiiot-ns'
key_name = 'RootManageSharedAccessKey'
key_value = 'Og6rOndhkdZ2ay9hhtNEnOa5Wm2Yo1IEEpybptsXNG8='
hubname = "iiit_hyd"

# Thingspeak specific constants.
url='https://api.thingspeak.com'
read_api_key='RGSCM5J3IYHG9UDE'
write_api_key='YGFJLPP24CG7QLAW'

if __name__ == '__main__':

  ts = thingspeak_interface(url, read_api_key, write_api_key, proxy=True)
  az = azure_interface(name_space, key_name, key_value, proxy=True)
  last_values = []
            
  try:
    while True:
      humidity, temperature = Adafruit_DHT.read_retry(sensor, pin)

      # If this happens try again!
      if humidity is not None and temperature is not None:
        if last_values != [temperature, humidity]:
          ts.write_to_thingspeak([temperature, humidity])
          az.event_send(hubname, [temperature, humidity])
          print('Temp={0:0.1f}  Humidity={1:0.1f}%'.format(temperature, humidity))
        last_values = [temperature, humidity]
      else:
        print('Failed to get reading. Try again!')
        sys.exit(1)

      time.sleep(1)
  except:
    print sys.exc_info()
