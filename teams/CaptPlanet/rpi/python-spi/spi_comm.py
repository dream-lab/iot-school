import spidev
import time
import RPi.GPIO as GPIO


from picamera import PiCamera
from time import sleep
import urllib
import requests
import time
import paho.mqtt.client as paho

_url = 'https://api.projectoxford.ai/vision/v1/analyses'
_maxNumRetries = 10
_key="7ac55b11055d47f7978dd3f7f9975e6a"
url_base = 'api.projectoxford.ai'

http_proxy  = "http://proxy.iisc.ernet.in:3128"
https_proxy = "https://proxy.iisc.ernet.in:3128"#1080
ftp_proxy   = "ftp://proxy.iisc.ernet.in:3128"

proxyDict = {
              "http"  : http_proxy,
              "https" : https_proxy,
              "ftp"   : ftp_proxy
            }

def processRequest2( json, data, headers, params ):

    """
    Helper function to process the request to Project Oxford

    Parameters:
    json: Used when processing images from its URL. See API Documentation
    data: Used when processing image read from disk. See API Documentation
    headers: Used to pass the key information and the data type request
    """

    retries = 0
    result = None

    while True:

#        response = requests.post( _url, json = json, data = data, headers = headers, params = params )
        response = requests.request( 'post', _url, json = json, data = data, headers = headers, params = params,proxies=proxyDict )

        if response.status_code == 429:

            print( "Message: %s" % ( response.json()['error']['message'] ) )

            if retries <= _maxNumRetries:
                time.sleep(1)
                retries += 1
                continue
            else:
                print( 'Error: failed after retrying!' )
                break

        elif response.status_code == 200 or response.status_code == 201:

            if 'content-length' in response.headers and int(response.headers['content-length']) == 0:
                result = None
            elif 'content-type' in response.headers and isinstance(response.headers['content-type'], str):
                if 'application/json' in response.headers['content-type'].lower():
                    result = response.json() if response.content else None
                elif 'image' in response.headers['content-type'].lower():
                    result = response.content
        else:
            print( "Error code: %d" % ( response.status_code ) )
            print( "Message: %s" % ( response.json()['error']['message'] ) )

        break

    return result
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

	GPIO.wait_for_edge(24, GPIO.RISING,bouncetime=600)
#	a = [0,0]
#	sleep(1)
#	print "spi now open"
	
	spi.open(0,0)

	weight = 0
	sleep(0.1)
	temp = spi.readbytes(10)
	sleep(1)
	weight = spi.readbytes(10)
#	weight = int(weight[0])*256 + int(temp[0])
	spi.close()
	
	spi.open(0,0)

        weight = 0
        sleep(0.1)
        temp = spi.readbytes(10)
        sleep(1)
        weight = spi.readbytes(10)
#       weight = int(weight[0])*256 + int(temp[0])
        spi.close()

	print "weight",weight[0] + temp[0]*256
#	print "spi closed"
#	print "weight = ", weight,"temp=",temp,
#	print a, "a"
#	print int(a[1][0]) * 256  + int(a[0][0])
	

	camera.start_preview()
	camera.capture('/home/pi/Desktop/new-image' + str(image_count) + '.jpg')
	camera.stop_preview()
	
	print "picture taken"

	# Load raw image file into memory
	pathToFileInDisk = r'/home/pi/Desktop/new-image'+str(image_count) + '.jpg'
	with open( pathToFileInDisk, 'rb' ) as f:
    		data = f.read()
	params = { 'visualFeatures' : 'Color,Categories,Tags'}

	headers = dict()
	headers['Ocp-Apim-Subscription-Key'] = _key
	headers['Content-Type'] = 'application/octet-stream'

	json = None

#	result = processRequest2( json, data, headers, params )

	now = str(time.time())

#	req = '?ID='+str(DEV_ID) +'&time='+ now +'&weight='+str(weight[0] + temp[0]*256)+'&image='+str(image_count)+ '&result=' + str(result)
	req = '?ID='+str(DEV_ID) +'&time='+ now +'&weight='+str(weight[0] + temp[0]*256)+'&image='+str(image_count)
	print req
	r = requests.post('http://192.168.1.2:5000/uploader', files={'file': open('/home/pi/Desktop/new-image'+str(image_count)+'.jpg', 'rb')})
	image_count = image_count + 1
	r = requests.get('http://192.168.1.2:5000/uploader'+req)
