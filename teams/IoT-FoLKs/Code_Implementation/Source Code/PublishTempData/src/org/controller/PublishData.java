package org.controller;

/**
+---------------+------+---------+
|         SenML | JSON | Type    |
+---------------+------+---------+
|     Base Name | bn   | String  |
|     Base Time | bt   | Number  |
|     Base Unit | bu   | String  |
|    Base Value | bv   | Number  |
|       Version | bver | Number  |
|          Name | n    | String  |
|          Unit | u    | String  |
|         Value | v    | Number  |
|  String Value | vs   | String  |
| Boolean Value | vb   | Boolean |
|    Data Value | vd   | String  |
|     Value Sum | s    | Number  |
|          Time | t    | Number  |
|   Update Time | ut   | Number  |
+---------------+------+---------+

Optional:
agrs[0]=absolute path of csv file
*/
//import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.senml.data.MqttEvent;
import org.senml.data.SenML;

public class PublishData {
		
	static String BrokerURL = "tcp://iotsummerschoolmqttbroker.cloudapp.net:1883";
	static String UserName = "iotsummer";
	static String Password = "iotsummer";

	static String Topic = "iotfolks/temperature";
	static String ClientId;
	static  String csvFile = System.getProperty("user.dir") + "/temp.csv";
	static  BufferedReader br=null;
	static String line = "";
	
	public static void main(String[] args) {

		//int i=1;
		
		MemoryPersistence persistence = new MemoryPersistence();
		ClientId = String.valueOf(new Random().nextInt(500000));
		try {

			initCredentials();
			

			String topicName = Topic;
			String content;

			int qos = 2;

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setUserName(UserName);
			connOpts.setPassword(Password.toCharArray());
			connOpts.setCleanSession(true);

			MqttClient mqttClient = new MqttClient(BrokerURL, ClientId, persistence);

			System.out.println("Connecting to broker: " + BrokerURL);
			mqttClient.connect(connOpts);
			System.out.println("Connected");
			
			while (true) {
							
					try{
							Thread.sleep(3000);
							if (args.length > 0)
							csvFile = args[0];
							br = new BufferedReader(new FileReader(csvFile));

							while ((line = br.readLine()) != null) {
				
							content = getJSONContentFromFile(line);
							publish_Message(content, qos, topicName, mqttClient);		
						}
				
						br.close();
					}
					catch (FileNotFoundException e) {
							System.out.println("Specified file was not found");
							content = "Sample Content & waiting for File";
							publish_Message(content, qos, topicName, mqttClient);
							
							//fileMove("C:/Users/vijay/Desktop/IOT Summer School/File-Backup/temperature1"+".csv", csvFile);
							 try {
						            //thread to sleep for the specified number of milliseconds
						            Thread.sleep(30000);
						        } catch ( java.lang.InterruptedException ie) {
						            System.out.println(ie);
						        }
							continue;
					} 
					catch (Exception e) {
							e.printStackTrace();
							content = "Sample Content";
							publish_Message(content, qos, topicName, mqttClient);
							
					}
					finally {
						if (br != null) {
							try {
									br.close();
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
			
					/*try{
			    		
			    		File file = new File(csvFile);
			        	
			    		if(file.delete()){
			    			System.out.println(file.getName() + " is deleted!");
			    		}else{
			    			System.out.println("Delete operation is failed.");
			    		}
			    	   
			    	}catch(Exception e){
			    		
			    		e.printStackTrace();
			    		
			    	}*/
						
			      //  fileMove(csvFile, "C:/Users/vijay/Desktop/IOT Summer School/File-Backup/temperature"+j+".csv");
					
			        try {
			            //thread to sleep for the specified number of milliseconds
			            Thread.sleep(30000);
			        } catch ( java.lang.InterruptedException ie) {
			            System.out.println(ie);
			        }
				//j++;
				//i++;
			}

			//mqttClient.disconnect();
			//System.out.println("Disconnected");
			//System.exit(0);

		} catch (MqttException me) {
			System.out.println("Reason " + me.getReasonCode());
			System.out.println("Message " + me.getMessage());
			System.out.println("Local message " + me.getLocalizedMessage());
			System.out.println("Cause " + me.getCause());
			System.out.println("Exception " + me);
			me.printStackTrace();
		}
	}
	
	public static String getJSONContentFromFile(String line) {

			String content = "";
			MqttEvent mqttsendEvent = new MqttEvent();
				
				String[] csvData = line.split(",");
			//	System.out.print("Current Temperature is :  "+csvData[0]+"\n");//+"   "+csvData[1]+"  "+csvData[2]+"\n");
				SenML reading = new SenML();
				//reading.setUnit(csvData[0]);
				reading.setValue(Double.parseDouble(csvData[0]));
				//reading.setTime(Double.parseDouble(csvData[2]));
				mqttsendEvent.add(reading);
				if(Double.parseDouble(csvData[0])>=27)
				{
					content = csvData[0]+" : Turn ON Sprinkler";
					return content;
				}
				else {
						content= csvData[0]+" : Turn OFF Sprinkler";
						return content;
					}
		}

	public static void initCredentials() {

		String brokerConnectionFile = System.getProperty("user.dir") + "/broker.properties";

		Properties prop;
		InputStream input = null;
		try {

			prop = new Properties();
			input = new FileInputStream(brokerConnectionFile);

			prop.load(input);

			if (prop.getProperty("brokerurl") != null)
				BrokerURL = prop.getProperty("brokerurl");
			if (prop.getProperty("username") != null)
				UserName = prop.getProperty("username");
			if (prop.getProperty("password") != null)
				Password = prop.getProperty("password");

		} catch (FileNotFoundException e) {

		} catch (Exception e) {

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return;

	}

	public static String publish_Message(String content, int qos, String topicName, MqttClient mqttClient){
		
		try {
			System.out.print("Publishing topic : " + topicName);
			MqttMessage message = new MqttMessage(content.getBytes());
			System.out.println(" To : " + BrokerURL + " : Publish Temperature: " + content);
			message.setQos(qos);
			mqttClient.publish(topicName, message);
			//System.out.println("   Sent Message is : "+message);
			Thread.sleep(3000);
			return "Success";
		
		}
		catch(Exception e)
		{
			return "failure";
		}
		
	}
	
	public static void fileMove(String f1, String f2){
		
		File oldfile =new File(f1);
        File newfile =new File(f2);

        if(oldfile.renameTo(newfile)){
            System.out.println("File renamed");
        }else{
            System.out.println("Sorry! the file can't be renamed");
        }
		
	}
}
