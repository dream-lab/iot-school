    # __author__ = 'shashankshekhar'

# -*- coding: utf-8 -*-
# import paho.mqtt.client as mqtt
#
# mqttc = mqtt.Client()
# mqttc.username_pw_set("admin", "password")
# mqttc.connect("localhost", 61613)
# mqttc.publish(u"hello/world", u"Hello World!")
# mqttc.loop_forever(timeout=1.0, max_packets=1, retry_first_connection=False)
# mqttc.loop(200) # timeout = 200s
import paho.mqtt.client as paho
import time

logFileName = "publishLog.txt"

def on_connect(client, userdata, flags, rc):
    print("CONNACK received with code "+ str(rc))
    # client.subscribe("iisc/smartx/crowd/network/mqttTest1",2)

def on_disconnect(client, userdata, rc):
    print ("client disconnected")

def on_publish (clientid,userdata,mid):
    # write_to_file("published")
    print ("published")

# def write_to_file(string):
#     file = open(logFileName,"a")
#     currTime  = time.strftime("%I:%M:%S")
#     currdate = time.strftime("%d/%m/%Y")
#     file.write(str(currdate)+"-"+str(currTime)+ "-"+string)
#     file.write("\n")
#     file.close()

def on_subscribe(client, userdata, mid, granted_qos):
    print("Subscribed: "+str(mid)+" "+str(granted_qos))

def on_message(client, userdata, msg):
    print("received: "+" "+str(msg.qos)+" "+str(msg.payload))

client = paho.Client(client_id="abcd", clean_session=False, userdata=None, protocol=paho.MQTTv311)

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
i=0
while i<5:

	print("publishing: " + str(i))
	i+=1
	time.sleep(1)
	(rc, mid) = client.publish("iisc/summerschool/iot/garfield", str(i),2, retain=True)
		
	

f = open("11.jpg")
imagestring = f.read()
byteArray = bytearray(imagestring)
print byteArray
#client.publish("photo", byteArray ,0)	
time.sleep(5)
(rc, mid) = client.publish("iisc/summerschool/iot/garfield", byteArray,0)

i=0
while i<1:

	print("published Garfield ")
	i+=1
	time.sleep(1)
	(rc, mid) = client.publish("iisc/summerschool/iot/garfield", "GARFIELD", retain=True)
		


