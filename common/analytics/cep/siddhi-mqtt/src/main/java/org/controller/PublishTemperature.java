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
import java.io.FileInputStream;
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

public class PublishTemperature {

	// Default value used if broker.properties file does not exist
	static String BrokerURL = "tcp://iotsummerschoolmqttbroker.cloudapp.net:1883";
	static String UserName = "iotsummer";
	static String Password = "iotsummer";
	static Random rn = new Random();
	static String Topic = "demo/temperature";
	static String ClientId = "demoPublisher"+rn.nextInt();

	public static void main(String[] args) throws Exception {

		MemoryPersistence persistence = new MemoryPersistence();

		try {

			initCredentials();
			String topicName = Topic;
			
			if(args.length>=1)
				topicName = args[0];
			if(args.length>=2)
				ClientId=args[1];
			
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
				content = getJSONContentFromFile(args);
				System.out.print("Publishing topic : " + topicName);
				MqttMessage message = new MqttMessage(content.getBytes());
				System.out.println(" To : " + BrokerURL + " : Publishing message: " + content);
				message.setQos(qos);
				mqttClient.publish(topicName, message);
				Thread.sleep(500);
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

	public static String getJSONContentFromFile(String[] args) {

		String csvFile = System.getProperty("user.dir") + "/temperature.csv";

//		if (args.length > 0)
//			csvFile = args[0];

		BufferedReader br = null;
		String line = "";
		String content = "";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			MqttEvent mqttsendEvent = new MqttEvent();

			while ((line = br.readLine()) != null) {

				String[] csvData = line.split(",");
				SenML reading = new SenML();
				reading.setUnit(csvData[0]);
				reading.setValue(Double.parseDouble(csvData[1]));
				mqttsendEvent.add(reading);
			}

			content = mqttsendEvent.getJSONReprsentation();

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