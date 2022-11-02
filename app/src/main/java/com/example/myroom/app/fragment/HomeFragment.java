package com.example.myroom.app.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myroom.R;
import com.example.myroom.app.LoginFinal;
import com.example.myroom.app.banner_pkg.BannerAdapter;
import com.example.myroom.app.demo.Helper;
import com.example.myroom.app.demo.RoomData;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.home.RoomDetailsModel;
import com.example.myroom.app.map.DrawMarker;
import com.example.myroom.app.new_ui.All_Roon_Activity;
import com.example.myroom.app.new_ui.Room_Detais_Activity;
import com.example.myroom.app.new_ui_adapter.NearByRoom_Adapter;
import com.example.myroom.app.new_ui_adapter.Recomended_Room_Adapter;
import com.example.myroom.app.new_ui_adapter.Room_type_adapter;
import com.example.myroom.app.retrofit.ApiClient;
import com.example.myroom.app.serializable.DataSerializable;
import com.example.myroom.app.startScreen;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements BannerAdapter.ClicktPost, LocationListener {
    private static final String TAG = "Abhi:::";
    RecyclerView rce_room_type,rec_recomended,rec_nearbyroom;
    LinearLayoutManager linearLayoutManager;
    private ActionBarDrawerToggle toggle;
    public DrawerLayout drawerLayout;
    private Toolbar toolbar;
    ViewPager vpHomeFirstBanner;
    AppSession appSession;
    Helper helper;
    private int currentPage = 0;
    private LinearLayout ll_signal;
    private Handler handler;
    private Timer timer;
    private boolean permissioncheck =false;
    private long DELAY_MS = 500;
    private long PERIOD_MS = 5000;
    private CardView cardposter;
    View single_1,single_2,single_3,single_4,single_5;
    Boolean toast_check = true;
    String address;
    ProgressDialog progressDialog;
    List<Address> addresses = null;
    Boolean location_check = false;
    ArrayList<RoomData> roomData = new ArrayList<>();
    ArrayList<RoomDetailsData> roomDetailsData = new ArrayList<>();
    BannerAdapter bannerAdapter;
    private  GoogleMap googleMap;
    private NavigationView nevigation_view;
    Boolean  i =false;
    LatLng origin;
    private  boolean isUserScroll = false;
    private int currentItems =0 , totalItems =0 , scrolledItems =0;
    RelativeLayout rl_nearbylocation;
    ArrayList<LatLng> data =new ArrayList<>();
    private GoogleMap mMap;
    private TextView noRoomFind;
    Double longitude_1,lattitude_1;
    RelativeLayout rl_loction;
    FusedLocationProviderClient fusedLocationProviderClient;
    WormDotsIndicator dotsIndicator;
    TextView header_Email,Fname,tv_city;
    AppCompatTextView tv_more_nearby;
    LocationManager manager;
    Timer task1;
    int colorHandler = 0;
     boolean checkLocation = false;


    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

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
    ProgressBar progressBar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView checkData;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        String lat = appSession.getMainlon();
        Log.d(TAG, "appSession: "+lat);
        if(appSession.getMainlat()=="0") {
            checkRunTimePermission();
        }

     rec_nearbyroom.addOnScrollListener(new RecyclerView.OnScrollListener() {
         @Override
         public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
             super.onScrollStateChanged(recyclerView, newState);
             if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
             {
                 isUserScroll = true;

             }
         }

//         @Override
//         public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//             super.onScrolled(recyclerView, dx, dy);
//             currentItems = linearLayoutManager.getChildCount();
//             totalItems = linearLayoutManager.getItemCount();
//             scrolledItems = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//             if(isUserScroll&&(scrolledItems ==2))
//             {
//                 Log.d(TAG, "endScrolll");
//             }
//
//         }
     });
        scrooling(3);
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());



        mSwipeRefreshLayout = (SwipeRefreshLayout)view. findViewById(R.id.container);
        mSwipeRefreshLayout.setColorScheme(R.color.red,
                R.color.card_green, R.color.quantum_orange, R.color.purple_200);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadHome();
            }
        });
        loadHome();
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.dr_lay);



        final ScrollView scroll = (ScrollView) view.findViewById(R.id.scroll);
        ImageView transparent = (ImageView) view.findViewById(R.id.imagetrans);

        transparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scroll.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scroll.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scroll.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });



        rl_nearbylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        tv_more_nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lat = appSession.getMainlat();
                String log = appSession.getMainlon();
                 if(appSession.getMainlat()!="0"&&appSession.getMainlat()!="0") {
                     Intent intent = new Intent(getActivity(), All_Roon_Activity.class);
                     intent.putExtra("lat", appSession.getMainlat());
                     intent.putExtra("lon", appSession.getMainlon());
                     getActivity().startActivity(intent);
                 }
            }
        });

        return view;
    }

    private void loadHome() {

                        if(appSession.getCityCurrent()!="0")
                            tv_city.setText(appSession.getCityCurrent());

                        if(appSession.getIsCustomeLocation()==false)
                        {
                            ll_signal.setVisibility(View.VISIBLE);
                            float strnth = Float.parseFloat(appSession.getSignalStrenth());
                            if (strnth < 8.00) {
                                single_1.setAlpha(1f);
                                single_2.setAlpha(1f);
                                single_3.setAlpha(1f);
                                single_4.setAlpha(1f);
                                single_5.setAlpha(1f);
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level less than 8m", Toast.LENGTH_LONG).show();

                            } else if (strnth < 20.00) {
                                single_1.setAlpha(1f);
                                single_2.setAlpha(1f);
                                single_3.setAlpha(1f);
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 10m", Toast.LENGTH_LONG).show();

                                single_4.setAlpha(1f);
                                single_5.setAlpha(.2f);

                            } else if (strnth < 40.0) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 20m", Toast.LENGTH_LONG).show();

                                single_1.setAlpha(1f);
                                single_2.setAlpha(1f);
                                single_3.setAlpha(1f);
                                single_4.setAlpha(.2f);
                                single_5.setAlpha(.2f);

                            } else if (strnth < 60.00) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 50m", Toast.LENGTH_LONG).show();

                                single_1.setAlpha(1f);
                                single_2.setAlpha(1f);
                                single_3.setAlpha(.2f);
                                single_4.setAlpha(.2f);
                                single_5.setAlpha(.2f);
                            } else if (strnth < 800.00) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 500m", Toast.LENGTH_LONG).show();

                                single_1.setAlpha(1f);
                                single_2.setAlpha(.2f);
                                single_3.setAlpha(.2f);
                                single_4.setAlpha(.2f);
                                single_5.setAlpha(.2f);
                            } else {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level more then 1200m", Toast.LENGTH_LONG).show();
                                single_1.setAlpha(.2f);
                                single_2.setAlpha(.2f);
                                single_3.setAlpha(.2f);
                                single_4.setAlpha(.2f);
                                single_5.setAlpha(.2f);
                            }
                        }else {
                            ll_signal.setVisibility(View.GONE);
                        }

                        getNearbyRoom(this);



                    }









    @Override
    public void clickPostListner(int pos) {

    }










    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                        if(permissioncheck==true) {

                            triggerRebirth(getActivity().getApplicationContext());
//                            getcurrentlocation();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();

                }
                return;
            }
            case 100: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        if(permissioncheck==true) {
                            getcurrentlocation();
                        }
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //  Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 102:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0]);
                    Toast.makeText(getContext(),"done ",Toast.LENGTH_LONG).show();
                    //resume tasks needing this permission
                }
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
    public void onLocationChanged(Location location) {

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




    public void resetApplication() {
        Intent resetApplicationIntent = getContext().getPackageManager().getLaunchIntentForPackage(getActivity().getPackageName());
        if (resetApplicationIntent != null) {
            resetApplicationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(resetApplicationIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private void initView(View view) {
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.container);
        rec_nearbyroom = (RecyclerView)view.findViewById(R.id.rec_nearbyroom);
        cardposter = (CardView) view.findViewById(R.id.cardposter);
//        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL, true);
//        rec_nearbyroom.setLayoutManager(linearLayoutManager);
        ll_signal = (LinearLayout) view.findViewById(R.id.ll_signal);
        rce_room_type = (RecyclerView)view.findViewById(R.id.rce_room_type);
        rec_recomended = (RecyclerView)view.findViewById(R.id.rec_recomended);
        nevigation_view = (NavigationView)view.findViewById(R.id.nav_view);
        dotsIndicator = (WormDotsIndicator)view.findViewById(R.id.dots_indicator);
        tv_more_nearby = (AppCompatTextView) view.findViewById(R.id.tv_more_nearby);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        noRoomFind = (TextView) view.findViewById(R.id.noRoomFind);
        rl_loction = (RelativeLayout) view.findViewById(R.id.rl_loction);
        rl_nearbylocation = (RelativeLayout)view. findViewById(R.id.rl_nearbylocation);
        vpHomeFirstBanner = (ViewPager)view.findViewById(R.id.banner);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        single_1 = (View)view.findViewById(R.id.single_1);
        single_2 = (View)view.findViewById(R.id.single_2);
        single_3 = (View)view.findViewById(R.id.single_3);
        single_4 = (View)view.findViewById(R.id.single_4);
        single_5 = (View)view.findViewById(R.id.single_5);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading home.....");
//       progressDialog.show();

        long delay = 1400;
        long period = 1000;
        task1 = new Timer();


//try {
//
//    task1.scheduleAtFixedRate(new TimerTask() {
//        @Override
//        public void run() {
//            if (Looper.myLooper()==null)
//                Looper.prepare();
//
//            if(location_check==true){
//                getActivity().runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        task1.cancel();
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });
//
//            }
//            progressBar
//                    .getIndeterminateDrawable()
//                    .setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
////                    if(colorHandler==0){
////                        colorHandler++;
////
////                    }else if(colorHandler==1)
////                    {
////                        colorHandler++;
////                        progressBar
////                                .getIndeterminateDrawable()
////                                .setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
////                    }else if(colorHandler==2)
////                    {
////                        colorHandler++;
////                        progressBar
////                                .getIndeterminateDrawable()
////                                .setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
////                    }else if(colorHandler==3)
////                    {
////                        colorHandler++;
////                        ((ProgressBar)view.findViewById(R.id.progressBar))
////                                .getIndeterminateDrawable()
////                                .setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
////                    }else if(colorHandler==4)
////                    {
////                        colorHandler = 0;
////                        progressBar
////                                .getIndeterminateDrawable()
////                                .setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
////                    }
//        }
//    }, delay, period);
//
//}catch (Exception e)
//{
//     e.printStackTrace();
//}




        appSession =  new AppSession(getActivity());
        ChipNavigationBar chipNavigationBar;
        chipNavigationBar = (ChipNavigationBar) getActivity().findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.home,true);

        helper = new Helper(1);
        Room_type_adapter room_type_adapter = new Room_type_adapter(getContext());
        rce_room_type.setAdapter(room_type_adapter);
        rce_room_type.scheduleLayoutAnimation();


//        Recomended_Room_Adapter recomended_Room_Adapter = new Recomended_Room_Adapter(getContext(),getActivity());
//        rec_recomended.setAdapter(recomended_Room_Adapter);
//        rec_recomended.scheduleLayoutAnimation();

    }
    private void buildAlertMessageNoGps(android.location.LocationListener locationListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Your GPS seems to be disabled, do you want to enable it?").setMessage("without turing on GPS service you can't use nearby room search")
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
    public void updateGPSCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Geocoder geocoder = new Geocoder(getContext(),
                    Locale.getDefault());
            //Inilize address list
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1
                );
            } catch (IOException e) {
                e.printStackTrace();
                //  Log.d("TAG", "onComplete: ");
            }

            address = addresses.get(0).getLocality()+", "+addresses.get(0).getPostalCode()+", "+addresses.get(0).getSubAdminArea()+", "+addresses.get(0).getAdminArea();
            //  getNearByDriver();", "+addresses.get(0).getSubAdminArea()
            tv_city.setText(address);
            location_check=true;
            Log.d(TAG, "Lat_lon: "+addresses.get(0).getLatitude()+"   "+addresses.get(0).getLongitude());
            rl_loction.setVisibility(View.VISIBLE);

        }
    }


    public void checkRunTimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //  getcurrentlocation();
                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps(this);
                }else {
                     getcurrentlocation();
                }

            } else {
                permissioncheck=true;
                progressDialog.dismiss();
                requestPermissions(new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        10);
                // Manifest.permission.ACCESS_BACKGROUND_LOCATION
                // Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            }
        } else {
            progressDialog.dismiss();
        }
    }


    private void getcurrentlocation() {

        try {


            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    //Initialize location
                    Location location = task.getResult();

                    if (location != null) {

                        try {
                            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext().getApplicationContext(),
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

                                appSession.setMainlat(String.valueOf(lattitude_1));
                                appSession.setMainlon(String.valueOf(longitude_1));
                                address =addresses.get(0).getSubLocality()+", "+ addresses.get(0).getPostalCode() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                                 tv_city.setText(address);
                                 appSession.setCityCurrent(address);
                              loadHome();

                            }



                            if (location.getAccuracy() < 8.00) {
                                single_1.setAlpha(1f);
                                single_2.setAlpha(1f);
                                single_3.setAlpha(1f);
                                single_4.setAlpha(1f);
                                single_5.setAlpha(1f);
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level less than 8m", Toast.LENGTH_LONG).show();

                            } else if (location.getAccuracy() < 20.00) {
                                single_1.setAlpha(1f);
                                single_2.setAlpha(1f);
                                single_3.setAlpha(1f);
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 10m", Toast.LENGTH_LONG).show();

                                single_4.setAlpha(1f);
                                single_5.setAlpha(.2f);

                            } else if (location.getAccuracy() < 40.0) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 20m", Toast.LENGTH_LONG).show();

                                single_1.setAlpha(1f);
                                single_2.setAlpha(1f);
                                single_3.setAlpha(1f);
                                single_4.setAlpha(.2f);
                                single_5.setAlpha(.2f);

                            } else if (location.getAccuracy() < 60.00) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 50m", Toast.LENGTH_LONG).show();

                                single_1.setAlpha(1f);
                                single_2.setAlpha(1f);
                                single_3.setAlpha(.2f);
                                single_4.setAlpha(.2f);
                                single_5.setAlpha(.2f);
                            } else if (location.getAccuracy() < 800.00) {
//                                Toast.makeText(getContext(), "GPS single detected, Accuracy level 500m", Toast.LENGTH_LONG).show();

                                single_1.setAlpha(1f);
                                single_2.setAlpha(.2f);
                                single_3.setAlpha(.2f);
                                single_4.setAlpha(.2f);
                                single_5.setAlpha(.2f);
                            } else {
                              //  Toast.makeText(getContext(), "GPS single detected, Accuracy level more then 1200m", Toast.LENGTH_LONG).show();
                                single_1.setAlpha(.2f);
                                single_2.setAlpha(.2f);
                                single_3.setAlpha(.2f);
                                single_4.setAlpha(.2f);
                                single_5.setAlpha(.2f);
                            }



                            address = addresses.get(0).getLocality() + ", " + addresses.get(0).getPostalCode() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                            //  getNearByDriver();", "+addresses.get(0).getSubAdminArea()

                            if(!address.isEmpty())
                                appSession.setCityCurrent(address);

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
    private void show_Dialogue() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.search_dialogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        AppCompatEditText search  = dialog.findViewById(R.id.ed_search);
        Spinner size  = dialog.findViewById(R.id.sp_Size);
        Spinner price  = dialog.findViewById(R.id.sp_price);
        Spinner rating  = dialog.findViewById(R.id.sp_Rating);
        Button submit  = dialog.findViewById(R.id.submit);
        LinearLayout rl_top  = dialog.findViewById(R.id.ll_top2);
        // View line  = dialog.findViewById(R.id.line);
        AppCompatImageView img_filter  = dialog.findViewById(R.id.img_filter);
        dialog.show();

        // Arraylist of spinner and size spinner adapter

        ArrayList<String> arrayList_size = new ArrayList<>();
        arrayList_size.add("Single Room");
        arrayList_size.add("1RK");
        arrayList_size.add("1BHK");
        arrayList_size.add("2BHK");
        arrayList_size.add("3BHK");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList_size);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size.setAdapter(adapter);
        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                Log.d("TAG", "onItemSelected: "+arrayList_size.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // price adapter
        ArrayList<String> arrayList_price = new ArrayList<>();
        arrayList_price.add("1000-3000");
        arrayList_price.add("3000-6000");
        arrayList_price.add("6000-10000");
        arrayList_price.add("10000-20000");
        arrayList_price.add("above 20000");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList_price);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price.setAdapter(adapter1);
        price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                Log.d("TAG", "onItemSelected: "+arrayList_price.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //rating adapter
        ArrayList<String> arrayList_rating = new ArrayList<>();
        arrayList_rating.add("1");
        arrayList_rating.add("2");
        arrayList_rating.add("3");
        arrayList_rating.add("4");
        arrayList_rating.add("5");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList_rating);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rating.setAdapter(adapter2);
        rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                Log.d("TAG", "onItemSelected: "+arrayList_rating.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        img_filter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                if(i==false) {
                    // line.setVisibility(View.VISIBLE);
                    img_filter.setAlpha(.3f);
                    rl_top.setAlpha(.3f);

                    i = true;
                }else {
                    //  line.setVisibility(View.GONE);
                    img_filter.setAlpha(.9f);
                    rl_top.setAlpha(.9f);

                    i=false;
                }
            }
        });




    }


    public void showLocationBassesRoomDialogue(RoomDetailsData roomData)
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
         dialog.setCancelable(true);
        dialog.setContentView(R.layout.room_details_dialogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        TextView tv_owner_name = dialog.findViewById(R.id.tv_owner_name);
        TextView tv_owner_mobile = dialog.findViewById(R.id.tv_owner_mobile);
        TextView tv_rent = dialog.findViewById(R.id.tv_rent);
        TextView tv_size = dialog.findViewById(R.id.tv_size);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        ImageView img_room_image = dialog.findViewById(R.id.img_room_image);
        TextView tv_full_address = dialog.findViewById(R.id.tv_full_address);
        TextView tv_view_full_details = dialog.findViewById(R.id.tv_view_full_details);

        tv_owner_name.setText(roomData.getRmOwnFullname());
        tv_owner_mobile.setText(roomData.getRmOwnMbleNum());
        tv_rent.setText(getString(R.string.rs)+" "+roomData.getRmRent());
        tv_size.setText(roomData.getRmSize());
        tv_full_address.setText("House No "+roomData.getRmHouseNo()+", "+roomData.getRmColny()+", "+roomData.getRmCity());

        dialog.show();
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        if(roomData.getImages().size()!=0)
            Glide.with(getActivity().getApplicationContext()).load(roomData.getImages().get(0).getImgName()).into(img_room_image);

        tv_view_full_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Room_Detais_Activity.class);
