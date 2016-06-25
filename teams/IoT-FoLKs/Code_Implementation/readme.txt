Requirement: JDK 1.7+, (OS): Ubuntu/ Windows

To Run
------
Open Command prompt and go to the directory "project.jar"

Type in command prompt:
-----------------------
java -jar <file_Name>.jar


Included jar file names
------------------------
PublishData.jar
SubscribeTemperature.jar

Sample Output:
--------------
Threshold Value of temperature(in celcius) : 27 degree celcius

Publisher:
-----------
Publishing topic : iotfolks/temperature To : tcp://iotsummerschoolmqttbroker.cloudapp.net:1883 : Publish Temperature: 24 : Turn OFF Sprinkler
Publishing topic : iotfolks/temperature To : tcp://iotsummerschoolmqttbroker.cloudapp.net:1883 : Publish Temperature: 25 : Turn OFF Sprinkler
Publishing topic : iotfolks/temperature To : tcp://iotsummerschoolmqttbroker.cloudapp.net:1883 : Publish Temperature: 28 : Turn ON Sprinkler



Subsriber:
----------
tcp://iotsummerschoolmqttbroker.cloudapp.net:1883 Subscribe Temperature : 24 : Turn OFF Sprinkler
tcp://iotsummerschoolmqttbroker.cloudapp.net:1883 Subscribe Temperature : 25 : Turn OFF Sprinkler
tcp://iotsummerschoolmqttbroker.cloudapp.net:1883 Subscribe Temperature : 28 : Turn ON Sprinkler
