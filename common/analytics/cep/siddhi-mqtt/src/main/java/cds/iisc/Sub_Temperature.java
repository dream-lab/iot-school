package cds.iisc;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.senml.data.MqttEvent;
import org.senml.data.SenML;
import org.wso2.siddhi.core.stream.input.InputHandler;

public class Sub_Temperature implements MqttCallback {
	MqttClient client;
	InputHandler inputHandler;

	private String SubID;

	static final String BROKER_URL = "tcp://iotsummerschoolmqttbroker.cloudapp.net:1883";
	String TOPIC = "demo/temperature";
	static final String MQTT_USERNAME = "iotsummer";
	static final String MQTT_PASSWORD = "iotsummer";

	MqttClient PubClient, SubClient;
	MqttConnectOptions PubConnOpt, SubConnOpt;

	public Sub_Temperature(InputHandler inputHandler, String topic) {
		this.inputHandler = inputHandler;
		this.TOPIC = topic;
		initSub();
	}

	private void initSub() {
		SubID = "Siddhi_Sub";

		SubConnOpt = new MqttConnectOptions();
		SubConnOpt.setCleanSession(true);
		SubConnOpt.setUserName(MQTT_USERNAME);
		SubConnOpt.setPassword(MQTT_PASSWORD.toCharArray());

		try {
			SubClient = new MqttClient(BROKER_URL, SubID);
			SubClient.connect(SubConnOpt);
			SubClient.setCallback(this);
			SubClient.subscribe(TOPIC);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {

		System.out.println(topic + " : " + message);
		MqttEvent recEvent = MqttEvent.getEventFromJson(message.toString());
		for (SenML senML : recEvent.e) {
//			System.out.println(senML.getValue().floatValue());
			inputHandler.send(new Object[] { senML.getValue().floatValue() });
			Thread.sleep(1000);
		}
		System.out.println("-----------------");
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}

}