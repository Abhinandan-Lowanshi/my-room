package com.example.myroom.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private FirebaseAuth mAuth;
    private AppSession appSession;
    private LocationManager manager;
    private Timer task1, task2;
    boolean connected = false;
    private TextView progressText;
    private boolean permissioncheck = false;
    private Double longitude_1, lattitude_1;
    private boolean clickOnOk = false;
    private List<Address> addresses = null;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        appSession = new AppSession(startScreen.this);

//

        setContentView(R.layout.activity_start_screen);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(startScreen.this);


        try {
            if (appSession.getIsCustomeLocation() == true && appSession.getCityCurrent() != "") {
                if (appSession.getIsLogin() == null) {
                    Intent intent = new Intent(getApplicationContext(), LoginFinal.class);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
//                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
//            connected = true;
//        }
//        else {
//            Toast.makeText(startScreen.this, "No internet connection", Toast.LENGTH_LONG);
//            connected = false;
//        }
                    startActivity(intent);
                    finish();
                } else {
                    if (appSession.getIsLogin().equalsIgnoreCase("1")) {
                        Intent i = new Intent(getApplicationContext(), NewHomeActivityFR.class);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), LoginFinal.class);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                        startActivity(intent);
                        finish();

                    }
                }

            } else {
                checkRunTimePermission();
            }
        } catch (Exception e) {

            Log.d("TAG", "Abhiiiiiii: " + e.getLocalizedMessage());
            Toast.makeText(startScreen.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }


    }

    public void checkRunTimePermission() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(startScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(startScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                 getcurrentlocation();
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        buildAlertMessageNoGps();
                    } else {

                        getcurrentlocation();

                    }

                } else {
                    permissioncheck = true;
//                progressDialog.dismiss();
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                            10);
                    // Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    // Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                }
            } else {
                Log.d("TAG", "checkRunTimePermission: ");

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "checkRunTimePermission: " + e.getLocalizedMessage());
        }

    }

    private void buildAlertMessageNoGps() {

        final Dialog dialog = new Dialog(startScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.gpsdisbaledialogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        AppCompatImageView img_close = dialog.findViewById(R.id.img_close);
        CardView close = dialog.findViewById(R.id.card_Close);
        CardView card_OpenSettings = dialog.findViewById(R.id.card_OpenSettings);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                System.exit(1);
            }
        });

        card_OpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 200);
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                System.exit(1);
            }
        });
        dialog.show();
    }

    private void locationPermission() {

        final Dialog dialog = new Dialog(startScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.locationpermissiondialogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        AppCompatImageView img_close = dialog.findViewById(R.id.img_close);
        CardView close = dialog.findViewById(R.id.card_Close);
        CardView card_OpenSettings = dialog.findViewById(R.id.card_OpenSettings);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
              System.exit(1);
            }
        });

        card_OpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                clickOnOk = true;
                startInstalledAppDetailsActivity(startScreen.this);
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                System.exit(1);
            }
        });
        dialog.show();
    }
    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
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
                        if (location == null) {
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
                        if (lattitude_1 != null && longitude_1 != null) {
                            appSession.setMainlat(String.valueOf(lattitude_1));
                            appSession.setMainlon(String.valueOf(longitude_1));
                            if (appSession.getIsLogin() == null) {
                                Intent intent = new Intent(getApplicationContext(), LoginFinal.class);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                startActivity(intent);
                                finish();
                            } else {
                                if (appSession.getIsLogin().equalsIgnoreCase("1")) {
                                    appSession.setSignalStrenth(String.valueOf(location.getAccuracy()));
                                    Intent i = new Intent(getApplicationContext(), NewHomeActivityFR.class);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), LoginFinal.class);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                    startActivity(intent);
                                    finish();

                                }
                            }

                        }
                        address = addresses.get(0).getSubLocality() + ", " + addresses.get(0).getPostalCode() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                        if (!address.isEmpty())
                            appSession.setCityCurrent(address);
                    } catch (Exception e) {
                        buildAlertMessageNoGps();
                        Log.d("TAG", "Abhi :::::::::::::: ex " + e.getLocalizedMessage());
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.d("TAG", "Fail 255552: " + e.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("TAG", "onRequestPermissionsResult: " + requestCode);
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(startScreen.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(startScreen.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        checkRunTimePermission();
                        if (permissioncheck == true) {

                            triggerRebirth(startScreen.this);
                        }
                    }
                } else {
                    locationPermission();
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
        } catch (Exception e) {
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

    @Override
    protected void onResume() {
        super.onResume();
        if(clickOnOk) {
            clickOnOk = false;
            checkRunTimePermission();
        }
    }
}