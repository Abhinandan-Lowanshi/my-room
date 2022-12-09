package com.myroom.myroom.app.new_ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myroom.myroom.R;
import com.myroom.myroom.app.map.DrawMarker;
import com.myroom.myroom.app.map.DrawRouteMaps;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import appsession.AppSession;

public class
TakeLocationByGoogleMap extends AppCompatActivity implements OnMapReadyCallback, LocationListener ,GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener {

    private static final String TAG ="Abhi:::" ;
    private GoogleMap mMap;
    private LatLng origin;
    AppCompatImageView img_back;
    private  double lat,lon;
    private TextView start_tracking;
    Timer task_is;
    AppCompatImageView img_notification;
    TextView accuracy;
    Button save_location;
    AppSession appSession;
    double longitude_1 = 0.0,lattitute_1=0.0;
    Boolean location_check = false;
    LocationManager manager;
    View single_1,single_2,single_3,single_4,single_5;
    Boolean toast_check = true;
    private  float  strength;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    // Store LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER information
    private String provider_info;
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS Tracking is enabled
    boolean isGPSTrackingEnabled = false;
    Location location;
    double latitude;
    double longitude;
    ProgressDialog progressDialog;
    FusedLocationProviderClient fusedLocationProviderClient;
    private boolean clickOnOk =false;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_location_by_google_map);
        initView();
        //  startTracking(this);
        manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if (manager != null) {
            List<String> providers = manager.getAllProviders();
            for (String provider : providers) {
                manager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }
        }
      fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkRunTimePermission();
//        start_tracking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // startTracking(MapDetailsActivity.this);
//                }
//        });
        save_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               appSession.setlat(String.valueOf(lattitute_1));
               appSession.setlon(String.valueOf(longitude_1));
               onBackPressed();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TakeLocationByGoogleMap.this, Notification_Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent returnIntent = new Intent();
