import RPi.GPIO as GPIO
from time import sleep
GPIO.setmode(GPIO.BCM)
GPIO.setup(2,GPIO.IN)
GPIO.setup(3,GPIO.IN)
GPIO.setup(4,GPIO.IN)
GPIO.setup(17,GPIO.IN)
GPIO.setup(27,GPIO.IN)
GPIO.setup(22,GPIO.IN)
GPIO.setup(10,GPIO.IN)
GPIO.setup(9,GPIO.IN)
adc_value=0
#file = open('soilsensor_reading.txt','w')


while(1):
	if GPIO.pin(26)==1:
	adc_value = GPIO.input(9)*128+GPIO.input(10)*64+GPIO.input(22)*32+GPIO.input(27)*16+GPIO.input(17)*8+GPIO.input(4)*4+GPIO.input(3)*2+GPIO.input(2)*1
	#file.write(adc_value)
	#file_content = file.read()
	#print(file_content)
	print("sensor1")
	print(adc_value)
	else:
	adc_value = GPIO.input(9)*128+GPIO.input(10)*64+GPIO.input(22)*32+GPIO.input(27)*16+GPIO.input(17)*8+GPIO.input(4)*4+GPIO.input(3)*2+GPIO.input(2)*1
	#file.write(adc_value)
	#file_content = file.read()
	#print(file_content)
	print("sensor2")
	print(adc_value)
	
	



