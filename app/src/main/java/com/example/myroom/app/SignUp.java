package com.example.myroom.app;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.loginmanage.ManageSession;
import com.example.myroom.app.retrofit.ApiClient;
import com.example.myroom.app.signup.SignUpData;
import com.example.myroom.app.signup.SignUpModel;
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
   private FirebaseAuth mAuth;
   AppSession appSession;
   private  String firebaseToken , passwordCK = "", repasswordCK="";
   AppCompatImageView img_back;
   private TextView password_validater ,email_validater,re_password_validater,password_matched;
   private EditText name,surname,email,mobile,password,repassword,present,permanent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        appSession = new AppSession(SignUp.this);
           initView();


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
        });  repassword.addTextChangedListener(new TextWatcher() {
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

    @SuppressLint("ResourceAsColor")
    private void validatePassword(String charSequence) {

        if(!SupportValidation.passwordValidation_2(charSequence))
        {

            password_validater.setVisibility(View.VISIBLE);

        }else {
            password_validater.setVisibility(View.GONE);


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
  @SuppressLint("ResourceAsColor")
    private void validateEmail(String charSequence) {

        if(SupportValidation.emailvalidation(charSequence))
        {
            email_validater.setVisibility(View.GONE);

        }else {

            email_validater.setVisibility(View.VISIBLE);
        }
    }


    public void validation()
    {

//        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
        String name_1 = name.getText().toString();
        String surname_1 = surname.getText().toString();
        String mobile_1 = mobile.getText().toString();
        String email_1 = email.getText().toString();
        String permanent_1 = permanent.getText().toString();
        String present_1 = present.getText().toString();
        String password_1 = password.getText().toString();
        String repassword_1 = repassword.getText().toString();
        if(password_1.equals(repassword_1))
        {
            if(name_1.length()<3)
            {
                name.setFocusable(true);
                name.setError("Invalid name");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                name.startAnimation(shake);


            }
            else if(surname_1.length()<2)
            {
                surname.setFocusable(true);
                surname.setError("User name can't be empty");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                surname.startAnimation(shake);

            }
            else if(email_1.isEmpty())
            {
                email.setFocusable(true);
                email.setError("Email  can't be empty");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                email.startAnimation(shake);

            }  else if(!SupportValidation.emailvalidation(email_1))
            {
                email.setFocusable(true);
                email.setError("Invalid email address");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                email.startAnimation(shake);

            }
            else if(mobile_1.isEmpty()||!SupportValidation.mobileValidation(mobile_1))
            {
                mobile.setFocusable(true);
                mobile.setError("Mobile number can't be empty");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                mobile.startAnimation(shake);

            }  else if(!SupportValidation.mobileValidation(mobile_1))
            {
                mobile.setFocusable(true);
                mobile.setError("Invalid mobile number");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                mobile.startAnimation(shake);

            }
            else if(permanent_1.isEmpty())
            {
                permanent.setFocusable(true);
                permanent.setError("Permanent address can't be empty");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                permanent.startAnimation(shake);

            } else if(present_1.isEmpty())
            {
                present.setFocusable(true);
                present.setError("Permanent address can't be empty");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                present.startAnimation(shake);

            }
            else if(password_1.isEmpty())
            {
                password.setFocusable(true);
                password.setError("Password can't be empty");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                password.startAnimation(shake);

            }
            else if(repassword_1.isEmpty())
            {
                repassword.setFocusable(true);
                repassword.setError("Password  can't be empty");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                repassword.startAnimation(shake);

          } else if(repassword_1.length()<8)
            {
                repassword.setFocusable(true);
                repassword.setError("Password  can't be smaller then 8");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                repassword.startAnimation(shake);

          }
            else if(!SupportValidation.passwordValidation_2(password_1))
            {
                repassword.setFocusable(true);
                repassword.setError("Invalid password");
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                Animation shake = AnimationUtils.loadAnimation(SignUp.this, R.anim.shake);
                repassword.startAnimation(shake);

            }
            else {
                progressDialog.show();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("firstName",name_1);
                jsonObject.addProperty("lastName",surname_1);
                jsonObject.addProperty("email",email_1);
                jsonObject.addProperty("phone",mobile_1);
                jsonObject.addProperty("currentAdrs",present_1);
                jsonObject.addProperty("prmntAddress",permanent_1);
                jsonObject.addProperty("password",password_1);
                jsonObject.addProperty("device_token",new AppSession(SignUp.this).getToken());
                ApiClient.getClient().registerUser(jsonObject).enqueue(new Callback<SignUpModel>() {
                    @Override
                    public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                        if(response.isSuccessful())
                        {
                            progressDialog.dismiss();
                            SignUpModel signUpModel = response.body();
                            Toast.makeText(getApplicationContext(),signUpModel.getMessage(),Toast.LENGTH_LONG).show();


                             if(signUpModel.getStatus()==true)
                             {
                                  SignUpData signUpData = signUpModel.getData();
//                                 Toast.makeText(getApplicationContext(),signUpData.getUsrFirstName(),Toast.LENGTH_LONG).show();

                                  if(signUpData!=null) {

                                      ManageSession.Login(SignUp.this,String.valueOf(signUpData.getUsrId()),signUpData.getUsrFirstName()
                                              ,signUpData.getUsrLastName(),signUpData.getUsrEmail(),signUpData.getUsrPhone(),
                                              signUpData.getUsrParmentAdrss(),signUpData.getUsrCurrentAdrss());
//                                      appSession.setIsLogin("1");
////                                      appSession.setCurrentPassword(password.getText().toString());
//                                      appSession.setFname(signUpData.getUsrFirstName());
//                                      appSession.setLname(signUpData.getUsrLastName());
//                                      appSession.setEmail(signUpData.getUsrEmail());
//                                      appSession.setMobile(signUpData.getUsrPhone());
//                                      appSession.setUserID(signUpData.getUsrId());
//                                      appSession.setPresentAddress(signUpData.getUsrCurrentAdrss());
//                                      appSession.setPermanentAddress(signUpData.getUsrParmentAdrss());
                                      Intent intent = new Intent(SignUp.this, NewHomeActivityFR.class);
                                      startActivity(intent);
                                      finish();
                                  }

                             }
                             else {
//                                 Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                             }
                        }
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<SignUpModel> call, Throwable t) {
                        Log.d("TAG", "555555555"+t.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();

                    }
                });
            }

           // {"firstName":"vishak","lastName":"vishak","email":"vishalvishalthakur1999@gmail.com","phone":"4545654565454","currentAdrs":"GVGHDJ FHBJHF ","prmntAddress":"FHJFHJK","password":"vishal@123"}

        }
        else
        { progressDialog.dismiss();
          Toast.makeText(getApplicationContext(),"password not matched",Toast.LENGTH_LONG);
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
    private void initView()
                    {
                        mAuth = FirebaseAuth.getInstance();
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
                                        }else {
                                            new AppSession(SignUp.this).setToken(task.getResult());
                                            // Get new FCM registration token


                                        }
                                     }
                                });
                  }catch (Exception e)
                  {
                      e.printStackTrace();
                  }


                        firebaseToken = new AppSession(SignUp.this).getToken();
                        name = (EditText)findViewById(R.id.ed_name);
                        img_back = (AppCompatImageView) findViewById(R.id.img_back);
                        surname = (EditText)findViewById(R.id.rd_surname);
                        mobile = (EditText)findViewById(R.id.ed_mobile);
                        password = (EditText)findViewById(R.id.ed_password);
                        repassword= (EditText)findViewById(R.id.ed_re_passord);
                        email = (EditText)findViewById(R.id.ed_email);
                        re_password_validater = (TextView)findViewById(R.id.re_password_validater);
                        password_matched = (TextView)findViewById(R.id.password_matched);
                        password_validater = (TextView)findViewById(R.id.password_validater);
                        email_validater = (TextView)findViewById(R.id.email_validater);
                        permanent = (EditText)findViewById(R.id.ed_present);
                        present = (EditText)findViewById(R.id.ed_permanent);
                        submit = (Button)findViewById(R.id.submit);
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
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
}


//
//       if(name_1.isEmpty()|| !SupportValidation.emailvalidation(email_1)||!SupportValidation.mobileValidation(mobile_1)||!SupportValidation.passwordValidation_2(password_1)|| surname_1.isEmpty() || mobile_1.isEmpty() || email_1.isEmpty()|| permanent_1.isEmpty() || present_1.isEmpty() || password_1.isEmpty() || repassword_1.isEmpty() == true)
//               {
//               Toast.makeText(getApplicationContext(), "check  all the field ", Toast.LENGTH_LONG).show();
//               if(SupportValidation.mobileValidation(mobile_1)==false)
//               mobile.setError("Invalid mobile no");
//               if(SupportValidation.emailvalidation(email_1)==false)
//               email.setError("Invalid email");
//               if (SupportValidation.passwordValidation_2(password_1)==false)
//               password.setError("Enter case sensitive password like....Exam@55662 and password must have more then 7 characters");
//
//
//
//               }else {
//               progressDialog.show();
//               submit.setText("Working on....");
//               submit.setEnabled(false);
//               mAuth.createUserWithEmailAndPassword(email_1,password_1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//@Override
//public void onComplete(@NonNull Task<AuthResult> task) {
//        if(task.isSuccessful())
//
//        {
//        setContentView(R.layout.loading_screen);
//        String uid = mAuth.getUid();
//        SignInClass ob = new SignInClass();
//        ob.setName(name_1);
//        ob.setSurname(surname_1);
//        ob.setEmail(email_1);
//        ob.setMobile(mobile_1);
//        ob.setPermanentadd(permanent_1);
//        ob.setPresentadd(present_1);
//        databaseReference.child(uid).setValue(ob).addOnCompleteListener(new OnCompleteListener<Void>() {
//@Override
//public void onComplete(@NonNull Task<Void> task) {
//        if(task.isSuccessful())
//        {
//
//        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//@Override
//public void onComplete(@NonNull Task<Void> task) {
//        if(task.isSuccessful())
//        {       progressDialog.dismiss();
//        Intent intent = new Intent(getApplicationContext(), LogIn_1.class);
//        startActivity(intent);
//        finishAffinity();
//        Toast.makeText(getApplicationContext(), "Verification link has been send", Toast.LENGTH_LONG).show();
//        }
//        else {
//        progressDialog.dismiss();
//        Toast.makeText(getApplicationContext(), "Error"+task.getException().toString(), Toast.LENGTH_LONG).show();
//
//        }
//        }
//        });
//
//
//        finish();
//
//
//
//
//
//
//        }
//        else {
//        progressDialog.dismiss();
//        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
//        submit.setText("Submit");
//        submit.setEnabled(true);
//        setContentView(R.layout.activity_sign_up);
//
//
//        }
//
//        }
//        });
//        }
//        else
//        {
//
//        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
//        progressDialog.dismiss();
//        submit.setText("Submit");
//        submit.setEnabled(true);
//        setContentView(R.layout.activity_sign_up);
//
//        }
//
//
//        }
//        });
//
//        }

