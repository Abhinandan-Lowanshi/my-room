package com.myroom.myroom.app.pushnotification;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import appsession.AppSession;

public class FirebaseTokenGetter {
    private String TAG;

    public static void getToekn(Context context)
    {
        final String token;
        try {


          FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }else
                            new AppSession(context).setToken(task.getResult());
                        // Get new FCM registration token


                        // Log and toast

                    }
                });
        }catch (Exception e)
        {
      e.printStackTrace();
        }
    }
}
