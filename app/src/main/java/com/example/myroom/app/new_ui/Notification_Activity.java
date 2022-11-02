package com.example.myroom.app.new_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myroom.R;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.new_ui_adapter.Notification_Adapter;
import com.example.myroom.app.pushnotification.NotificationModelAPI;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_Activity extends AppCompatActivity {
    AppCompatImageView img_back;
    RecyclerView rec_notification;
    String notification = "non";
    private TextView empty_text;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initview();
        getNotification();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                  }


        });
    }

    private void getNotification() {


         try {
             JsonObject jsonObject = new JsonObject();
             jsonObject.addProperty("id", new AppSession(Notification_Activity.this).getUserID());

             ApiClient.getClient().getNotification(jsonObject).enqueue(new Callback<NotificationModelAPI>() {
                 @Override
                 public void onResponse(Call<NotificationModelAPI> call, Response<NotificationModelAPI> response) {
                     if(response.isSuccessful())
                     {

                         progressDialog.dismiss();
                         if(response.body().getStatus()==true)
                         {
                             if(response.body().getData().size()>0)
                             {
                                 empty_text.setVisibility(View.GONE);
                                 NotificationModelAPI notificationModelAPI = response.body();
                                 Notification_Adapter notification_adapter =  new Notification_Adapter(Notification_Activity.this,Notification_Activity.this,notificationModelAPI.getData());
                                 rec_notification.setAdapter(notification_adapter);
                             }else{
                                 empty_text.setVisibility(View.VISIBLE);
                             }

                         }else {
                             empty_text.setVisibility(View.VISIBLE);
                         }
                     }else {
                         progressDialog.dismiss();
                         empty_text.setVisibility(View.VISIBLE);
                     }
                 }

                 @Override
                 public void onFailure(Call<NotificationModelAPI> call, Throwable t) {
                     progressDialog.dismiss();
                     empty_text.setVisibility(View.VISIBLE);
                 }
             });

         }catch (Exception e)
         {
             progressDialog.dismiss();
             empty_text.setVisibility(View.VISIBLE);
             e.printStackTrace();
         }

    }

    private void initview() {

//        JSONObject json = new JSONObject(string);
//        System.out.println(json.toString());
//        String technology = json.getString("technology");

        progressDialog = new ProgressDialog(Notification_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting notification.....");
        progressDialog.show();
        img_back = (AppCompatImageView)findViewById(R.id.img_back);
        empty_text = (TextView) findViewById(R.id.empty_text);
        rec_notification = (RecyclerView) findViewById(R.id.rec_notification);

        Intent intent = getIntent();
        notification = intent.getStringExtra("Notification");
        if(notification==null)
        {
             notification="null";
        }
    }
    @Override
    public void onBackPressed() {

        if(notification.equalsIgnoreCase("Notification"))
        {
            Intent intent = new Intent(Notification_Activity.this, NewHomeActivityFR.class);
            startActivity(intent);
        }else {
            super.onBackPressed();
//            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }



    }
}