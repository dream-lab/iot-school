package com.abhilash.chatmqtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.abhilash.smartcampusmqttlib.HelperClass.CommonUtils;
import com.abhilash.smartcampusmqttlib.IncomingHandler;
import com.abhilash.smartcampusmqttlib.Interfaces.ServiceCallback;
import com.abhilash.smartcampusmqttlib.Interfaces.ServiceStatusCallback;
import com.abhilash.smartcampusmqttlib.SCServiceConnector;
import com.abhilash.smartcampusmqttlib.ServiceAdapter;
import com.abhilash.smartcampusmqttlib.SmartXLibConstants;

import static com.abhilash.chatmqtt.ChatConstants.*;

import java.lang.Byte;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ServiceCallback,SmartXLibConstants,ServiceStatusCallback {
    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    List<ChatMessage> messages;
    EditText messageEditText;
    Button sendButton;
    SharedPreferences sharedpreferences;

    ServiceAdapter serviceAdapter;
    Messenger clientMessenger;
    SCServiceConnector connector;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateSharedPreferences();
        fetchCredentialsFromSharedPreferences();
        serviceAdapter = new  ServiceAdapter(getApplicationContext());
        connector = new SCServiceConnector(this);

        //set up a service to receive messages in the background and setup broadcast receiver in the service
        //startService(new Intent(this, ChatReceiverService.class));

        //setup messenger to interact the master app service
        clientMessenger = new Messenger(new IncomingHandler(getApplicationContext(), this));

        //setup broadcast receiver to receive incoming messages
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                CommonUtils.printLog("Broadcast received in Chat App Screen");
                String message = intent.getStringExtra("message");
                ChatMessage chatMessage = new ChatMessage(message);

                /*
                since we are publishing and are subscribed to the same topic, we'll be having two instances of the message
                one created while sending and one which we'll be receiving
                here is a small hack to prevent displaying our sent message twice
                */
                if(!chatMessage.getUsername().equals(SettingsActivity.getChatUsername()))
                {
                    messages.add(chatMessage);
                    chatAdapter.notifyItemInserted(messages.size()-1);
                    recyclerView.scrollToPosition(messages.size()-1);
                }
            }
        };

        messages = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.chatList);
        messageEditText = (EditText) findViewById(R.id.messageText);
        sendButton = (Button) findViewById(R.id.messageSendButton);


        //setup RecyclerView (or ListView) to display the list of messages
        final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(messages);
        recyclerView.setAdapter(chatAdapter);


        messageEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(messages.size()-1);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SettingsActivity.getChatUsername() == null || SettingsActivity.getChatUsername().isEmpty())
                {
                    CommonUtils.showToast(MainActivity.this,"Set Username from Settings first");
                    return;
                }
                if(messageEditText.length() > 0)
                {
                    ChatMessage chatMessage = new ChatMessage(SettingsActivity.getChatUsername(),messageEditText.getText().toString());
                    messages.add(chatMessage);
                    chatAdapter.notifyItemInserted(messages.size()-1);
                    recyclerView.scrollToPosition(messages.size()-1);
                    messageEditText.setText("");

                    //api call to master app to publish data to the topic mentioned in activity (SettingsActivity)
                    serviceAdapter.publishGlobal(SettingsActivity.getTopic(),null,chatMessage.parseMessageToJson(),clientMessenger);
                }
            }
        });
    }

    public void bindService () {
        if (!serviceAdapter.serviceConnected()) {
            serviceAdapter.startAndBindToService(connector);
            return;
        }
        else
        {
            subscribeToTopic();
            CommonUtils.showToast(this,"Service connected already");
        }
    }

    public void unbindService () {
        if (serviceAdapter.serviceConnected()) {
            serviceAdapter.unbindFromService(connector);
            return;
        }
        CommonUtils.showToast(this,"Service not connected anyways");
    }

    public void subscribeToTopic() {
        if (serviceAdapter.serviceConnected() == false) {
            CommonUtils.showToast(getApplicationContext(),"Service not connected");
            return;
        }
        serviceAdapter.subscribeToTopic(SettingsActivity.getTopic(),clientMessenger);
    }

    public void unsubscribeToTopic () {
        if (serviceAdapter.serviceConnected() == false) {
            CommonUtils.showToast(getApplicationContext(),"Service not connected");
            return;
        }
        serviceAdapter.unsubscribeFromTopic(SettingsActivity.getTopic(),clientMessenger);
    }

    @Override
    public void serviceConnected () {
        CommonUtils.printLog("Chat App : Service connected");
        CommonUtils.showToast(this,"Chat App : Service connected");
        subscribeToTopic();
    }
    @Override
    public void serviceDisconnected () {
        CommonUtils.printLog("Chat App : Service Disconnected");
        CommonUtils.showToast(MainActivity.this,"Chat App : Service Disconnected");
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService();
        setupBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unsubscribeToTopic();
        //unbindService();
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageReceivedFromService(int i) {
        /*
        this is the callback received from the master app service for events
         */
        switch (i) {
            case SUBSCRIPTION_SUCCESS:
                setupBroadcastReceiver();
                CommonUtils.showToast(MainActivity.this,"Subscribed to topic successfully");
                break;
            case SUBSCRIPTION_ERROR:
                CommonUtils.showToast(MainActivity.this,"Could NOT subscribe to topic, try again!");
                break;
            case UNSUBSCRIPTION_SUCCESS:
                CommonUtils.showToast(MainActivity.this,"Un-subscribed from topic successfully");
                break;
            case UNSUBSCRIPTION_ERROR:
                CommonUtils.showToast(MainActivity.this,"Could NOT un-subscribe from topic, try again!");
                break;
            case NO_NETWORK_AVAILABLE:
                CommonUtils.showToast(MainActivity.this,"Network unavailable, try again later!");
                break;
            case MQTT_NOT_CONNECTED:
                CommonUtils.showToast(MainActivity.this,"MQTT not connected: service unavailable, try again!");
                break;
            case TOPIC_PUBLISHED:
                //CommonUtils.showToast(MainActivity.this,"Chat Sent");
                break;
            case ERROR_IN_PUBLISHING:
                CommonUtils.showToast(MainActivity.this,"Chat NOT sent");
                break;
        }
    }


    //[broadcast receiver]
    private void setupBroadcastReceiver () {
        if (SettingsActivity.getTopic() == null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SettingsActivity.getTopic());
        try {
            registerReceiver(broadcastReceiver, intentFilter);
        } catch (IllegalArgumentException ex) {
            // recevier already registered
            ex.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                startActivity(SettingsActivity.startIntent(MainActivity.this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSharedPreferences () {
        sharedpreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        CommonUtils.printLog("trying to update shared preference");
        if (sharedpreferences.contains(PACKAGE_NAME) == false || sharedpreferences.getString(PACKAGE_NAME,"").equals("")) {
            String packageName = getApplicationContext().getPackageName();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(PACKAGE_NAME, packageName);
            editor.commit();
        }
    }

    private void fetchCredentialsFromSharedPreferences()
    {
        sharedpreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SettingsActivity.setChatUsername(sharedpreferences.getString(CHAT_APP_DEFAULT_USERNAME_KEY,CHAT_APP_DEFAULT_USERNAME));
        SettingsActivity.setTopic(sharedpreferences.getString(CHAT_APP_TOPIC_KEY,CHAT_APP_TOPIC));
    }
}
