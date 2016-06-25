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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.senml.data.MqttEvent;
import org.senml.data.SenML;

public class PublishMoisture {


	// Default value used if broker.properties file does not exist
	static String BrokerURL = "tcp://iotsummerschoolmqttbroker.cloudapp.net:1883";
	static String UserName = "iotsummer";
	static String Password = "iotsummer";
	static Random rn = new Random();
	static String Topic = "demo/moisture";
	static String ClientId = "demoPublisher"+rn.nextInt();
	static long current1 = 0;
	static long current2 = 0;
	
	public static void main(String[] args) throws Exception {

		MemoryPersistence persistence = new MemoryPersistence();

		try {

			initCredentials();
			String topicName = Topic;
			
			
			
			String Sen =  topicName + "/sensor";
			String content;

			int qos = 2;

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setUserName(UserName);
			connOpts.setPassword(Password.toCharArray());
			connOpts.setCleanSession(true);

			MqttClient mqttClient = new MqttClient(BrokerURL, ClientId, persistence);

			System.out.println("Connecting to broker: " + BrokerURL);
			mqttClient.connect(connOpts);
			System.out.println(ClientId+" Connected");

			while (true) {
				content = getJSONContentFromFile(Sen,1);
				if(!content.equals(""))
				{
					MqttMessage message = new MqttMessage(content.getBytes());
					message.setQos(qos);					
					mqttClient.publish(Sen + "1", message);
					System.out.println("Publishing sensor data 1: " + content);
				}
				content = getJSONContentFromFile(Sen,2);
				if(!content.equals(""))
				{
					MqttMessage message = new MqttMessage(content.getBytes());
					message.setQos(qos);					
					mqttClient.publish(Sen + "2", message);
					System.out.println("Publishing sensor data 2: " + content);
				}
				Thread.sleep(5000);
			}

			// mqttClient.disconnect();
			// System.out.println("Disconnected");
			// System.exit(0);

		} catch (MqttException me) {
			System.out.println("Reason " + me.getReasonCode());
			System.out.println("Message " + me.getMessage());
			System.out.println("Local message " + me.getLocalizedMessage());
			System.out.println("Cause " + me.getCause());
			System.out.println("Exception " + me);
			me.printStackTrace();
		}
	}

	public static String getJSONContentFromFile(String topic, int sen_num) {

		String csvFile = System.getProperty("user.dir") + "/sendata" + new Integer(sen_num).toString();
		double x,y;
		
		if(sen_num == 1){
			x = 0.5;
			y = 0.3;
		}
		else{
			x = 0.1;
			y = 0.2;
		}
		
		BufferedReader br = null;
		String line = "";
		String content = "";

		try {
			
			String brokerConnectionFile = System.getProperty("user.dir") + "/sendata";
			
			
			File file = new File(csvFile);
			
			long changed = (long)file.lastModified(); 
			long current;
			
			//	System.out.println(new Long(changed).toString());
			
			if(sen_num == 1)
			{
				current = current1;
			}
			else
			{
				current = current2;
			}
			
			if(changed != current)
			{
				br = new BufferedReader(new FileReader(csvFile));
				line = br.readLine();
				//line = new Integer(((int)(Math.random()*255))).toString();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String timestamp = sdf.format(new Date());
				content = new Integer(sen_num).toString() + " " + line + " " + new Double(x).toString() + " " + new Double(y).toString() + " " + timestamp;
				current = changed;
			}
			
			if(sen_num == 1)
			{
				current1 = current;
			}
			else
			{
				current2 = current;
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Specified file was not found");
			content = "Sample Content";
		} catch (Exception e) {
			e.printStackTrace();
			content = "Sample Content";
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return content;

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
}