package com.myroom.myroom.app.fragment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.myroom.myroom.R;
import com.myroom.myroom.app.chat.User_Chat_Activity;
import com.myroom.myroom.app.fragment.HomeFragment;
import com.myroom.myroom.app.fragment.NewFavFragment;
import com.myroom.myroom.app.fragment.NewMyAccountFragment;
import com.myroom.myroom.app.fragment.NewMyPostFragment;
import com.myroom.myroom.app.fragment.NewUploadFragment;
import com.myroom.myroom.app.new_ui.Notification_Activity;
import com.myroom.myroom.app.new_ui.Search_Activity;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class NewHomeActivityFR extends AppCompatActivity {
    private static final String ROOT_FRAGMENT_TAG = "10";
    private ChipNavigationBar chipNavigationBar;
    private AppCompatImageView img_notification, img_search, img_chat;
    private int count = 0;
    private RelativeLayout no_internet_rl, main_rl;
    private  FragmentManager fragmentManager;
    private   FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home_fr);
        chipNavigationBar = findViewById(R.id.bottom_nav_bar);
        initview();
        loadFragment(this, new HomeFragment(), 0);
        chipNavigationBar.setItemSelected(R.id.home, true);

        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewHomeActivityFR.this, Notification_Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        img_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NewHomeActivityFR.this, User_Chat_Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewHomeActivityFR.this, Search_Activity.class);
                startActivity(intent);
            }
        });
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        loadFragment(NewHomeActivityFR.this, new HomeFragment(), 1);
                        break;
                    case R.id.forever_rent:
                        loadFragment(NewHomeActivityFR.this, new NewUploadFragment(), 1);
                        break;
                    case R.id.Saved:
                        loadFragment(NewHomeActivityFR.this, new NewMyPostFragment(), 1);
                        break;
                    case R.id.favourite:
                        loadFragment(NewHomeActivityFR.this, new NewFavFragment(), 1);
                        break;
                    case R.id.MyAccount:
                        loadFragment(NewHomeActivityFR.this, new NewMyAccountFragment(), 1);

                        break;

                }

            }
        });

    }

    private void initview() {
        img_search = (AppCompatImageView) findViewById(R.id.img_search);
        no_internet_rl = (RelativeLayout) findViewById(R.id.no_internet_rl);
        main_rl = (RelativeLayout) findViewById(R.id.main_rl);
        img_notification = (AppCompatImageView) findViewById(R.id.img_notification);
        img_chat = (AppCompatImageView) findViewById(R.id.img_chat);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("Notification");
        if (msg != null) {
            if (msg.equalsIgnoreCase("Notification")) {
                Intent intent1 = new Intent(NewHomeActivityFR.this, Notification_Activity.class);
                startActivity(intent1);
            }
        }
    }

    private void loadFragment(Context context, Fragment fragment, int fg) {

//        try {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            if (fg == 0) {
                fragmentTransaction.add(R.id.main_container, fragment);
                fragmentManager.popBackStack(ROOT_FRAGMENT_TAG, 0);
                fragmentTransaction.commit();
            } else {
                fragmentTransaction.replace(R.id.main_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }catch (Exception e)
        {

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}