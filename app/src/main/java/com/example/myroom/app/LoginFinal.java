package com.example.myroom.app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.login.LoginData;
import com.example.myroom.app.login.LoginModel;
import com.example.myroom.app.loginmanage.ManageSession;
import com.example.myroom.app.new_ui.ForgotPassword;
import com.example.myroom.app.pushnotification.FirebaseTokenGetter;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFinal extends AppCompatActivity {
    private TextInputEditText email,password;
    private FirebaseAuth mAuth;
    private ConstraintLayout constraintLayout;
    private Button login;
    AppSession appSession;
    String f_email="";
    String f_password ="";
    private float heightInch;
    private ProgressDialog progressDialog;
    private TextView emailverification,signup;
    LinearLayout passlayout;
    private String firebasetoken;
    private  ActivityResultLauncher<String> mPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float mWidthPixels = dm.widthPixels;
        float  mHeightPixels = dm.heightPixels;
        float widthInch = mWidthPixels/dm.density;
        heightInch = mHeightPixels/dm.density;
        run();



    }
    private void run()
    {
        if(650<heightInch)
            setContentView(R.layout.activity_log_in_1);
        else
            setContentView(R.layout.login_small);


        mPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // TODO: Inform user that that your app will not show notifications.
            }
        });


        constraintLayout = (ConstraintLayout)findViewById(R.id.contraintLayout);



            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                return;
                            }else {
                                new AppSession(LoginFinal.this).setToken(task.getResult());
                                // Get new FCM registration token


                            }

                            // Log and toast

                        }
                    });
            firebasetoken = new AppSession(LoginFinal.this).getToken();


        emailverification = (TextView) findViewById(R.id.resendEmaillink);
        email = (TextInputEditText) findViewById(R.id.email);
        appSession = new AppSession(LoginFinal.this);
        passlayout = (LinearLayout) findViewById(R.id.passlayout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Logging in...");
        password = (TextInputEditText) findViewById(R.id.password);
        signup = (TextView) findViewById(R.id.signup);
        login = (Button)findViewById(R.id.login);
        login.setEnabled(false);
        login.setAlpha(.4f);
        @SuppressLint("ResourceType") final Animation myAnim = AnimationUtils.loadAnimation(this, R.drawable.animation);

          email.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              }

              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  f_email = charSequence.toString();
                    loginButton(f_email,f_password);
              }

              @Override
              public void afterTextChanged(Editable editable) {

              }
          });
          password.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              }

              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  f_password = charSequence.toString();
                  loginButton(f_email,f_password);

              }

              @Override
              public void afterTextChanged(Editable editable) {

              }
          });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setEnabled(false);

                Toast.makeText(getApplicationContext(),"Wait...",Toast.LENGTH_LONG).show();

                Intent i  = new Intent(LoginFinal.this, SignUp.class);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

                startActivity(i);

                signup.setEnabled(true);


            }

        });
        passlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginFinal.this, ForgotPassword.class);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                startActivity(intent);





            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
//                    Intent intent = new Intent(LoginFinal.this, NewHomeActivityFR.class);
//                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
//                    startActivity(intent);
//                    finish();

                    if (email.getText().toString().isEmpty()) {
                        email.setFocusable(true);
                        email.setError("Email can't be empty");
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        Animation shake = AnimationUtils.loadAnimation(LoginFinal.this, R.anim.shake);
                        email.startAnimation(shake);

                    } else if (password.getText().toString().isEmpty()) {
                        password.setFocusable(true);
                        password.setError("Password can't be empty");
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        Animation shake = AnimationUtils.loadAnimation(LoginFinal.this, R.anim.shake);
                        password.startAnimation(shake);
                    }
                    else {


                        progressDialog.show();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("email", email.getText().toString());
                        jsonObject.addProperty("password", password.getText().toString());
                        jsonObject.addProperty("device_token", new AppSession(LoginFinal.this).getToken());

                        ApiClient.getClient().login(jsonObject).enqueue(new Callback<LoginModel>() {
                            @Override
                            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                                progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    LoginModel loginModel = response.body();
                                    if (loginModel.getStatus() == true) {
                                        LoginData loginData = loginModel.getData();

                                        ManageSession.Login(LoginFinal.this,String.valueOf(loginData.getUsrId()),loginData.getUsrFirstName()
                                        ,loginData.getUsrLastName(),loginData.getUsrEmail(),loginData.getUsrPhone(),
                                                loginData.getUsrParmentAdrss(),loginData.getUsrCurrentAdrss());
                                        Intent intent = new Intent(LoginFinal.this, NewHomeActivityFR.class);
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginFinal.this, loginModel.getMessage()
                                                , Toast.LENGTH_SHORT).show();

                                    }

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginFinal.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<LoginModel> call, Throwable t) {
//                                Toast.makeText(LoginFinal.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });

                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    Animation shake = AnimationUtils.loadAnimation(LoginFinal.this, R.anim.shake);
                    email.startAnimation(shake);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    Animation shake11 = AnimationUtils.loadAnimation(LoginFinal.this, R.anim.shake);
                    password.startAnimation(shake11);
                }




            }
        });




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        email.clearFocus();
        password.clearFocus();


        return true;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {



        if(email.getText().toString().isEmpty()&&password.getText().toString().isEmpty()) {
            new AlertDialog.Builder(this).setCancelable(false)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(1);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }
        else {
            email.setText("");
            password.setText("");
            email.clearFocus();
            password.clearFocus();
        }

    }
    public void loginButton(String email,String password)
    {
         if(AppSession.isValidEmail(email)&& password.length()>5&&email.length()>1)
         {
              login.setEnabled(true);
             login.setAlpha(1f);
         }else {
             login.setEnabled(false);
             login.setAlpha(.4f);
         }
    }

//    private void askNotificationPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                PackageManager.PERMISSION_GRANTED) {
//            // FCM SDK (and your app) can post notifications.
//        } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//            // TODO: display an educational UI explaining to the user the features that will be enabled
//            //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//            //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//            //       If the user selects "No thanks," allow the user to continue without notifications.
//        } else {
//            // Directly ask for the permission
//            mPermission.launch(Manifest.permission.POST_NOTIFICATIONS);
//        }
//    }
}