package org.controller;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
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

public class SubscribeMoisture implements MqttCallback {

	// Default value used if broker.properties file does not exist
	static String BrokerURL = "tcp://iotsummerschoolmqttbroker.cloudapp.net:1883";
	static String UserName = "iotsummer";
	static String Password = "iotsummer";
	static Random rn = new Random();
	static String Topic = "demo/moisture/#";
	static String ClientId = "demosubcriber"+rn.nextInt();
	String oldTimestamp = "00:00:00";
	MqttClient client;
	double savg1 = 0;
	int sz1 = 0;
	double savg2 = 0;
	int sz2 = 0;
	
	public SubscribeMoisture() {
	}

	public static void main(String[] args) {
		new SubscribeMoisture().checkCallBack(args);
	}

	public void checkCallBack(String[] args) {
		initCredentials();
		try {
			
			String topicName = Topic;

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
	
	public int getminute(String timestamp) {
		String[] dist = timestamp.split(":");
		return Integer.parseInt(dist[1]);
	}
	
	

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		System.out.println(message);
		String content = message.toString();
		String[] sendata = content.split(" ");
		String filename =  "sensordata.txt";

		boolean appendata = true;
		
		int old_minute = getminute(oldTimestamp);
		int current_minute = getminute(sendata[4]);
		int diff_min = Math.abs(current_minute - old_minute);
		
		/*
		 if currenttimeStamp - oldtimestamp = 5 min
		 then reset the data stream
		 */
		
		if(diff_min > 3){
			appendata = false;
			oldTimestamp = sendata[4];
			sz1 = 0;
			sz2 = 0;
			PrintWriter out = new PrintWriter("sensoravg.txt","UTF-8");
			out.println(new Double(savg1).toString() + ","+ new Double(savg2).toString());
			out.close();
		}
		else{ 
			appendata = true;
		}
		
		if(sendata[0].equals("1")){
			savg1 = savg1*sz1 + Integer.parseInt(sendata[1]); 
			sz1 = sz1 + 1;
			savg1 = savg1/(double)sz1;
		}else{
			savg2 = savg2*sz2 +  Integer.parseInt(sendata[1]);
			sz2 = sz2 + 1;
			savg2 = savg2/(double)sz2;
		}
		
		String[] timedata = sendata[4].split(":",3);
		
		String payload = sendata[0]+","+sendata[1]+","+sendata[2]+","+sendata[3]+","+timedata[0]+","+timedata[1]+","+timedata[2];
		
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, appendata)));	
		writer.println(payload);
		writer.close();
		
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