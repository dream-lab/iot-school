from picamera import PiCamera
from time import sleep
import urllib
import requests
import time
import paho.mqtt.client as paho

#url = "http://api.learn2crack.com/rpi/rpi_get.php"

camera = PiCamera()

#i = 0 
#for i in range(5):
camera.start_preview()
sleep(2)
camera.capture('/home/pi/Desktop/image.jpg')
camera.stop_preview()

#import requests
#r = requests.post('http://10.16.240.:5000/uploader', files={'file': open('/home/pi/Desktop/image.jpg', 'rb')})

#logFileName = "publishLog.txt"

def on_connect(client, userdata, flags, rc):
    print("CONNACK received with code "+ str(rc))
    # client.subscribe("iisc/smartx/crowd/network/mqttTest1",2)

def on_disconnect(client, userdata, rc):
    print ("client disconnected")

def on_publish (clientid,userdata,mid):
    # write_to_file("published")
    print ("published")

def on_subscribe(client, userdata, mid, granted_qos):
    print("Subscribed: "+str(mid)+" "+str(granted_qos))

def on_message(client, userdata, msg):
    print("received: "+" "+str(msg.qos)+" "+str(msg.payload))

client = paho.Client(client_id="my_pi", clean_session=False, userdata=None, protocol=paho.MQTTv311)

client.on_connect = on_connect
client.on_disconnect = on_disconnect
client.on_publish  = on_publish
client.on_subscribe = on_subscribe
client.on_message = on_message
# client.username_pw_set("AppUser", "scdl@App")

client.username_pw_set("admin", "password")

# client.connect("smartx.cds.iisc.ac.in", 1883)
#client.username_pw_set("iotsummer","iotsummer")
client.connect("52.200.58.205", 1883)
client.loop_start()
#i=0

#while i<5:

#	print("publishing: " + str(i))
#	i+=1
#	time.sleep(1)
#	(rc, mid) = client.publish("iisc/summerschool/iot/garfield", str(i),2, retain=True)
#		
	

#f = open("11.jpg")
#imagestring = f.read()
#byteArray = bytearray(imagestring)
#print byteArray
#client.publish("photo", byteArray ,0)	
#time.sleep(5)
#(rc, mid) = client.publish("iisc/summerschool/iot/garfield", byteArray,0)

#i=0#
#while i<1:

#	print("published Garfield ")
#	i+=1
#	time.sleep(1)
#	(rc, mid) = client.publish("iisc/summerschool/iot/garfield", "GARFIELD", retain=True)