//        setResult(Activity.RESULT_OK,returnIntent);
//        finish();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }

     void showDialogue()
     {
         final Dialog dialog = new Dialog(TakeLocationByGoogleMap.this);
         dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
         dialog.setCancelable(true);
         dialog.setContentView(R.layout.addressdialogue);
         dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
         dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
         //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
         dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                 WindowManager.LayoutParams.MATCH_PARENT);
         AppCompatImageView img_close = dialog.findViewById(R.id.img_close);
         CardView card_Close = dialog.findViewById(R.id.card_Close);
         card_Close.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dialog.dismiss();
             }
         });  img_close.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             dialog.dismiss();
         }
     });



         dialog.show();
     };
    private void initView() {
        showDialogue();
        lat = getIntent().getDoubleExtra("lat",0.000);
        lon = getIntent().getDoubleExtra("lon",0.0000);
        origin = new LatLng(lat, lon);
        img_notification = (AppCompatImageView) findViewById(R.id.img_notification);

        start_tracking = (TextView) findViewById(R.id.start_tracking);
        single_1 = (View)findViewById(R.id.single_1);
        single_2 = (View)findViewById(R.id.single_2);
        single_3 = (View)findViewById(R.id.single_3);
        single_4 = (View)findViewById(R.id.single_4);
        single_5 = (View)findViewById(R.id.single_5);
        appSession = new AppSession(TakeLocationByGoogleMap.this);
        appSession.setlat("");
        appSession.setlon("");
        save_location  = (Button) findViewById(R.id.save_location);
        accuracy = (TextView) findViewById(R.id.accuracy);
        img_back = (AppCompatImageView) findViewById(R.id.img_back);
//        if(appSession.getMainlat()!="0")
//        {
//            latitude = Double.parseDouble(appSession.getMainlat());
//            longitude = Double.parseDouble(appSession.getMainlon());
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//            mapFragment.getMapAsync(TakeLocationByGoogleMap.this::onMapReady);
//
//            if(appSession.getSignalStrenth()!="0") {
//                strength = Float.parseFloat(appSession.getSignalStrenth());
//                accuracy.setText("Accuracy:- "+String.valueOf(strength)+"");
//                toast_check =false;
//                if (strength < 8.00) {
//                    single_1.setAlpha(1f);
//                    single_2.setAlpha(1f);
//                    single_3.setAlpha(1f);
//                    single_4.setAlpha(1f);
//                    single_5.setAlpha(1f);
////                    Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
//
//                } else if (strength < 20.00) {
//                    single_1.setAlpha(1f);
//                    single_2.setAlpha(1f);
//                    single_3.setAlpha(1f);
////                    Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
//
//                    single_4.setAlpha(1f);
//                    single_5.setAlpha(.2f);
//
//                } else if (strength < 40.0) {
////                    Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
//
//                    single_1.setAlpha(1f);
//                    single_2.setAlpha(1f);
//                    single_3.setAlpha(1f);
//                    single_4.setAlpha(.2f);
//                    single_5.setAlpha(.2f);
//
//                } else if (strength < 60.00) {
////                    Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
//
//                    single_1.setAlpha(1f);
//                    single_2.setAlpha(1f);
//                    single_3.setAlpha(.2f);
//                    single_4.setAlpha(.2f);
//                    single_5.setAlpha(.2f);
//                } else if (strength < 800.00) {
////                    Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
//
//                    single_1.setAlpha(1f);
//                    single_2.setAlpha(.2f);
//                    single_3.setAlpha(.2f);
//                    single_4.setAlpha(.2f);
//                    single_5.setAlpha(.2f);
//                } else {
////                    Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
//                    single_1.setAlpha(.2f);
//                    single_2.setAlpha(.2f);
//                    single_3.setAlpha(.2f);
//                    single_4.setAlpha(.2f);
//                    single_5.setAlpha(.2f);
//                }
//            }
//        }
        progressDialog = new ProgressDialog(TakeLocationByGoogleMap.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Preparing map please wait....");
        progressDialog.show();

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.addMarker(new MarkerOptions().position(origin).title("name"));

         mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lattitute_1, longitude_1), 18.0f));
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(lattitute_1, longitude_1));
        circleOptions.radius(60);
        circleOptions.fillColor(Color.TRANSPARENT);
        circleOptions.strokeWidth(3);
        circleOptions.getCenter();
        circleOptions.strokeColor(Color.GREEN);
        mMap.addCircle(circleOptions);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

    }
    private void buildAlertMessageNoGps() {

        final Dialog dialog = new Dialog(TakeLocationByGoogleMap.this);
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
              onBackPressed();
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
                onBackPressed();
            }
        });
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 0) {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (provider != null) {
                Log.v("TAG", " Location providers: " + provider);

                 checkRunTimePermission();
//                resetApplication();
                //  getLocation(this);
                // startActivity(Intent.makeRestartActivityTask(getIntent().getComponent()));

                //   onRestart();
                //Start searching for location and update the location text when update available.
// Do whatever you want
                // startFetchingLocation();
            } else {
                //Users did not switch on the GPS
            }
        }

    }
    public void resetApplication() {
        Intent resetApplicationIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        if (resetApplicationIntent != null) {
            resetApplicationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(resetApplicationIntent);
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onLocationChanged(Location location) {
//        if(location!=null) {
//            LatLng data = new LatLng(location.getLatitude(),location.getLongitude());
//            mMap.clear();
//            mMap.addMarker(new MarkerOptions().position(data).title("name"));
//            Toast.makeText(getApplicationContext(),"Locatoin updated",Toast.LENGTH_LONG).show();
//            Log.d(TAG, "Update: Location"+location.getLatitude()+"  "+location.getLongitude());
//        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    public void getLocation(android.location.LocationListener locationListener) {

        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            //getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            // Try to get location if you GPS Service is enabled
            if (isGPSEnabled) {
                this.isGPSTrackingEnabled = true;

                Log.d(TAG, "Application use GPS Service");

                /*
                 * This provider determines location using
                 * satellites. Depending on conditions, this provider may take a while to return
                 * a location fix.
                 */

                provider_info = LocationManager.GPS_PROVIDER;

            } else if (isNetworkEnabled) { // Try to get location if you Network Service is enabled
                this.isGPSTrackingEnabled = true;

                Log.d(TAG, "Application use Network State to get GPS coordinates");

                /*
                 * This provider determines location based on
                 * availability of cell tower and WiFi access points. Results are retrieved
                 * by means of a network lookup.
                 */
                provider_info = LocationManager.NETWORK_PROVIDER;

            }

            // Application can use GPS or Network Provider
            if (!provider_info.isEmpty()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(
                        provider_info,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        locationListener);

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider_info);

                    Log.d(TAG, "getAccurecy: "+location.getAccuracy());
                    updateGPSCoordinates();
                }
            }
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            Log.e("TAG", "Impossible to connect to LocationManager", e);
        }
    }
    public void updateGPSCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Geocoder geocoder = new Geocoder(TakeLocationByGoogleMap.this,
                    Locale.getDefault());
            //Inilize address list
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1
                );
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG", "onComplete: ");
            }

            // address = addresses.get(0).getLocality()+", "+addresses.get(0).getPostalCode()+", "+addresses.get(0).getSubAdminArea()+", "+addresses.get(0).getAdminArea();
            //  getNearByDriver();", "+addresses.get(0).getSubAdminArea()

