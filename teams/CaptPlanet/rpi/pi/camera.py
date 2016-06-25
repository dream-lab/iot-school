from picamera import PiCamera
from time import sleep
import urllib
import requests

#url = "http://api.learn2crack.com/rpi/rpi_get.php"

camera = PiCamera()

#for i in range(5):
camera.start_preview()
sleep(2)
camera.capture('/home/pi/Desktop/'+str(i)+'image.jpg')
camera.stop_preview()

#import requests
#r = requests.post('http://10.16.240.:5000/uploader', files={'file': open('/home/pi/Desktop/image.jpg', 'rb')})
