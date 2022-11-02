package com.example.myroom.app.new_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.LoginFinal;
import com.example.myroom.app.SignUp;
import com.example.myroom.app.changeassword.ChangePasswordModel;
import com.example.myroom.app.changepassword.ChangePasswrodModel;
import com.example.myroom.app.forgotpassword.ForgotPasswordModel;
import com.example.myroom.app.loginmanage.ManageSession;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;

import java.util.concurrent.Executor;

import Adapter.SupportValidation;
import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasword extends AppCompatActivity {
    private AppCompatImageView img_back;
    private Button submit;
    private AppCompatImageView img_notification;
    private ProgressDialog progressDialog;
    private AppSession appSession;
    private String cur ="",Pnew ="",Rnew="";
    private AppCompatEditText tv_current_password,tv_new_password,tv_re_enter_password;
    private TextView password_validater ,re_password_validater,password_matched;
    private String repasswordCK ="" , passwordCK="";
    private CheckBox checkbox_cr,checkbox_new , checkbox_newR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pasword);
        initview();


        checkbox_cr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // hide password
                    tv_current_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    // show password
                    tv_current_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        checkbox_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // hide password
                    tv_new_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    // show password
                    tv_new_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        checkbox_newR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // hide password
                    tv_re_enter_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    // show password
                    tv_re_enter_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });


        tv_current_password.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 cur = charSequence.toString();
                 buttonVisibilityControl(cur,Pnew,Rnew);
             }

             @Override
             public void afterTextChanged(Editable editable) {

             }
         });
           tv_new_password.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 Pnew = charSequence.toString();
                 buttonVisibilityControl(cur,Pnew,Rnew);
                 passwordCK = String.valueOf(charSequence);
                 passwordMatchedOrNot();
                 validatePassword(String.valueOf(charSequence));
             }

             @Override
             public void afterTextChanged(Editable editable) {

             }
         });
             tv_re_enter_password.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 Rnew = charSequence.toString();
                 buttonVisibilityControl(cur,Pnew,Rnew);
                 repasswordCK = String.valueOf(charSequence);
                 passwordMatchedOrNot();
                 validateRePassword(String.valueOf(charSequence));

             }

             @Override
             public void afterTextChanged(Editable editable) {

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

                Intent intent = new Intent(ChangePasword.this, Notification_Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }
    @SuppressLint("ResourceAsColor")
    private void validatePassword(String charSequence) {

        if(!SupportValidation.passwordValidation_2(charSequence))
        {

            password_validater.setVisibility(View.VISIBLE);

        }else {
            password_validater.setVisibility(View.GONE);


        }
    }
    private void passwordMatchedOrNot()
    {
        if(passwordCK.equals(repasswordCK))
        {
            password_matched.setVisibility(View.GONE);
        }else {
            password_matched.setVisibility(View.VISIBLE);
        }
    }
    @SuppressLint("ResourceAsColor")
    private void validateRePassword(String charSequence) {

        if(!SupportValidation.passwordValidation_2(charSequence))
        {

            re_password_validater.setVisibility(View.VISIBLE);

        }else {
            re_password_validater.setVisibility(View.GONE);


        }
    }
    private void initview() {
        img_back = (AppCompatImageView)findViewById(R.id.img_back);
        password_validater = (TextView)findViewById(R.id.password_validater);
        re_password_validater = (TextView)findViewById(R.id.re_password_validater);
        password_matched = (TextView)findViewById(R.id.password_matched);
        tv_re_enter_password = (AppCompatEditText) findViewById(R.id.tv_re_enter_password);
        tv_current_password = (AppCompatEditText) findViewById(R.id.tv_current_password);
        tv_new_password = (AppCompatEditText) findViewById(R.id.tv_new_password);
        submit = (Button) findViewById(R.id.submit);
        checkbox_cr = (CheckBox) findViewById(R.id.checkbox_cr);
        checkbox_newR = (CheckBox) findViewById(R.id.checkbox_newR);
        checkbox_new = (CheckBox) findViewById(R.id.checkbox_new);
        img_notification = (AppCompatImageView) findViewById(R.id.img_notification);
        progressDialog = new ProgressDialog(ChangePasword.this);
        progressDialog.setMessage("Working on it..");
        progressDialog.setCancelable(false);
       appSession = new AppSession(ChangePasword.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                changePasswordOperation();

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }


    private void buttonVisibilityControl(String cur ,String pnew , String rnew)
    {
         if(cur.isEmpty()||pnew.isEmpty()||rnew.isEmpty())
         {
             submit.setEnabled(false);
             submit.setAlpha(.4f);
         }else {
             submit.setEnabled(true);
             submit.setAlpha(1f);
         }

    }

    private void changePasswordOperation()
    {

        try {

            if(appSession.getUserID()!=null) {

                if(tv_current_password.getText().toString().isEmpty())
                {
                    tv_current_password.setFocusable(true);
                    tv_current_password.setError("Current Password can't be empty");
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    Animation shake = AnimationUtils.loadAnimation(ChangePasword.this, R.anim.shake);
                    tv_current_password.startAnimation(shake);
                }else if(tv_new_password.getText().toString().isEmpty()&&tv_new_password.getText().toString().length()<7)
                {
                    tv_new_password.setFocusable(true);
                    tv_new_password.setError("Password can't be empty");
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    Animation shake = AnimationUtils.loadAnimation(ChangePasword.this, R.anim.shake);
                    tv_new_password.startAnimation(shake);
                }else if(!SupportValidation.passwordValidation_2(tv_new_password.getText().toString()))
                {
                    tv_new_password.setFocusable(true);
                    tv_new_password.setError("Invalid Password");
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    Animation shake = AnimationUtils.loadAnimation(ChangePasword.this, R.anim.shake);
                    tv_new_password.startAnimation(shake);
                }else if(!SupportValidation.passwordValidation_2(tv_re_enter_password.getText().toString()))
                {
                    tv_re_enter_password.setFocusable(true);
                    tv_re_enter_password.setError("Invalid Password");
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    Animation shake = AnimationUtils.loadAnimation(ChangePasword.this, R.anim.shake);
                    tv_re_enter_password.startAnimation(shake);
                }else if(tv_re_enter_password.getText().toString().isEmpty())
                {
                    tv_re_enter_password.setFocusable(true);
                    tv_re_enter_password.setError("Password can't be empty");
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    Animation shake = AnimationUtils.loadAnimation(ChangePasword.this, R.anim.shake);
                    tv_re_enter_password.startAnimation(shake);
                }
                else if(!tv_re_enter_password.getText().toString().equals(tv_new_password.getText().toString()))
                {
//                    tv_re_enter_password.setFocusable(true);
//                    tv_re_enter_password.setError("New Password can't be empty or shorter than 7");
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    Animation shake = AnimationUtils.loadAnimation(ChangePasword.this, R.anim.shake);
                     overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    tv_re_enter_password.startAnimation(shake);
                    tv_new_password.startAnimation(shake);
                    tv_re_enter_password.setError("Password not matched");

                }else {

                    progressDialog.show();

                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("user_id", appSession.getUserID());
                    jsonObject.addProperty("old_password", tv_current_password.getText().toString());
                    jsonObject.addProperty("new_password", tv_new_password.getText().toString());

                    ApiClient.getClient().changePassword(jsonObject).enqueue(new Callback<ChangePasswrodModel>() {
                        @Override
                        public void onResponse(Call<ChangePasswrodModel> call, Response<ChangePasswrodModel> response) {

                            if(response.isSuccessful())
                            {
                                progressDialog.dismiss();
                                if(response.body().getStatus()==true)
                                {

//
                                    Toast.makeText(ChangePasword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    onBackPressed();
//                                    Intent intent = new Intent(ChangePasword.this, LoginFinal.class);
////                                    ManageSession.logOut(getApplicationContext());
//                                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
//                                    startActivity(intent);
//                                    finishAffinity();


                                }else
                                    Toast.makeText(ChangePasword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(ChangePasword.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswrodModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(ChangePasword.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }else {

                progressDialog.dismiss();
                Intent intent = new Intent(ChangePasword.this, LoginFinal.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                finishAffinity();
            }
        }catch (Exception e)
        {
            progressDialog.dismiss();
             e.printStackTrace();
        }

    }
}

