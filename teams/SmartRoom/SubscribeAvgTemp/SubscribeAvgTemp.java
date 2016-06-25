package org.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

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
*/
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubscribeAvgTemp implements MqttCallback {

	// Default value used if broker.properties file does not exist
	static String BrokerURL = "tcp://iotsummerschoolmqttbroker.cloudapp.net:1883";
	static String UserName = "iotsummer";
	static String Password = "iotsummer";

	static String Topic = "team4/iot-summer/temperature/avg";
	static String ClientId ;

	MqttClient client;

	public SubscribeAvgTemp() {
	}

	public static void main(String[] args) {
		ClientId = String.valueOf(new Random().nextInt());
		new SubscribeAvgTemp().checkCallBack(args);
	}

	public void checkCallBack(String[] args) {
		initCredentials();
		try {

			String topicName = Topic;
			if (args.length > 0)
				topicName = args[0];

			System.out.println("Subscription topic : " + topicName);

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			connOpts.setUserName(UserName);
			connOpts.setPassword(Password.toCharArray());

			client = new MqttClient(BrokerURL, ClientId);
			client.connect(connOpts);
			client.setCallback(this);
			client.subscribe(topicName);

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println(BrokerURL + " AverageTemperature : " + message);

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

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