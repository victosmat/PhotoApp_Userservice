package com.example.photo_app.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class MyFirebaseMessagingService {

//    @Override
//    public void onNewToken(@NonNull String token) {
//        super.onNewToken(token);
//        //submit to save when you need
//        Log.d(Utils.TAG,token);
//    }
//
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage message) {
//        super.onMessageReceived(message);
//        //Show notification when we receive it
//        String title = message.getNotification().getTitle();
//        String content = message.getNotification().getBody();
//        String data = new Gson().toJson(message.getData());
//
//        //create notification to show
//        Utils.showNotification(this,title,content);
//        //show raw data by log
//        Log.d(Utils.TAG,data);
//    }
}