//                intent.putExtra("name",roomData.getRmOwnFullname());
                intent.putExtra("room_id",String.valueOf(roomData.getRmPkey()));
//                intent.putExtra("mobile",roomData.getRmOwnMbleNum());
//                intent.putExtra("lat",roomData.getRmLatitude());
//                intent.putExtra("lon",roomData.getRmLongitude());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

                dialog.dismiss();
            }
        });

    }
    private void scrooling(final int length) {
        /*After setting the adapter use the timer */
        handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == length) {
                    currentPage = 0;
                }
                vpHomeFirstBanner.setCurrentItem(currentPage++, true);
            }
        };

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        handler.post(runnable);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timertask, DELAY_MS, PERIOD_MS);
    }

    private void loadDataOnMap(ArrayList<LatLng> data) {

        try {

            SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);


        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap eMap) {
                // When map is loaded
               try {
                   googleMap = eMap;
                   if(appSession.getMainlat()!=null&&appSession.getMainlat()!=""&&appSession.getMainlon()!=null&&appSession.getMainlon()!="")
                   {
                       origin = new LatLng(Double.parseDouble(appSession.getMainlat()), Double.parseDouble(appSession.getMainlon()));
                       DrawMarker.getInstance(getActivity().getApplicationContext()).draw(googleMap, origin, R.drawable.location_destinition, "Your Location");
                   }
                   for (int i = 0; i < data.size(); i++) {
                       eMap.addMarker(new MarkerOptions().position(data.get(i)).title(String.valueOf(roomDetailsData.get(i).getRmPkey())));
                   }
                   LatLngBounds bounds = new LatLngBounds.Builder()
                           .include(origin)
                           .build();
                   Point displaySize = new Point();
                   getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
                   eMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 15.0f));

                   if (data.size() < 3)
                       eMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 20.0f));
                   else if (data.size() > 3)
                       eMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 13.0f));
                   else if (data.size() > 10)
                       eMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 10.0f));
                   else if (data.size() > 25)
                       eMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 8.0f));
                   else if (data.size() > 50)
                       eMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 5.0f));
                   eMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                       @Override
                       public boolean onMarkerClick(Marker marker) {
                           for (int i = 0; i < roomDetailsData.size(); i++) {
                               if (String.valueOf(roomDetailsData.get(i).getRmPkey()).equalsIgnoreCase(marker.getTitle())) {
                                   showLocationBassesRoomDialogue(roomDetailsData.get(i));
                               }
                           }

                           return true;

                       }
                   });
               }catch (Exception e)
               {
                    e.printStackTrace();
               }
            }
        });



    }catch (Exception e)
    { e.printStackTrace();}
    }
    private void getNearbyRoom(BannerAdapter.ClicktPost clicktPost)
    {


        try {


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("latitude", appSession.getMainlat());
        jsonObject.addProperty("user_id", String.valueOf(appSession.getUserID()));
        jsonObject.addProperty("longitude",appSession.getMainlon());
        jsonObject.addProperty("radius","2");


        ApiClient.getClient().getNearByRedius(jsonObject).enqueue(new Callback<RoomDetailsModel>() {
            @Override
            public void onResponse(Call<RoomDetailsModel> call, Response<RoomDetailsModel> response) {


                try {
                    mSwipeRefreshLayout.setRefreshing(false);


                    if (response.isSuccessful()) {
                        RoomDetailsModel roomDetailsModel = response.body();

                        if (roomDetailsModel.getStatus() == true) {

                            if(roomDetailsModel.getData().size()!=0)
                            {
                                Log.d(TAG, "roomDetailsModel: " + roomDetailsModel.toString());
                                rl_nearbylocation.setVisibility(View.VISIBLE);
                                data.clear();
                                tv_more_nearby.setVisibility(View.VISIBLE);
//                                cardposter.setVisibility(View.GONE);
                                noRoomFind.setVisibility(View.GONE);
                                roomDetailsData = roomDetailsModel.getData();
                                if(roomDetailsData.size()>10)
                                {
                                   tv_more_nearby.setVisibility(View.VISIBLE);
                                }else {
                                    tv_more_nearby.setVisibility(View.INVISIBLE);
                                }
                                for (int i = 0; i < roomDetailsData.size(); i++) {
                                    origin = new LatLng(Double.parseDouble(roomDetailsData.get(i).getRmLatitude()), Double.parseDouble(roomDetailsData.get(i).getRmLongitude()));
                                    data.add(origin);
                                }
                                loadDataOnMap(data);
                                bannerAdapter = new BannerAdapter(getActivity(), roomDetailsData,clicktPost );
                                vpHomeFirstBanner.setAdapter(bannerAdapter);
                                dotsIndicator.setViewPager(vpHomeFirstBanner);
                                Recomended_Room_Adapter nearByRoom_Adapter = new Recomended_Room_Adapter(getContext(),getActivity(),roomDetailsData,AppSession.FROM_HOME);
                                rec_nearbyroom.setAdapter(nearByRoom_Adapter);
                                rec_nearbyroom.scheduleLayoutAnimation();

                            }else {
                                rl_nearbylocation.setVisibility(View.GONE);
                                cardposter.setVisibility(View.VISIBLE);
                                noRoomFind.setVisibility(View.VISIBLE);
//                                Toast.makeText(getActivity().getApplicationContext(), "Room list not found", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            rl_nearbylocation.setVisibility(View.GONE);
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        rl_nearbylocation.setVisibility(View.GONE);
                        Toast.makeText(getActivity().getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e)
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                    rl_nearbylocation.setVisibility(View.GONE);
//                    Toast.makeText(getActivity().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RoomDetailsModel> call, Throwable t) {
                rl_nearbylocation.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        }catch (Exception e )
        {
            mSwipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        progressDialog.dismiss();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }


}
