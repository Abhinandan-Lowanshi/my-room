package com.example.myroom.app.new_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myroom.R;

public class PrivacyPolicy extends AppCompatActivity {
 AppCompatImageView img_back;
    AppCompatImageView img_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        initView();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);


            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PrivacyPolicy.this, Notification_Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }

    private void initView() {
        img_back = (AppCompatImageView)findViewById(R.id.img_back);
        img_notification = (AppCompatImageView) findViewById(R.id.img_notification);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
}