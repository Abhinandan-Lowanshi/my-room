package com.example.myroom.app;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.customlatModel.CustomeLatLon;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.new_ui_adapter.Recomended_Room_Adapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import appsession.AppSession;

public class startScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    AppSession appSession;
    LocationManager manager;
    Timer task1,task2;
    boolean connected = false;
    TextView progressText;
    private boolean permissioncheck =false;
    Double longitude_1,lattitude_1;
//    ProgressDialog progressDialog;
    ProgressBar progressBar3;
    int progress =0;
    List<Address> addresses = null;
    Boolean location_check = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        appSession = new AppSession(startScreen.this);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            Toast.makeText(startScreen.this, "No internet connection", Toast.LENGTH_LONG);
            connected = false;
        }

        Log.d("TAG", "network: "+connected);
        setContentView(R.layout.activity_start_screen);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(startScreen.this);
        appSession.setMainlat("0");
        appSession.setMainlon("0");
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);

        progressText = (TextView) findViewById(R.id.progressText);
        progressText.setText("0%");
        task1 = new Timer();
        task2 = new Timer();


        try {
            // if(appSession.getMainlat().equals("0") || appSession.getMainlon().equals("0"))
            if(appSession.getIsCustomeLocation()==true&&appSession.getCityCurrent()!="")
            {
//                CustomeLatLon customeLatLon = appSession.getCustomLatLon();
//                appSession.setMainlat(String.valueOf(customeLatLon.getLat()));
//                appSession.setMainlon(String.valueOf(customeLatLon.getLon()));
                if(appSession.getIsLogin()==null) {
                    Intent intent = new Intent(getApplicationContext(), LoginFinal.class);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                    startActivity(intent);
                    finish();
                }
                else {
                    if(appSession.getIsLogin().equalsIgnoreCase("1")) {
                        task1.cancel();
                        task1.cancel();
//                        appSession.setSignalStrenth(String.valueOf(location.getAccuracy()));
                        Intent i = new Intent(getApplicationContext(), NewHomeActivityFR.class);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), LoginFinal.class);
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        startActivity(intent);
                        finish();

                    }
                }

            }else {
                checkRunTimePermission();
            }
        } catch (Exception e) {

            Log.d("TAG", "Abhiiiiiii: " +e.getLocalizedMessage());
            Toast.makeText(startScreen.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }





    }
    public void checkRunTimePermission() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(startScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(startScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
            Log.d("TAG", "checkRunTimePermission: "+e.getLocalizedMessage());
        }

            }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(startScreen.this);
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


            if (ActivityCompat.checkSelfPermission(startScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(startScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //  return;


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

                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("TAG", "onComplete: " + e.getLocalizedMessage());
                        }
                        try {
                            Location location = task.getResult();
                            if (location==null)
                            {
                                return;
                            }
                            Geocoder geocoder = new Geocoder(startScreen.this.getApplicationContext(),
                                    Locale.getDefault());
                            addresses = null;
                            addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );

                            longitude_1 = addresses.get(0).getLongitude();
                            lattitude_1 = addresses.get(0).getLatitude();
                             if(lattitude_1!=null&&longitude_1!=null)
                             {
                                 task1.cancel();
                                 location_check = true;
                                 appSession.setMainlat(String.valueOf(lattitude_1));
                                 appSession.setMainlon(String.valueOf(longitude_1));
                                 if(appSession.getIsLogin()==null) {
                                    Intent intent = new Intent(getApplicationContext(), LoginFinal.class);
                                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    if(appSession.getIsLogin().equalsIgnoreCase("1")) {
                                        task1.cancel();
                                        task1.cancel();
                                        appSession.setSignalStrenth(String.valueOf(location.getAccuracy()));
                                        Intent i = new Intent(getApplicationContext(), NewHomeActivityFR.class);
                                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                                        startActivity(i);
                                        finish();
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(), LoginFinal.class);
                                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                                        startActivity(intent);
                                        finish();

                                    }
                                }

                             }
//                            address =addresses.get(0).getSubLocality()+", "+ addresses.get(0).getLocality() + ", " + addresses.get(0).getPostalCode() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                          address =addresses.get(0).getSubLocality()+", "+ addresses.get(0).getPostalCode() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                            //  getNearByDriver();", "+addresses.get(0).getSubAdminArea()

                             if(!address.isEmpty())
                                 appSession.setCityCurrent(address);


//                            tv_city.setText(address);

//                    float checkStatus = (float) 7.0;
                        } catch (Exception e) {
//                            task1.cancel();
                            Log.d("TAG", "Abhi :::::::::::::: ex "+ e.getLocalizedMessage());
//                            Log.d(TAG, "Error: "+e.getLocalizedMessage());
//                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
//                        if (toast_check == true) {
//                            toast_check = false;
//                            if (location.getAccuracy() < 8.00) {
//                                single_1.setAlpha(1f);
//                                single_2.setAlpha(1f);
//                                single_3.setAlpha(1f);
//                                single_4.setAlpha(1f);
//                                single_5.setAlpha(1f);
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level less than 8m", Toast.LENGTH_LONG).show();
//
//                            } else if (location.getAccuracy() < 20.00) {
//                                single_1.setAlpha(1f);
//                                single_2.setAlpha(1f);
//                                single_3.setAlpha(1f);
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 10m", Toast.LENGTH_LONG).show();
//
//                                single_4.setAlpha(1f);
//                                single_5.setAlpha(.2f);
//
//                            } else if (location.getAccuracy() < 40.0) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 20m", Toast.LENGTH_LONG).show();
//
//                                single_1.setAlpha(1f);
//                                single_2.setAlpha(1f);
//                                single_3.setAlpha(1f);
//                                single_4.setAlpha(.2f);
//                                single_5.setAlpha(.2f);
//
//                            } else if (location.getAccuracy() < 60.00) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 50m", Toast.LENGTH_LONG).show();
//
//                                single_1.setAlpha(1f);
//                                single_2.setAlpha(1f);
//                                single_3.setAlpha(.2f);
//                                single_4.setAlpha(.2f);
//                                single_5.setAlpha(.2f);
//                            } else if (location.getAccuracy() < 800.00) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 500m", Toast.LENGTH_LONG).show();
//
//                                single_1.setAlpha(1f);
//                                single_2.setAlpha(.2f);
//                                single_3.setAlpha(.2f);
//                                single_4.setAlpha(.2f);
//                                single_5.setAlpha(.2f);
//                            } else {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level more then 1200m", Toast.LENGTH_LONG).show();
//                                single_1.setAlpha(.2f);
//                                single_2.setAlpha(.2f);
//                                single_3.setAlpha(.2f);
//                                single_4.setAlpha(.2f);
//                                single_5.setAlpha(.2f);
//                            }
//                        }
//




                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.d("TAG", "Fail 255552: " + e.getMessage());
                }
            });

        }catch (Exception e)
        {
//            Log.d(TAG, "getcurrentlocation: "+e.getLocalizedMessage());

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
                    if (ContextCompat.checkSelfPermission(startScreen.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(startScreen.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        if (permissioncheck == true) {

                            triggerRebirth(startScreen.this);
//                            getcurrentlocation();
                        }
                    }
                } else {
                    Toast.makeText(startScreen.this, "Location access Denied", Toast.LENGTH_SHORT).show();

                }
                return;
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          try {
              Log.d("TAG", "onActivityResult: ");
              checkRunTimePermission();
          }catch (Exception e)
          {
               e.printStackTrace();
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
}