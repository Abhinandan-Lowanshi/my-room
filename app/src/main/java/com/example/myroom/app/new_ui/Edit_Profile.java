package com.example.myroom.app.new_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.LoginFinal;
import com.example.myroom.app.SignUp;
import com.example.myroom.app.editprofile.EditProfileModel;
import com.example.myroom.app.loginmanage.ManageSession;
import com.example.myroom.app.loginmanage.UserData;
import com.example.myroom.app.myaccount.MyAccountModelData;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Profile extends AppCompatActivity {
    AppCompatImageView img_back;
    EditText ed_Fname,ed_Lname,ed_mobile,ed_presentaddress,ed_permanentaddress;
    Button save_profile;
    AppSession appSession;
    private ProgressDialog progressDialog;
    MyAccountModelData myAccountModelData;
    AppCompatImageView img_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initview();

        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
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

                Intent intent = new Intent(Edit_Profile.this, Notification_Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });

    }

    private void saveProfile() {

         try {
              if(ed_Fname.getText().toString().isEmpty()) {
                  ed_Fname.setFocusable(true);
                  ed_Fname.setError("Firs Name can't be empty");
                  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                  Animation shake = AnimationUtils.loadAnimation(Edit_Profile.this, R.anim.shake);
                  ed_Fname.startAnimation(shake);

              }else if(ed_Lname.getText().toString().isEmpty())
              {
                  ed_Lname.setFocusable(true);
                  ed_Lname.setError("Last Name can't be empty");
                  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                  Animation shake = AnimationUtils.loadAnimation(Edit_Profile.this, R.anim.shake);
                  ed_Lname.startAnimation(shake);

              }else if(ed_mobile.getText().toString().isEmpty()||ed_mobile.getText().toString().length()!=10)
              {
                  ed_mobile.setFocusable(true);
                  ed_mobile.setError("Mobile No can't be empty or Invalid");
                  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                  Animation shake = AnimationUtils.loadAnimation(Edit_Profile.this, R.anim.shake);
                  ed_mobile.startAnimation(shake);

              }else if(ed_presentaddress.getText().toString().isEmpty())
              {
                  ed_presentaddress.setFocusable(true);
                  ed_presentaddress.setError("Present Address can't be empty");
                  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                  Animation shake = AnimationUtils.loadAnimation(Edit_Profile.this, R.anim.shake);
                  ed_presentaddress.startAnimation(shake);

              }else if(ed_permanentaddress.getText().toString().isEmpty())
              {
                  ed_permanentaddress.setFocusable(true);
                  ed_permanentaddress.setError("Permanent Address can't be empty");
                  overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                  Animation shake = AnimationUtils.loadAnimation(Edit_Profile.this, R.anim.shake);
                  ed_permanentaddress.startAnimation(shake);

              }else {

                   try {

                        if(appSession.getUserID()!=null)
                        {
                            progressDialog.show();
                            JsonObject jsonObject = new JsonObject();

                            jsonObject.addProperty("user_id", appSession.getUserID());
                            jsonObject.addProperty("usr_firstName", ed_Fname.getText().toString());
                            jsonObject.addProperty("usr_lastName", ed_Lname.getText().toString());
                            jsonObject.addProperty("usr_phone", ed_mobile.getText().toString());
                            jsonObject.addProperty("usr_parmentAdrss", ed_permanentaddress.getText().toString());
                            jsonObject.addProperty("usr_currentAdrss", ed_presentaddress.getText().toString());

                            ApiClient.getClient().editUserProfile(jsonObject).enqueue(new Callback<EditProfileModel>() {
                                @Override
                                public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {

                                     if(response.isSuccessful())
                                     {
                                         progressDialog.dismiss();
                                         if(response.body().getStatus()==true)
                                         {
                                             UserData userData = new UserData(ed_Fname.getText().toString() , ed_Lname.getText().toString() ,ed_mobile.getText().toString(),"" ,ed_presentaddress.getText().toString(),ed_permanentaddress.getText().toString());
                                             ManageSession.updateUserData(Edit_Profile.this,userData);
                                             Toast.makeText(Edit_Profile.this, "Profile successfully Updated", Toast.LENGTH_SHORT).show();
                                             onBackPressed();
                                         }else Toast.makeText(Edit_Profile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                     }else
                                     {
                                         Toast.makeText(Edit_Profile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                         progressDialog.show();
                                     }




                                }

                                @Override
                                public void onFailure(Call<EditProfileModel> call, Throwable t) {
                                    Toast.makeText(Edit_Profile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    progressDialog.show();
                                }
                            });
                        }else {

                            Intent intent = new Intent(Edit_Profile.this, LoginFinal.class);
                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                             finishAffinity();
                        }


                   }catch (Exception e)
                   {
                        e.printStackTrace();
                   }


              }

         }catch (Exception e)
         {
              e.printStackTrace();
         }

    }

    private void initview() {
     appSession = new AppSession(Edit_Profile.this);
     appSession.setIsProfileUpdated("0");
        img_back = (AppCompatImageView)findViewById(R.id.img_back);
        ed_mobile = (EditText)findViewById(R.id.ed_mobile);
        ed_Fname = (EditText)findViewById(R.id.ed_Fname);
        ed_Lname = (EditText)findViewById(R.id.ed_Lname);
        ed_permanentaddress = (EditText)findViewById(R.id.ed_permanentaddress);
        ed_presentaddress = (EditText)findViewById(R.id.ed_presentaddress);
        img_notification = (AppCompatImageView) findViewById(R.id.img_notification);
        save_profile = (Button) findViewById(R.id.save_profile);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Working on it....");
        myAccountModelData = new MyAccountModelData();
        myAccountModelData = (MyAccountModelData) getIntent().getSerializableExtra(appSession.USER_DATA);
        Log.d("TAG", "initview: " + myAccountModelData.getUsrCurrentAdrss());
        ed_Fname.setText(myAccountModelData.getUsrFirstName());
        ed_Lname.setText(myAccountModelData.getUsrLastName());
        ed_mobile.setText(myAccountModelData.getUsrPhone());
        ed_permanentaddress.setText(myAccountModelData.getUsrParmentAdrss());
        ed_presentaddress.setText(myAccountModelData.getUsrCurrentAdrss());

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
}