//
//                    locationArrayList.add(Brisbane);

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(TakeLocationByGoogleMap .this::onMapReady);
        }
    }
    public void checkRunTimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //  getcurrentlocation();
                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                }else {

                    long delay = 1000;
                    long period = 1000;
                    task_is = new Timer();
                    task_is.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if (Looper.myLooper()==null)
                                Looper.prepare();
                            if(location_check==false) {
                                Log.d(TAG, "Abhi::::: "+"  start");
                                getcurrentlocation();
                                //getLocation(Home_Acitvity_New.this);

                            } else if(location_check==true)
                            {
                                task_is.cancel();
                                progressDialog.dismiss();
                                Log.d(TAG, "Abhi::::: "+"  stop");
                            }
                        }
                    }, delay, period);

                    // getLocation(this);
                }

            } else {
                requestPermissions(new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        10);
                // Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            }
        } else {

        }
    }
    private void locationPermission() {

        final Dialog dialog = new Dialog(TakeLocationByGoogleMap.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.locationpermissiondialogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        AppCompatImageView img_close = dialog.findViewById(R.id.img_close);
        AppCompatTextView message = dialog.findViewById(R.id.message);
        CardView close = dialog.findViewById(R.id.card_Close);
        CardView card_OpenSettings = dialog.findViewById(R.id.card_OpenSettings);
        message.setText("Need location permission to find your address");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();
            }
        });

        card_OpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                clickOnOk = true;
                startInstalledAppDetailsActivity(TakeLocationByGoogleMap.this);
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();
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
    @Override
    protected void onResume() {
        super.onResume();
        if(clickOnOk) {
            clickOnOk = false;
            checkRunTimePermission();
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(TakeLocationByGoogleMap.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    locationPermission();
                    Toast.makeText(this, "Permission Denied enable permission to save address", Toast.LENGTH_SHORT).show();
                    // requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    //  10);
                }
                return;
            }
            case 100: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(TakeLocationByGoogleMap.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //  Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 102: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0]);
                    Toast.makeText(getApplicationContext(), "done ", Toast.LENGTH_LONG).show();
                    //resume tasks needing this permission
                }
            }
        }
    }
    public void startTracking(android.location.LocationListener locationListener)
    {
        long delay = 1000;
        long period = 1000;
        Timer task = new Timer();
        task.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getLocationOnInterval(locationListener);
            }
        }, delay, period);
    }
    public void getLocationOnInterval(android.location.LocationListener locationListener)
    {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            //getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            // Try to get location if you GPS Service is enabled
            if (isGPSEnabled) {
                this.isGPSTrackingEnabled = true;

                Log.d(TAG, "Application use GPS Service");

                /*
                 * This provider determines location using
                 * satellites. Depending on conditions, this provider may take a while to return
                 * a location fix.
                 */

                provider_info = LocationManager.GPS_PROVIDER;

            } else if (isNetworkEnabled) { // Try to get location if you Network Service is enabled
                this.isGPSTrackingEnabled = true;

                Log.d(TAG, "Application use Network State to get GPS coordinates");

                /*
                 * This provider determines location based on
                 * availability of cell tower and WiFi access points. Results are retrieved
                 * by means of a network lookup.
                 */
                provider_info = LocationManager.NETWORK_PROVIDER;

            }

            // Application can use GPS or Network Provider
            if (!provider_info.isEmpty()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(
                        provider_info,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        locationListener);

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider_info);
                    if(location!=null) {
                        LatLng data = new LatLng(location.getLatitude(),location.getLongitude());

                        mMap.addMarker(new MarkerOptions().position(data).title("name"));
                        Toast.makeText(getApplicationContext(),"Location updated",Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Update: Location"+location.getLatitude()+"  "+location.getLongitude());
                    }

                    LatLng origin = new LatLng(lat, lon);
                    LatLng destination = new LatLng(latitude, longitude);
                    //  LatLng destination = new LatLng(23.2599, 77.4126);
                    DrawRouteMaps.getInstance(this,0,this)
                            .draw(origin, destination, mMap);
                    DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.location_origon, "Origin Location");
                    DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.location_destinition, "Destination Location");
                    LatLngBounds bounds = new LatLngBounds.Builder()
                            .include(origin)
                            .build();
                    Point displaySize = new Point();
                    getWindowManager().getDefaultDisplay().getSize(displaySize);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 100, 20));

