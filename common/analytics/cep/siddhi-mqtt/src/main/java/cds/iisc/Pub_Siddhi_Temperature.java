package cds.iisc;

import java.util.Random;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Pub_Siddhi_Temperature  implements MqttCallback
{
	static final String BROKER_URL = "tcp://iotsummerschoolmqttbroker.cloudapp.net:1883";
	String TOPIC = "demo/siddhi/temperature";
	static final String MQTT_USERNAME = "iotsummer";
	static final String MQTT_PASSWORD = "iotsummer";
	
	private String PubID;
	private MqttClient PubClient;
	private MqttConnectOptions PubConnOpt;
	private MemoryPersistence persistence;
	private MqttTopic topic;
	
	public Pub_Siddhi_Temperature(String topic)
	{
		this.TOPIC = topic;
		initPub();
	}
	
	public void initPub()
	{
		persistence = new MemoryPersistence();
		Random rn = new Random();
		PubID = "Siddhi_Pub"+rn.nextInt();	
		
		PubConnOpt = new MqttConnectOptions();
		PubConnOpt.setCleanSession(true);
		PubConnOpt.setUserName(MQTT_USERNAME);
		PubConnOpt.setPassword(MQTT_PASSWORD.toCharArray());
		
		try 
		{
			PubClient = new MqttClient(BROKER_URL, PubID, persistence);
			PubClient.connect(PubConnOpt);
			topic = PubClient.getTopic(TOPIC);
		} 
		catch (MqttException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deliver(String str)
	{	
	   		int pubQoS = 2;
			MqttMessage message = new MqttMessage(str.getBytes());
	    	message.setQos(pubQoS);
	    	message.setRetained(true);

	    	// Publish the message
	    	MqttDeliveryToken token = null;
	    	try {
	    		// publish message to broker
				token = topic.publish(message);
		    	// Wait until the message has been delivered to the broker
				token.waitForCompletion();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
}
