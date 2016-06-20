1. Siddhi Dependencies:

	Maven Repository for Siddhi built-in jars: 

		http://maven.wso2.org/nexus/content/repositories/releases/
	
	Maven Repository for Paho built-in jars:

		https://repo.eclipse.org/content/repositories/paho-releases/
	
	For maven dependencies:
		Look for pom.xml file in siddhi-mqtt folder
	
2. Build siddhi-mqtt project:
	
	Required:   
			jdk 1.7
			maven >=3.0.5
				
	To build: 	
			$ cd siddh-mqtt/
			$ ls (to find "pom.xml" file that contain list of maven dependencies)
			$ mvn clean compile package -Dmaven.test.skip=true
				
	To run:		
			$ cd target/
			$ ls (to find "siddhi-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar" file)
			$ java -cp siddhi-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar <Query Argument>
3. Publish and Subscribe using MQTT:
	
	Please do publish under your name:
		My Name: Rajrup Ghosh
		Publish under Topic: rajrupghosh/demo/temperature (replace 'rajrupghosh' with your name)
		Siddhi subscribes: rajrupghosh/demo/temperature
		Siddhi publishes under Topic (if query is aggregate): rajrupghosh/demo/siddhi/agg_temp
		Subscribe to siddhi output: rajrupghosh/demo/siddhi/agg_temp
		
	Example:
	Step 1: To publish publish temperature
		$ java -cp target/siddhi-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar org.controller.PublishTemperature <topic name>
		
		$ java -cp target/siddhi-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar org.controller.PublishTemperature rajrupghosh/demo/temperature
		
	Step 2: To run Siddhi
		$ java -cp target/siddhi-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar cds.iisc.SiddhiAnalytics <query type> <temperature topic name> <siddhi output topic name>
		
		$ java -cp target/siddhi-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar cds.iisc.SiddhiAnalytics filter rajrupghosh/demo/temperature rajrupghosh/demo/siddhi/filter_temperature
		
	Step 3: To subscribe to Siddhi Output
		$ java -cp target/siddhi-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar org.controller.SubscribeQueryTemp <siddhi output topic name>

		$ java -cp target/siddhi-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar org.controller.SubscribeQueryTemp rajrupghosh/demo/siddhi/filter_temperature	
		
		
		
