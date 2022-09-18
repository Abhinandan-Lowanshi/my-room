package com.example.myroom.app.pushnotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.myroom.R;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.new_ui.Notification_Activity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import appsession.AppSession;

public class MyNotificationService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);


        if(new AppSession(getApplicationContext()).getNotificationStatus().equalsIgnoreCase("true")) {
            Log.d("TAG", "onMessageReceived: ");
            Intent intent = new Intent(getApplicationContext(), Notification_Activity.class);
            intent.putExtra("Notification", "Notification");
            String CHANNEL_ID = "MYCHANNEL";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                    .setContentText(message.getNotification().getTitle())
//               .setContentTitle(message.getNotification().getTitle())
                    .setContentIntent(pendingIntent)
                    .addAction(R.mipmap.logo, "Title", pendingIntent)
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(R.mipmap.logo)
                    .build();

            Log.d("Motog", "Title: " + message.getNotification().getTitle());
            Log.d("Motog", "setContentText: " + message.getNotification().getBody());
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(1, notification);
        }
    }
}
