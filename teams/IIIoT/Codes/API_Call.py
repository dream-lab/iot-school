import os
import glob
import time
import thread
import socket
from datetime import datetime
from azure.servicebus import ServiceBusService

os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')

base_dir = '/sys/bus/w1/devices/'
device1_folder = glob.glob(base_dir + '28*')[0]
device2_folder = glob.glob(base_dir + '28*')[1]
device1_file = device1_folder + '/w1_slave'
device2_file = device2_folder + '/w1_slave'
name_space = 'sensordemo-ns'

key_name = 'RootManageSharedAccessKey'
key_value = 'nmoamu9fHRphGQodT/J7SBXfmLGYfVsrUDIZXxm+hMc='

def read_temp_raw(dfile):
    f = open(dfile, 'r')
    lines = f.readlines()
    f.close()
    return lines

def read_temp(dfile):
    lines = read_temp_raw(dfile)
    while lines[0].strip()[-3:] != 'YES':
        time.sleep(0.2)
        lines = read_temp_raw(dfile)
    equals_pos = lines[1].find('t=')
    if equals_pos != -1:
        temp_string = lines[1][equals_pos+2:]
        temp_c = float(temp_string) / 1000.0
        return temp_c
host = socket.gethostname()

while True:
    try:
        temp1 = read_temp(device1_file)
        temp2 = read_temp(device2_file)
        body = '{\"DeviceId\": \"' + host + '\" '
        now = datetime.now()
        body += ', \"rowid\":' + now.strftime('%Y%m%d%H%M%S')
        body += ', \"Time\":\"' + now.strftime('%Y/%m/%d %H:%M:%S') + '\"'
        body += ', \"Temp1\":' + str(temp1)
        body += ', \"Temp2\":' + str(temp2) + '}'
        print body
        sbs = ServiceBusService(service_namespace=name_space,shared_access_key_name=key_name, shared_access_key_value=key_value)
        hubStatus = sbs.send_event('sensordemohub',body)

        print "Send Status:", repr(hubStatus)
        time.sleep(5)
    except Exception as e:
        print "Exception - ",
repr(e)

