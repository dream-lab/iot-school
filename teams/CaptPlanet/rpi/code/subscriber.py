import paho.mqtt.client as paho
import time

def on_connect(client, userdata, flags, rc):
        print("Connected with result code "+str(rc))
        client.subscribe("iisc/summerschool/iot/garfield",qos=2)

def on_message(client, userdata, msg):
        print(msg.topic+" "+str(msg.payload))


client = paho.Client(client_id="abcd1", clean_session=False, userdata=None, protocol=paho.MQTTv311)
client.on_connect = on_connect
client.on_message = on_message
client.username_pw_set("iotsummer", "iotsummer")
client.connect("52.200.58.205", 1883)


client.loop_forever()