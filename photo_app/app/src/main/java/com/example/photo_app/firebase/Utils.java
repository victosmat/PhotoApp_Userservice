package com.example.photo_app.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class Utils {
//    public static final String TAG = "FIREBASE";
//    public static void showNotification(Context context, String title, String body) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
//                .setSmallIcon(android.R.drawable.ic_dialog_info)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setPriority(NotificationCompat.PRIORITY_MAX);
//
//        //style
//        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
//        bigTextStyle.bigText(title);
//        bigTextStyle.setBigContentTitle(title);
//        bigTextStyle.setSummaryText(title);
//        builder.setStyle(bigTextStyle);
//
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
//            String channelId = "CHANNEL_ID";
//            NotificationChannel channel = new NotificationChannel(channelId, "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
//            manager.createNotificationChannel(channel);
//            builder.setChannelId(channelId);
//        }
//        manager.notify(new Random().nextInt(), builder.build());
//    }
}
