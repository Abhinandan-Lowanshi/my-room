//package com.myroom.myroom.app.pushnotification;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//
//import com.myroom.myroom.R;
//import com.myroom.myroom.app.LoginFinal;
//import com.myroom.myroom.app.loginmanage.ManageSession;
//import com.myroom.myroom.app.new_ui.Notification_Activity;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import appsession.AppSession;
//
//public class MyNotificationService extends FirebaseMessagingService {
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage message) {
//        super.onMessageReceived(message);
//
//
////        if(new AppSession(getApplicationContext()).getNotificationStatus().equalsIgnoreCase("true")) {
//            Log.d("TAG", "onMessageReceived: ");
//        Intent intent ;
//        if(new AppSession(getApplicationContext()).getUserID()!=null && new AppSession(getApplicationContext()).getUserID()!="")
//        intent  = new Intent(getApplicationContext(), Notification_Activity.class);
//        else {   intent  = new Intent(getApplicationContext(), LoginFinal.class);
//            ManageSession.logOut(getApplicationContext());
//        }
//            intent.putExtra("Notification", "Notification");
//            String CHANNEL_ID = "MYCHANNEL";
//        PendingIntent pendingIntent = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
//            pendingIntent = PendingIntent.getActivity
//                    (this, 1, intent, PendingIntent.FLAG_MUTABLE);
//        }
//        else
//        {
//            pendingIntent = PendingIntent.getActivity
//                    (this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
//        }
//            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
////            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
//            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
//                    .setContentText(message.getNotification().getTitle())
////               .setContentTitle(message.getNotification().getTitle())
//                    .setContentIntent(pendingIntent)
//                    .addAction(R.mipmap.logo, "Title", pendingIntent)
//                    .setChannelId(CHANNEL_ID)
//                    .setSmallIcon(R.mipmap.logo)
//                    .build();
//
//            Log.d("Motog", "Title: " + message.getNotification().getTitle());
//            Log.d("Motog", "setContentText: " + message.getNotification().getBody());
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(notificationChannel);
//            notificationManager.notify(1, notification);
////        }
//    }
//}
