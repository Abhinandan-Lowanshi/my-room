package com.myroom.myroom.app.new_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.myroom.myroom.R;
import com.myroom.myroom.app.LoginFinal;

import appsession.AppSession;

public class MyAccount extends AppCompatActivity {
  AppCompatImageView img_back;

    AppCompatImageView img_notification;
  ProgressDialog  progressDialog;
    AppSession appSession;
  CardView card_password,card_contact_us,card_privacy,card_about_us,card_logout,card_edit_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        initview();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,ChangePasword.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,Edit_Profile.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,Contact_Us.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,PrivacyPolicy.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccount.this,AboutUs.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MyAccount.this);
                dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.logout_dialogue);
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                AppCompatImageView img_close = dialog.findViewById(R.id.img_close);
                Button yes = dialog.findViewById(R.id.yes);
                Button no = dialog.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });  img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(MyAccount.this, LoginFinal.class);
                        appSession.setIsLogin("0");
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

                        startActivity(intent);
                        finish();

                    }
                });


                dialog.show();




            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyAccount.this, Notification_Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }

    private void initview() {
        img_back = (AppCompatImageView)findViewById(R.id.img_back);
        card_password = (CardView) findViewById(R.id.card_password);
        card_contact_us = (CardView) findViewById(R.id.card_contact_us);
        card_privacy = (CardView) findViewById(R.id.card_privacy);
        card_edit_profile = (CardView) findViewById(R.id.card_edit_profile);
        card_about_us = (CardView) findViewById(R.id.card_about_us);
        card_logout = (CardView) findViewById(R.id.card_logout);
        img_notification = (AppCompatImageView) findViewById(R.id.img_notification);

        appSession = new AppSession(MyAccount.this);
        progressDialog = new ProgressDialog(MyAccount.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging out");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
}

