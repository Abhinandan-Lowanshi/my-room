package com.example.myroom.app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.changeassword.ChangePasswordModel;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.loginmanage.ManageSession;
import com.example.myroom.app.retrofit.ApiClient;
import com.example.myroom.app.signup.SignUpData;
import com.example.myroom.app.signup.SignUpModel;
import com.example.myroom.app.signup.emailverification.EmailVericationModel;
import com.example.myroom.app.signup.emailverification.VerifiyOtpModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import Adapter.SupportValidation;
import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    private Button submit;
    private ProgressDialog progressDialog;
    private boolean isEmailVerified = false;
    private String passwordCK = "", repasswordCK = "";
    private AppCompatImageView img_back;
    private TextView password_validater, email_validater, re_password_validater, password_matched, tv_isEmailVrfd;
    private EditText name, surname, email, mobile, password, repassword, present, permanent;
    private AppCompatImageView img_verifyEmail;
    private CheckBox checkbox_pass, checkbox_pass_re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();

        checkbox_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });
        checkbox_pass_re.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    repassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                validation();

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        tv_isEmailVrfd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                sendOtp();

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePassword(String.valueOf(charSequence));
                passwordCK = String.valueOf(charSequence);
                passwordMatchedOrNot();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        repassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateRePassword(String.valueOf(charSequence));
                repasswordCK = String.valueOf(charSequence);
                passwordMatchedOrNot();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateEmail(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void sendOtp() {
        try {
            if (!email.getText().toString().isEmpty()) {
                progressDialog.setMessage("Sending otp....");
                progressDialog.show();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("email", email.getText().toString());

                ApiClient.getClient().sendEmailOtp(jsonObject).enqueue(new Callback<EmailVericationModel>() {
                    @Override
                    public void onResponse(Call<EmailVericationModel> call, Response<EmailVericationModel> response) {

                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            if (response.body().getStatus() == true) {
                                Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                isEmailVerifiedDialogue();
                            } else {
                                Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<EmailVericationModel> call, Throwable t) {
                        Log.d("TAG", "onFailure: "+t.toString());
                        progressDialog.dismiss();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }


    }

    private void isEmailVerifiedDialogue() {
        final Dialog dialog = new Dialog(SignUp.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.emaiverificationdialogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        AppCompatImageView img_close = dialog.findViewById(R.id.img_close);
        AppCompatEditText tv_enter_otp = dialog.findViewById(R.id.tv_enter_otp);
        AppCompatEditText tv_email = dialog.findViewById(R.id.tv_email);
        Button submit = dialog.findViewById(R.id.submit);

        tv_email.setText(email.getText().toString());
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Verifying otp...");
                progressDialog.show();
                String otp = "";
                otp = tv_enter_otp.getText().toString();

                if (otp.isEmpty()) {
                    tv_enter_otp.setError("Otp can't be empty");
                } else {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("email", tv_email.getText().toString());
                    jsonObject.addProperty("otp", otp);
                    ApiClient.getClient().verifyEmailotp(jsonObject).enqueue(new Callback<VerifiyOtpModel>() {
                        @Override
                        public void onResponse(Call<VerifiyOtpModel> call, Response<VerifiyOtpModel> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {

                                if (response.body().getStatus() == true) {
                                    isEmailVerified = true;
                                    dialog.dismiss();
                                    email.setEnabled(false);
                                    tv_isEmailVrfd.setVisibility(View.GONE);
                                    img_verifyEmail.setVisibility(View.VISIBLE);
                                    Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    isEmailVerified = false;
                                    Toast.makeText(SignUp.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            } else {

                            }

                        }

                        @Override
                        public void onFailure(Call<VerifiyOtpModel> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }

            }
        });


        dialog.show();
    }

    @SuppressLint("ResourceAsColor")
    private void validatePassword(String charSequence) {

        if (!SupportValidation.passwordValidation_2(charSequence)) {

            password_validater.setVisibility(View.VISIBLE);

        } else {
            password_validater.setVisibility(View.GONE);


        }
    }

    @SuppressLint("ResourceAsColor")
    private void validateRePassword(String charSequence) {

        if (!SupportValidation.passwordValidation_2(charSequence)) {

            re_password_validater.setVisibility(View.VISIBLE);

        } else {
            re_password_validater.setVisibility(View.GONE);


        }
    }

    @SuppressLint("ResourceAsColor")
    private void validateEmail(String charSequence) {

        if (SupportValidation.emailvalidation(charSequence)) {
            email_validater.setVisibility(View.GONE);
            tv_isEmailVrfd.setVisibility(View.VISIBLE);

        } else {
            tv_isEmailVrfd.setVisibility(View.GONE);
            email_validater.setVisibility(View.VISIBLE);
        }
    }


    public void validation() {
        String name_1 = name.getText().toString();
        String surname_1 = surname.getText().toString();
        String mobile_1 = mobile.getText().toString();
        String email_1 = email.getText().toString();
        String permanent_1 = permanent.getText().toString();
        String present_1 = present.getText().toString();
        String password_1 = password.getText().toString();
        String repassword_1 = repassword.getText().toString();
        if (password_1.equals(repassword_1)) {
            if (name_1.length() < 3) {
                name.setFocusable(true);
                name.setError("Invalid name");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                name.startAnimation(shake);


            } else if (surname_1.length() < 2) {
                surname.setFocusable(true);
                surname.setError("User name can't be empty");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                surname.startAnimation(shake);

            } else if (email_1.isEmpty()) {
                email.setFocusable(true);
                email.setError("Email  can't be empty");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                email.startAnimation(shake);

            } else if (!SupportValidation.emailvalidation(email_1)) {
                email.setFocusable(true);
                email.setError("Invalid email address");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                email.startAnimation(shake);

            } else if (isEmailVerified == false) {
                email.setFocusable(true);
                Toast.makeText(SignUp.this, "Your email is not verified verify your email.", Toast.LENGTH_LONG).show();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                email.startAnimation(shake);

            } else if (mobile_1.isEmpty() || !SupportValidation.mobileValidation(mobile_1)) {
                mobile.setFocusable(true);
                mobile.setError("Mobile number can't be empty");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                mobile.startAnimation(shake);

            } else if (!SupportValidation.mobileValidation(mobile_1)) {
                mobile.setFocusable(true);
                mobile.setError("Invalid mobile number");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                mobile.startAnimation(shake);

            } else if (permanent_1.isEmpty()) {
                permanent.setFocusable(true);
                permanent.setError("Permanent address can't be empty");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                permanent.startAnimation(shake);

            } else if (present_1.isEmpty()) {
                present.setFocusable(true);
                present.setError("Permanent address can't be empty");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                present.startAnimation(shake);

            } else if (password_1.isEmpty()) {
                password.setFocusable(true);
                password.setError("Password can't be empty");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                password.startAnimation(shake);

            } else if (repassword_1.isEmpty()) {
                repassword.setFocusable(true);
                repassword.setError("Password  can't be empty");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                repassword.startAnimation(shake);

            } else if (repassword_1.length() < 8) {
                repassword.setFocusable(true);
                repassword.setError("Password  can't be smaller then 8");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                repassword.startAnimation(shake);

            } else if (!SupportValidation.passwordValidation_2(password_1)) {
                repassword.setFocusable(true);
                repassword.setError("Invalid password");
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                repassword.startAnimation(shake);

            } else {
                progressDialog.setMessage("Signing....");

                progressDialog.show();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("firstName", name_1);
                jsonObject.addProperty("lastName", surname_1);
                jsonObject.addProperty("email", email_1);
                jsonObject.addProperty("phone", mobile_1);
                jsonObject.addProperty("currentAdrs", present_1);
                jsonObject.addProperty("prmntAddress", permanent_1);
                jsonObject.addProperty("password", password_1);
                jsonObject.addProperty("device_token", new AppSession(SignUp.this).getToken());
                ApiClient.getClient().registerUser(jsonObject).enqueue(new Callback<SignUpModel>() {
                    @Override
                    public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            SignUpModel signUpModel = response.body();
                            Toast.makeText(getApplicationContext(), signUpModel.getMessage(), Toast.LENGTH_LONG).show();


                            if (signUpModel.getStatus() == true) {
                                SignUpData signUpData = signUpModel.getData();
                                if (signUpData != null) {
                                    onBackPressed();
                                }

                            } else {

                            }
                        }
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<SignUpModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                    }
                });
            }

        } else {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "password not matched", Toast.LENGTH_LONG);
        }
    }

    private void passwordMatchedOrNot() {
        if (passwordCK.equals(repasswordCK)) {
            password_matched.setVisibility(View.GONE);
        } else {
            password_matched.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Signing in....");

        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                return;
                            } else {
                                new AppSession(SignUp.this).setToken(task.getResult());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        name = (EditText) findViewById(R.id.ed_name);
        img_back = (AppCompatImageView) findViewById(R.id.img_back);
        img_verifyEmail = (AppCompatImageView) findViewById(R.id.img_verifyEmail);
        surname = (EditText) findViewById(R.id.rd_surname);
        mobile = (EditText) findViewById(R.id.ed_mobile);
        password = (EditText) findViewById(R.id.ed_password);
        repassword = (EditText) findViewById(R.id.ed_re_passord);
        email = (EditText) findViewById(R.id.ed_email);
        re_password_validater = (TextView) findViewById(R.id.re_password_validater);
        password_matched = (TextView) findViewById(R.id.password_matched);
        tv_isEmailVrfd = (TextView) findViewById(R.id.tv_isEmailVrfd);
        password_validater = (TextView) findViewById(R.id.password_validater);
        email_validater = (TextView) findViewById(R.id.email_validater);
        permanent = (EditText) findViewById(R.id.ed_present);
        checkbox_pass = (CheckBox) findViewById(R.id.checkbox_pass);
        checkbox_pass_re = (CheckBox) findViewById(R.id.checkbox_pass_re);
        present = (EditText) findViewById(R.id.ed_permanent);
        submit = (Button) findViewById(R.id.submit);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            name.clearFocus();
            surname.clearFocus();
            email.clearFocus();
            mobile.clearFocus();
            permanent.clearFocus();
            present.clearFocus();
            password.clearFocus();
            repassword.clearFocus();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
}
