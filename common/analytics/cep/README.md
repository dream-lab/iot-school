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
				
3. Python MQTT scripts:
	
	Required: 	
			
			python 2.7
			
			pip
			
			paho-mqtt (pip install paho-mqtt)

4. Some more guide lines for Today's DEMO have been mentioned in siddhi-mqtt/README.txt
 
