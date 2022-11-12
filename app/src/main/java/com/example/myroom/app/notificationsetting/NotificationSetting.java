package com.example.myroom.app.notificationsetting;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.LoginFinal;
import com.example.myroom.app.customlatModel.CustomeLatLon;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.new_ui.Notification_Activity;
import com.example.myroom.app.new_ui.Search_Activity;
import com.example.myroom.app.pushnotification.Notification.UpdateNotificationModel;
import com.example.myroom.app.retrofit.ApiClient;
import com.example.myroom.app.startScreen;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSetting extends AppCompatActivity {
    private Switch switch_notification  ,switch_notification_custom;
    private TextView  ed_location , customlocationer;
    private AppSession appsession;
    private Geocoder geocoder;
    private  AppCompatImageView img_back;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 100;
    private double delivery_latitude;
    private double delivery_longitude;
    LocationManager manager;
    private boolean permissioncheck;
    Double longitude_1,lattitude_1;
    private ProgressDialog progressDialog;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean location_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting2);
        initView();
        ed_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(NotificationSetting.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        switch_notification_custom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {

                       if(b)
                       {
                           customlocationer.setVisibility(View.GONE);
                           switch_notification_custom.setText("ON");
                           ed_location.setText("tap to set location");
                           ed_location.setVisibility(View.VISIBLE);

                       }else {
                           new AppSession(NotificationSetting.this).setIsCustomeLocation(b);
                           checkRunTimePermission();
                           ed_location.setVisibility(View.GONE);

                       }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                try {
                    updateNotificationStatus(b,"","");

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

    public  void updateNotificationStatus(boolean status , String lat , String lon )
    {

         try {
             if(appsession.getUserID()!=null&&appsession.getUserID()!="")
             {
                 JsonObject jsonObject = new JsonObject();
                 jsonObject.addProperty("user_id",appsession.getUserID());
//                 jsonObject.addProperty("usr_latitude",lat);
//                 jsonObject.addProperty("usr_longitude",lon);
                 jsonObject.addProperty("isNotify" , status);
                 ApiClient.getClient().updateUserNotificationDetails(jsonObject).enqueue(new Callback<UpdateNotificationModel>() {
                     @Override
                     public void onResponse(Call<UpdateNotificationModel> call, Response<UpdateNotificationModel> response) {

                         if(response.isSuccessful())
                         {
                             UpdateNotificationModel  notificationModel = response.body();
                             if(notificationModel.getStatus()==true)
                             {
                                 if(status)
                                 {

                                    appsession.setNotificationStatus("true");
                                     switch_notification.setText("ON");
                                     switch_notification.setChecked(true);
                                 }else
                                 {
                                     appsession.setNotificationStatus("false");
                                     switch_notification.setText("OFF");
                                     switch_notification.setChecked(false);
                                 }

                                 Toast.makeText(NotificationSetting.this,"Successfully changes applied",Toast.LENGTH_LONG);
                             }else  Toast.makeText(NotificationSetting.this,"Successfully changes applied",Toast.LENGTH_LONG);

                         }

                     }

                     @Override
                     public void onFailure(Call<UpdateNotificationModel> call, Throwable t) {

                     }
                 });

             }

         }catch (Exception e)
         {
              e.printStackTrace();
         }


    }
    private void getNotificationStatus() {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", String.valueOf(new AppSession(NotificationSetting.this).getUserID()));
            ApiClient.getClient().getUserNotification(jsonObject).enqueue(new Callback<NotificationStatusModel>() {
                @Override
                public void onResponse(Call<NotificationStatusModel> call, Response<NotificationStatusModel> response) {

                    if(response.isSuccessful())
                    {
                        if(response.body().getStatus()==true)
                        {
                            if(response.body().getData().getIsNotify().equalsIgnoreCase("1"))
                                new AppSession(NotificationSetting.this).setNotificationStatus("true");
                            else if (response.body().getData().getIsNotify().equalsIgnoreCase("0"))
                                new AppSession(NotificationSetting.this).setNotificationStatus("false");
                        }
                    }


                }

                @Override
                public void onFailure(Call<NotificationStatusModel> call, Throwable t) {
                    Log.d("TAG", "onFailure: ");
                }
            });

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void checkRunTimePermission() {
        progressDialog.show();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(NotificationSetting.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(NotificationSetting.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                 getcurrentlocation();
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                        buildAlertMessageNoGps();
                    }else {

                        getcurrentlocation();

                    }

                } else {
                    permissioncheck=true;
//                progressDialog.dismiss();
                    requestPermissions(new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            10);
                    // Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    // Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                }
            } else {
                Log.d("TAG", "checkRunTimePermission: ");

            }

        }catch (Exception e)
        {
            e.printStackTrace();
            progressDialog.dismiss();
            Log.d("TAG", "checkRunTimePermission: "+e.getLocalizedMessage());
        }

    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(NotificationSetting.this);
        builder.setTitle("Your GPS seems to be disabled ?").setMessage("Turn on GPS without GPS we can't provide our services.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent,200);
                        //  startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),200);
//
                        // getLocation(locationListener);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    private void getcurrentlocation() {

        try {


            if (ActivityCompat.checkSelfPermission(NotificationSetting.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NotificationSetting.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission_group.LOCATION}, 10);
                }
            }


            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
                @Override
                public boolean isCancellationRequested() {
                    return false;
                }

                @NonNull
                @NotNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull @NotNull OnTokenCanceledListener onTokenCanceledListener) {
                    return null;
                }
            }).addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(Task<Location> task) {
                    //Initialize location
                    Location location = task.getResult();

                    if (location != null) {

                        List<Address> addresses = null;
                        try {
                            Geocoder geocoder = new Geocoder(NotificationSetting.this.getApplicationContext(),
                                    Locale.getDefault());
                            //Inilize address list
                            addresses = null;
                            addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("TAG", "onComplete: " + e.getLocalizedMessage());
                        }
                        //Initilize geocoder

                        try {


                            longitude_1 = addresses.get(0).getLongitude();
                            lattitude_1 = addresses.get(0).getLatitude();
                            if(lattitude_1!=null&&longitude_1!=null)
                            {

                                location_check = true;
                                appsession.setMainlat(String.valueOf(lattitude_1));
                                appsession.setMainlon(String.valueOf(longitude_1));


                            }

                            String address = addresses.get(0).getLocality() + ", " + addresses.get(0).getPostalCode() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                            //  getNearByDriver();", "+addresses.get(0).getSubAdminArea()

                            if(!address.isEmpty())
                                appsession.setCityCurrent(address);

                             ed_location.setText(address);
                             switch_notification_custom.setText("OFF");
                             progressDialog.dismiss();

//                            tv_city.setText(address);

//                    float checkStatus = (float) 7.0;
                        } catch (Exception e) {
//                            task1.cancel();
                            Log.d("TAG", "Abhi :::::::::::::: ex "+ e.getLocalizedMessage());
//                            Log.d(TAG, "Error: "+e.getLocalizedMessage());
//                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
//

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.d("TAG", "Fail 255552: " + e.getMessage());
                }
            });

        }catch (Exception e)
        {

        }
    }
    private void initView() {
//        getNotificationStatus() ;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(NotificationSetting.this);
        appsession= new AppSession(NotificationSetting.this);
        progressDialog = new ProgressDialog(NotificationSetting.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Getting location");
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        img_back =(AppCompatImageView) findViewById(R.id.img_back);
        geocoder = new Geocoder(this, Locale.getDefault());
        Places.initialize(NotificationSetting.this, "AIzaSyD8HnhMQpIt9ZGaPnkexNlGomWHOYerTVc");
        switch_notification = (Switch) findViewById(R.id.switch_notification);
        switch_notification_custom = (Switch) findViewById(R.id.switch_notification_custom);
        customlocationer = (TextView) findViewById(R.id.customlocationer);
        ed_location = (TextView) findViewById(R.id.ed_location);
        String nf_st ;



         try {

             String city = new AppSession(NotificationSetting.this).getCityCurrent();
              if(city!="")
              {
                   ed_location.setText(city);
              }

             if(appsession.getIsCustomeLocation()==true)
             {
                 switch_notification_custom.setChecked(true);
                 switch_notification_custom.setText("ON");
                 ed_location.setVisibility(View.VISIBLE);
             }
             else {

                 switch_notification_custom.setChecked(false);
                 switch_notification_custom.setText("OFF");
                 ed_location.setVisibility(View.GONE);

             }
         }catch (Exception e)
         {

         }

        nf_st = appsession.getNotificationStatus();
        try {
            if (nf_st.equalsIgnoreCase("true")) {
                switch_notification.setText("ON");

                switch_notification.setChecked(true);
            } else {
                switch_notification.setText("OFF");


                switch_notification.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("TAG", "onRequestPermissionsResult: "+requestCode);
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(NotificationSetting.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(NotificationSetting.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        if (permissioncheck == true) {

                            triggerRebirth(NotificationSetting.this);
//                            getcurrentlocation();
                        }
                    }
                } else {
                    Toast.makeText(NotificationSetting.this, "Location access Denied", Toast.LENGTH_SHORT).show();

                }
                return;
            }


        }
    }
    private void triggerRebirth(Context context) {

        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                try {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    String name =  place.getAddress();
                    List<String> tt = place.getAttributions();
                    ed_location.setText(name);
                    delivery_latitude = place.getLatLng().latitude;
                    delivery_longitude = place.getLatLng().longitude;
                    new AppSession(NotificationSetting.this).setIsCustomeLocation(true);
                    appsession.setMainlat(String.valueOf(delivery_latitude));
                    appsession.setMainlon(String.valueOf(delivery_longitude));
                    appsession.setCityCurrent(name);
                    Log.d("TAG", "appsession.setMainlat gggg: " +appsession.getMainlon()+"  "+appsession.getMainlat());

                } catch (Exception e) {
                    e.printStackTrace();


                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void setLatLonToSerer() {
    }

    private void setOpacity(boolean status)
    {

        switch_notification_custom.setEnabled(status);
        ed_location.setEnabled(status);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
    }
}