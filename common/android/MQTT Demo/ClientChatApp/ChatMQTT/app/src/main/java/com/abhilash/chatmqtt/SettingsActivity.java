package com.abhilash.chatmqtt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import static com.abhilash.chatmqtt.ChatConstants.*;
import com.abhilash.smartcampusmqttlib.HelperClass.CommonUtils;

public class SettingsActivity extends AppCompatActivity{

    private EditText chatUserNameEditText, topicEditText;
    private static String chatUsername, topic;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        chatUserNameEditText = (EditText) findViewById(R.id.input_username);
        topicEditText = (EditText) findViewById(R.id.input_topic);

        populateValuesFromSharedPreferences();
    }

    public void save(View v)
    {
        CommonUtils.showToast(SettingsActivity.this,"Settings Saved");
        chatUsername = chatUserNameEditText.getText().toString();
        topic = topicEditText.getText().toString();
        updateSharedPreferences();
    }

    private void updateSharedPreferences () {
        sharedpreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        CommonUtils.printLog("trying to update shared preference");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(CHAT_APP_DEFAULT_USERNAME_KEY, chatUsername);
        editor.putString(CHAT_APP_TOPIC_KEY, topic);
        editor.commit();
    }

    private void populateValuesFromSharedPreferences()
    {
        sharedpreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        chatUsername = sharedpreferences.getString(CHAT_APP_DEFAULT_USERNAME_KEY,CHAT_APP_DEFAULT_USERNAME);
        chatUserNameEditText.setText(chatUsername);
        topic = sharedpreferences.getString(CHAT_APP_TOPIC_KEY,CHAT_APP_TOPIC);
        topicEditText.setText(topic);
    }


    public static String getChatUsername() {
        if(chatUsername == null || chatUsername.isEmpty())
            return "user";
        else
            return chatUsername;
    }

    public static void setChatUsername(String chatUsername) {
        SettingsActivity.chatUsername = chatUsername;
    }

    public static String getTopic() {
        if(topic == null || topic.isEmpty())
            return "iisc/summerschool/iot/chat";
        else
            return topic;
    }

    public static void setTopic(String topic) {
        SettingsActivity.topic = topic;
    }

    public static Intent startIntent(Context callingContext)
    {
        Intent intent = new Intent();
        intent.setClass(callingContext,SettingsActivity.class);
        return intent;
    }
}
