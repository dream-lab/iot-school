package org.senml.data;

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
import java.util.ArrayList;

import com.google.gson.Gson;

public class MqttEvent {

	public ArrayList<SenML> e;

	public int getSenMLLenght(){
		return e.size();
	}
	
	
	public MqttEvent() {
		e = new ArrayList<SenML>();
	}

	public void add(SenML val) {
		e.add(val);
	}

	public SenML getSenML(int index) {
		return (SenML) e.get(index);
	}

	public int getSize() {
		return e.size();
	}

	public ArrayList<SenML> getCompleteList() {
		return e;
	}

	public String getJSONReprsentation() {
		return new Gson().toJson(this);

	}

	public static MqttEvent getEventFromJson(String json) {
		return new Gson().fromJson(json, MqttEvent.class);

	}

}
