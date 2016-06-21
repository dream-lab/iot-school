package com.abhilash.chatmqtt;

import android.util.Log;

import com.abhilash.smartcampusmqttlib.HelperClass.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Abhilash on 17/06/2016.
 */
public class ChatMessage {

    private String username,message;

    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public ChatMessage(String jsonMessage) {
        try{
            JSONObject message = new JSONObject(jsonMessage);
            this.username = message.getString(ChatConstants.USERNAME_JSON_KEY);
            this.message = message.getString(ChatConstants.MESSAGE_JSON_KEY);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String parseMessageToJson()
    {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put(ChatConstants.USERNAME_JSON_KEY,username);
            jsonObject.put(ChatConstants.MESSAGE_JSON_KEY,message);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
