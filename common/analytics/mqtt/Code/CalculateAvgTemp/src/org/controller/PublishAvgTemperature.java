package org.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PublishAvgTemperature {

	// Default value used if broker.properties file does not exist
	static String BrokerURL = "tcp://iotsummerschoolmqttbroker.cloudapp.net:1883";
	static String UserName = "iotsummer";
	static String Password = "iotsummer";

	static String Topic = "demo/temperature/average";
	static String ClientId ;

	public static void publishResults(Double result) throws Exception {

		MemoryPersistence persistence = new MemoryPersistence();
		initCredentials();
		ClientId = String.valueOf(new Random().nextInt());
		try {

			String topicName = Topic;
			int qos = 2;

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setUserName(UserName);
			connOpts.setPassword(Password.toCharArray());
			connOpts.setCleanSession(true);

			MqttClient mqttClient = new MqttClient(BrokerURL, ClientId, persistence);

			System.out.println("Connecting to broker: " + BrokerURL);
			mqttClient.connect(connOpts);
			System.out.println("Connected");

			System.out.println("Publishing topic : " + topicName);
			MqttMessage message = new MqttMessage(result.toString().getBytes());
			System.out.println("To : " + BrokerURL + " : publishing message: " + result);
			message.setQos(qos);
			mqttClient.publish(topicName, message);

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