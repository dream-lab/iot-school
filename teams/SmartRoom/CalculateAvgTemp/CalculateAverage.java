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
*/

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.senml.data.MqttEvent;
import org.senml.data.SenML;

public class CalculateAverage {

	public static double calculateAverage(MqttMessage message) {

		MqttEvent mqttGetEvent = MqttEvent.getEventFromJson(message.toString());

		double sum = 0;
		int count = 0;

		for (SenML senML : mqttGetEvent.e) {

			sum = sum + senML.getValue().doubleValue();
			count++;
		}
		if (count == 0)
			return 0;
		return sum / count;
	}

}