
->Common docs, scripts and code samples for mqtt.

Instructions to run on linux machine : After installing all pre reqs(Java 7)
------------------------------------------------------------

PreReq for Running
		Make sure'temperature.csv' exists in the current directry when publishing temperature values
		Optionally : 'broker.properties' file in the current directory can be edited if you want to connect to a different broker

1)Navigate to MQTT/demo_jars
 
2)Commands for running jars

Publishing Temperature Values

		->java -cp PublishTemperature-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.controller.PublishTemperature
		
(Optional : Relative path of the 'temperature.csv' file)

Calculating Average Temperature Values

		->java -cp CalculateAvgTemp-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.controller.SubscribeTemperature

Subscribing to Average Temperature Value Results

		->java -cp SubscribeAvgTemp-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.controller.SubscribeAvgTemp


Note : can also be executed from corresponding target directories ; refer below on how to build from source

Other References
------------------------------------------------------------
-http://www.eclipse.org/paho/


Building from repo 
------------------------------------------------------------
Dependencies:

Maven Repository for Paho built-in jars:

		https://repo.eclipse.org/content/repositories/paho-releases/
	
For maven dependencies:
Look for pom.xml file in respective folders

Build Process:
	
Required:   
-jdk 1.7
-maven >=3.0.5
	
To build: 	

(cd Individual project folders that you want to build ;PublishTemperature; CalculateAvgTemp; SubscribeAvgTemp)

		$ ls (to find "pom.xml" file that contain list of maven dependencies)
		$ mvn clean compile package -Dmaven.test.skip=true

To run projects after reaching corresponding project folders

To run PublishTemperature:
(Optional : Relative path of the 'temperature.csv' file)

		$java -cp target/PublishTemperature-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.controller.PublishTemperature

To run CalculateAvgTemp:	

		$java -cp target/CalculateAvgTemp-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.controller.SubscribeTemperature

To run SubscribeAvgTemp:

		$java -cp target/SubscribeAvgTemp-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.controller.SubscribeAvgTemp
