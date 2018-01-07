package com.app.tuppit.notifications;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by david on 17/10/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String TAG = "FirebaseMessaging";

    private LocalBroadcastManager localBroadcastManager;
    // private final String SERVICE_RESULT = "com.service.result";
    //private final String SERVICE_MESSAGE = "com.service.message";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Other stuff

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.i(TAG, "From: " + remoteMessage.getFrom());

        Log.i(TAG, "Notification Message: " + remoteMessage.getData().get("message"));
        Log.i(TAG, "Notification Message From: " + remoteMessage.getData().get("fromUser"));

        // MyApplication app = (MyApplication) getApplication();

        String fromUser, message, chatId;
        chatId = remoteMessage.getData().get("chatId");
        fromUser = remoteMessage.getData().get("fromUser");
        message = remoteMessage.getData().get("message");

        sendResult(chatId, message, fromUser);
    }

    private void sendResult(String chatId, String message, String fromUser) {
        Intent intent = new Intent("messageReceived");
        if(message != null){
            intent.putExtra("chatId", chatId);
            intent.putExtra("message", message);
            intent.putExtra("fromUser", fromUser);
            localBroadcastManager.sendBroadcast(intent);
        }
    }
}
