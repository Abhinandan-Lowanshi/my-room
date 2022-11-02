package com.example.myroom.app.new_ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.myroom.R;
import com.example.myroom.app.LoginFinal;
import com.example.myroom.app.SignUp;
import com.example.myroom.app.changeassword.ChangePasswordModel;
import com.example.myroom.app.forgotpassword.ForgotPasswordModel;
import com.example.myroom.app.forgotpassword.OtpVerificationModel;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import Adapter.SupportValidation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    AppCompatImageView img_back;
    Button submit_email,submit_submit_changePassword,submit_otp;
    ProgressDialog progressDialog;
    String email,password,repassword,otp;
    private RelativeLayout ll_hideShow_pass  ,ll_hideShow_repass ;
    private CheckBox checkbox_pass , checkbox_repass;
    AppCompatEditText tv_email,tv_otp,tv_password,tv_re_enter_password;
    private String repasswordCK="" ;
    private String passwordCK="";
    private TextView re_password_validater ,password_validater ,password_matched;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initview();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        tv_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
                repasswordCK = String.valueOf(charSequence);
                passwordMatchedOrNot();
                validateRePassword(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkbox_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // hide password
                    tv_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    // show password
                    tv_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        checkbox_repass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        submit_submit_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(tv_password.getText().toString().isEmpty())
                    {
                        tv_password.setFocusable(true);
                        tv_password.setError("Current Password can't be empty");
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        Animation shake = AnimationUtils.loadAnimation(ForgotPassword.this, R.anim.shake);
                        tv_password.startAnimation(shake);


                    }
                    else if(tv_re_enter_password.getText().toString().isEmpty())
                    {
                        tv_re_enter_password.setFocusable(true);
                        tv_re_enter_password.setError("Password can't be empty");
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        Animation shake = AnimationUtils.loadAnimation(ForgotPassword.this, R.anim.shake);
                        tv_re_enter_password.startAnimation(shake);

                    }
                    else if(!tv_re_enter_password.getText().toString().equalsIgnoreCase(tv_password.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"Password not matched",Toast.LENGTH_LONG).show();

                    }
                    else if(!SupportValidation.passwordValidation_2(tv_password.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_LONG).show();

                    }if(!SupportValidation.passwordValidation_2(tv_re_enter_password.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_LONG).show();

                    }else
                    {
                        progressDialog.setMessage("Password changing ...");
                        progressDialog.show();

                        String  password = tv_password.getText().toString();
                        String  email = tv_email.getText().toString();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("email", email);
                        jsonObject.addProperty("password",password);


                        ApiClient.getClient().updatePassword(jsonObject).enqueue(new Callback<ChangePasswordModel>() {
                            @Override
                            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                                if(response.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(ForgotPassword.this,response.body().getMessage(),Toast.LENGTH_LONG);

                                    if(response.body().getStatus()==true)
                                    {
                                        tv_password.setEnabled(false);
                                        tv_re_enter_password.setEnabled(false);
                                        submit_submit_changePassword.setText("Password change successfully");

                                        Toast.makeText(ForgotPassword.this,"Password change successfully",Toast.LENGTH_LONG);

                                        Timer time = new Timer();
                                        time.schedule(new TimerTask() {
                                            @Override
                                            public void run() {


                                                Intent intent = new Intent(ForgotPassword.this, LoginFinal.class);
//                                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                                                startActivity(intent);
                                                finishAffinity();
                                            }
                                        }, 2500);

                                    }
                                }else {
                                    tv_password.setEnabled(true);
                                    tv_re_enter_password.setEnabled(true);
                                    submit_submit_changePassword.setText("Proceed to change password");

                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                                progressDialog.dismiss();
                                tv_password.setEnabled(true);
                                tv_re_enter_password.setEnabled(true);
                                submit_submit_changePassword.setText("Proceed to change password");

                            }
                        });



                    }

                }catch (Exception e)
                {
                     e.printStackTrace();
                }


            }
        });

         submit_otp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Log.d("TAG", "onClick: ");
                 otp = tv_otp.getText().toString();
                 if (otp.isEmpty()) {
                        tv_otp.setError("Otp can't be empty");
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        Animation shake = AnimationUtils.loadAnimation(ForgotPassword.this, R.anim.shake);
                        tv_otp.startAnimation(shake);

                    } else {
                        progressDialog.setMessage("Otp verifying .....");
                        progressDialog.show();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("email", email);
                        jsonObject.addProperty("otp", otp);
                        ApiClient.getClient().verifyOtp(jsonObject).enqueue(new Callback<OtpVerificationModel>() {
                            @Override
                            public void onResponse(Call<OtpVerificationModel> call, Response<OtpVerificationModel> response) {

                                if (response.isSuccessful()) {
                                    progressDialog.dismiss();
                                    if (response.body().getStatus() == true) {
                                        if (response.body().getCode() == 200) {
                                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                                            geSubmitPasswordLayout();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                                    }

                                } else {
                                    progressDialog.dismiss();

                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<OtpVerificationModel> call, Throwable t) {
                                progressDialog.dismiss();

                            }
                        });

                    }
             }
         });
        submit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                     email = tv_email.getText().toString();
                    if (email == null) {
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        Animation shake = AnimationUtils.loadAnimation(ForgotPassword.this, R.anim.shake);
                        tv_email.startAnimation(shake);

                    } else if (email.isEmpty()) {
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        Animation shake = AnimationUtils.loadAnimation(ForgotPassword.this, R.anim.shake);
                        tv_email.startAnimation(shake);

                    } else {
                        progressDialog.setMessage("Sending otp to registered email");
                        progressDialog.show();

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("email", email);
                        ApiClient.getClient().forgotpassword(jsonObject).enqueue(new Callback<ForgotPasswordModel>() {
                            @Override
                            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                                try {
                                    progressDialog.dismiss();

                                    if (response.isSuccessful()) {
                                        ForgotPasswordModel forgotPasswordModel = response.body();
                                        if (forgotPasswordModel.getStatus() == true) {
                                            Toast.makeText(ForgotPassword.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                            getOtpLayout();
//                                          Intent intent = new Intent(ForgotPassword.this,LogIn_1.class);
//                                          overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
//                                          startActivity(intent);
//                                          finish();
                                        }
                                        else {
                                            progressDialog.dismiss();
                                            Toast.makeText(ForgotPassword.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        Toast.makeText(ForgotPassword.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                                    }


                                } catch (Exception e) {
                                    Toast.makeText(ForgotPassword.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.d("TAG", "onFailure: "+t.getMessage());
                                Toast.makeText(ForgotPassword.this, "Something went wrong", Toast.LENGTH_LONG).show();

                            }
                        });

                    }


                }catch (Exception e)
                {
                    progressDialog.dismiss();


                    e.printStackTrace();
                                    }
            }
        });

    }

    private void getOtpLayout() {
         tv_email.setVisibility(View.GONE);
        tv_password.setVisibility(View.GONE);
        tv_re_enter_password.setVisibility(View.GONE);
        submit_submit_changePassword.setVisibility(View.GONE);
        submit_email.setVisibility(View.GONE);
        tv_otp.setVisibility(View.VISIBLE);
        submit_otp.setVisibility(View.VISIBLE);



    } private void geSubmitPasswordLayout() {
         tv_email.setVisibility(View.GONE);
        tv_password.setVisibility(View.VISIBLE);
        ll_hideShow_pass.setVisibility(View.VISIBLE);
        ll_hideShow_repass.setVisibility(View.VISIBLE);
        tv_re_enter_password.setVisibility(View.VISIBLE);
        submit_submit_changePassword.setVisibility(View.VISIBLE);
        submit_email.setVisibility(View.GONE);
        tv_otp.setVisibility(View.GONE);
        submit_otp.setVisibility(View.GONE);



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
    private void validatePassword(String charSequence) {

        if(!SupportValidation.passwordValidation_2(charSequence))
        {

            password_validater.setVisibility(View.VISIBLE);

        }else {
            password_validater.setVisibility(View.GONE);


        }
    }
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
        tv_email = (AppCompatEditText) findViewById(R.id.tv_email);
        tv_otp = (AppCompatEditText) findViewById(R.id.tv_otp);
        ll_hideShow_pass = (RelativeLayout) findViewById(R.id.ll_hideShow_pass);
        ll_hideShow_repass = (RelativeLayout) findViewById(R.id.ll_hideShow_repass);
        checkbox_pass = (CheckBox) findViewById(R.id.checkbox_pass);
        re_password_validater = (TextView) findViewById(R.id.re_password_validater);
        password_validater = (TextView) findViewById(R.id.password_validater);
        password_matched = (TextView) findViewById(R.id.password_matched);
        checkbox_repass = (CheckBox) findViewById(R.id.checkbox_repass);
        tv_password = (AppCompatEditText) findViewById(R.id.tv_password);
        tv_re_enter_password = (AppCompatEditText) findViewById(R.id.tv_re_enter_password);
        submit_email = (Button) findViewById(R.id.submit_email);
        submit_submit_changePassword = (Button) findViewById(R.id.submit_submit_changePassword);
        submit_otp = (Button) findViewById(R.id.submit_otp);
        progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setCancelable(false);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
    //7389165256
}