package com.myroom.myroom.app.new_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.myroom.myroom.R;

public class Contact_Us extends AppCompatActivity {
   AppCompatImageView img_back;
   AppCompatTextView tv_phone,tv_whatsapp,tv_email,tv_custom_message;
   RelativeLayout rl_phone,rl_whatsapp,rl_email;
   LinearLayout ll_custom_message;
    AppCompatImageView img_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        initView();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        rl_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tv_phone.getText().toString()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        rl_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",tv_email.getText().toString(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        tv_custom_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ll_custom_message.setVisibility(View.VISIBLE);
            }
        });

        rl_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+91"+tv_whatsapp.getText().toString(); // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = Contact_Us.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    //i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");

                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(Contact_Us.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Contact_Us.this, Notification_Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }

    private void initView() {
        img_back = (AppCompatImageView)findViewById(R.id.img_back);
        rl_phone = (RelativeLayout)findViewById(R.id.rl_phone);
        rl_whatsapp = (RelativeLayout)findViewById(R.id.rl_whatsapp);
        rl_email = (RelativeLayout)findViewById(R.id.rl_email);
        img_notification = (AppCompatImageView) findViewById(R.id.img_notification);

        tv_phone=(AppCompatTextView) findViewById(R.id.tv_phone);
        tv_custom_message=(AppCompatTextView) findViewById(R.id.tv_custom_message);
        tv_whatsapp=(AppCompatTextView) findViewById(R.id.tv_whatsapp);
        tv_email=(AppCompatTextView) findViewById(R.id.tv_email);
        ll_custom_message=(LinearLayout) findViewById(R.id.ll_custom_message);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
}