//                    Log.d(TAG, "getAccurecy: "+location.getAccuracy());
//                    updateGPSCoordinates();
                }
            }
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            Log.e("TAG", "Impossible to connect to LocationManager", e);
        }
    }

    private void getcurrentlocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //  return;


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission_group.LOCATION},10);
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


                    //Initialize location
                    location = task.getResult();
                    if (location != null) {


                        //Initilize geocoder
                        Geocoder geocoder = new Geocoder(TakeLocationByGoogleMap.this,
                                Locale.getDefault());
                        //Inilize address list
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            longitude_1 = addresses.get(0).getLongitude();
                            lattitute_1 = addresses.get(0).getLatitude();
                            if (lattitute_1 != 0.0 && longitude_1 != 0.0) {
                                appSession.setMainlat(String.valueOf(lattitute_1));
                                appSession.setMainlon(String.valueOf(longitude_1));}
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("TAG", "onComplete: ");
                        }
                    }
                }catch (Exception e)
                {
                    task_is.cancel();

                    Toast.makeText(TakeLocationByGoogleMap.this, "We get an error pls try again later", Toast.LENGTH_SHORT).show();
                }
                    //
//                    LatLng sydney = new LatLng(22.7196, 75.8577);
//                    LatLng TamWorth = new LatLng(22.7533, 75.8937);
//                    LatLng NewCastle = new LatLng(22.7196, 75.8577);
                    //LatLng Brisbane = new LatLng(23.2599, 77.4126);

                    // creating array list for adding all our locations.


                    // on below line we are adding our
                    // locations in our array list.
//                    locationArrayList.add(sydney);
//                    locationArrayList.add(TamWorth);
//                    locationArrayList.add(NewCastle);
//                    locationArrayList.add(Brisbane);



                    //  address = addresses.get(0).getLocality()+", "+addresses.get(0).getPostalCode()+", "+addresses.get(0).getSubAdminArea()+", "+addresses.get(0).getAdminArea();
                    //  getNearByDriver();", "+addresses.get(0).getSubAdminArea()
                    location_check=true;
                    progressDialog.dismiss();
                    task_is.cancel();
                  //  start_tracking.setText("");
                    if(toast_check==true) {
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                        mapFragment.getMapAsync(TakeLocationByGoogleMap.this::onMapReady);
                    }
                    if(toast_check==true) {
                        accuracy.setText("Accuracy:- "+String.valueOf(location.getAccuracy())+"");
                        toast_check =false;
                        if (location.getAccuracy() < 8.00) {
                            single_1.setAlpha(1f);
                            single_2.setAlpha(1f);
                            single_3.setAlpha(1f);
                            single_4.setAlpha(1f);
                            single_5.setAlpha(1f);
//                            Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();

                        } else if (location.getAccuracy() < 20.00) {
                            single_1.setAlpha(1f);
                            single_2.setAlpha(1f);
                            single_3.setAlpha(1f);
//                            Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();

                            single_4.setAlpha(1f);
                            single_5.setAlpha(.2f);

                        } else if (location.getAccuracy() < 40.0) {
//                            Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();

                            single_1.setAlpha(1f);
                            single_2.setAlpha(1f);
                            single_3.setAlpha(1f);
                            single_4.setAlpha(.2f);
                            single_5.setAlpha(.2f);

                        } else if (location.getAccuracy() < 60.00) {
//                            Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();

                            single_1.setAlpha(1f);
                            single_2.setAlpha(1f);
                            single_3.setAlpha(.2f);
                            single_4.setAlpha(.2f);
                            single_5.setAlpha(.2f);
                        } else if (location.getAccuracy() < 800.00) {
//                            Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();

                            single_1.setAlpha(1f);
                            single_2.setAlpha(.2f);
                            single_3.setAlpha(.2f);
                            single_4.setAlpha(.2f);
                            single_5.setAlpha(.2f);
                        } else {
//                            Toast.makeText(getApplicationContext(), "GPS single detected, Accuracy level "+String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
                            single_1.setAlpha(.2f);
                            single_2.setAlpha(.2f);
                            single_3.setAlpha(.2f);
                            single_4.setAlpha(.2f);
                            single_5.setAlpha(.2f);
                        }
                    }
                    //  tv_city.setText(address);
//
//                    locationArrayList.add(Brisbane);
                    //Log.d("TAG", "Abhi:::::: "+lattitude_1+"   "+longitude_1);




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("TAG", "Fail 255552: "+e.getMessage());
            }
        });


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("name"));
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        Toast.makeText(getApplicationContext(),"Your house",Toast.LENGTH_LONG).show();



    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;
        Toast.makeText(getApplicationContext(),"onMarkerDragStart "+lattitute_1+"  "+longitude_1,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;
        Toast.makeText(getApplicationContext(),"onMarkerDrag "+lattitute_1+"  "+longitude_1,Toast.LENGTH_LONG).show();


    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        lattitute_1 = marker.getPosition().latitude;
        longitude_1 = marker.getPosition().longitude;

        Toast.makeText(getApplicationContext(),"onMarkerDragEnd "+lattitute_1+"  "+longitude_1,Toast.LENGTH_LONG).show();

    }

}