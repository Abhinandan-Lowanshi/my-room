package com.example.myroom.app.new_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myroom.R;
import com.example.myroom.app.AdapterRoomDetailsViewPager;
import com.example.myroom.app.banner_pkg.BannerAdapter;
import com.example.myroom.app.fav.FavModel;
import com.example.myroom.app.map.MapDetailsActivity;
import com.example.myroom.app.new_ui_adapter.Image_Show_Adapter;
import com.example.myroom.app.owerprofile.OwnerProfile;
import com.example.myroom.app.retrofit.ApiClient;
import com.example.myroom.app.reviews.ReviewActivity;
import com.example.myroom.app.roomdetails.RoomDetailsImage;
import com.example.myroom.app.roomdetails.RoomDetailsMain;
import com.example.myroom.app.roomdetails.RoomDetailsMainData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.tbuonomo.viewpagerdotsindicator.BaseDotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zoom.TouchImageView;

public class Room_Detais_Activity extends AppCompatActivity implements OnMapReadyCallback, Image_Show_Adapter.UpdateImageInterafce, AdapterRoomDetailsViewPager.ClicktPost {
    RecyclerView all_pic_rec;
    private RelativeLayout rl_map_layout;
    AppCompatImageView img_back, room_image, room_image_second,img_call ,img_whatsapp;
    private TextView tv_review;
    private ImageView img_handleMapSize;
    String name, mobile, room_id;
    private GoogleMap mMap;
    private Handler handler;
    private String data = "";
    private LatLng origin;
    private int counter = 0;
    private ProgressDialog progressDialog;
    private double lat, lon;
    private  int curPos =0;
    private AdapterRoomDetailsViewPager bannerAdapter;
    private ViewPager vpHomeFirstBanner;
    private ArrayList<RoomDetailsImage> roomDetailsImages;
    private TextView tv_owner_profile, view_location_details, tv_parking, which_flor, tv_dependency, tv_description, tv_furnised, availbility, tv_owner_name, tv_mobile, tv_rent, tv_address;
    private int currentPage;
    private Timer timer;
    private long DELAY_MS = 500;
    private long PERIOD_MS = 5000;
    private BaseDotsIndicator dotsIndicator;
    private String user_id_owner = "";
   private boolean isFillMap = true;
   private    RoomDetailsMainData roomDetailsMainData;
   private ImageView heart_fill , heart_empty ;
    private  RelativeLayout rl_fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detais);
        intiView(this::clickPostListner);


        final ScrollView scroll = (ScrollView) findViewById(R.id.scroll);
        ImageView transparent = (ImageView) findViewById(R.id.imagetrans);

        tv_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Room_Detais_Activity.this, ReviewActivity.class);
                intent.putExtra(AppSession.ROOM_ID , room_id);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        img_handleMapSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setHeigth(isFillMap);

            }
        });
        heart_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("user_id",new AppSession(Room_Detais_Activity.this).getUserID());
                    jsonObject.addProperty("room_id",room_id );
                    jsonObject.addProperty("fav_type", "1");
                    ApiClient.getClient().addToFav(jsonObject).enqueue(new Callback<FavModel>() {
                        @Override
                        public void onResponse(Call<FavModel> call, Response<FavModel> response) {

                            if(response.isSuccessful())
                            {
                                if(response.body().getStatus()==true)
                                {
                                    heart_fill.setVisibility(View.VISIBLE);
                                    heart_empty.setVisibility(View.GONE);
                                    Toast.makeText(Room_Detais_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                }else Toast.makeText(Room_Detais_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }else  Toast.makeText(Room_Detais_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();



                        }

                        @Override
                        public void onFailure(Call<FavModel> call, Throwable t) {
                            Toast.makeText(Room_Detais_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

         heart_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("user_id",new AppSession(Room_Detais_Activity.this).getUserID());
                    jsonObject.addProperty("room_id",room_id );
                    jsonObject.addProperty("fav_type", "0");
                    ApiClient.getClient().addToFav(jsonObject).enqueue(new Callback<FavModel>() {
                        @Override
                        public void onResponse(Call<FavModel> call, Response<FavModel> response) {

                            if(response.isSuccessful())
                            {
                                if(response.body().getStatus()==true)
                                {
                                    heart_fill.setVisibility(View.GONE);
                                    heart_empty.setVisibility(View.VISIBLE);
                                    Toast.makeText(Room_Detais_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                }else Toast.makeText(Room_Detais_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }else  Toast.makeText(Room_Detais_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();



                        }

                        @Override
                        public void onFailure(Call<FavModel> call, Throwable t) {
                            Toast.makeText(Room_Detais_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                if(roomDetailsMainData.getRmOwnMbleNum()!=null&&roomDetailsMainData.getRmOwnMbleNum()!="" )
                {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +roomDetailsMainData.getRmOwnMbleNum()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                }

                }catch (Exception e)
                {
                     e.printStackTrace();
                }

            }
        }); img_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    if(roomDetailsMainData.getRmOwnMbleNum()!=null&&roomDetailsMainData.getRmOwnMbleNum()!="" )
                    {
                        String contact = "+91"+(roomDetailsMainData.getRmOwnMbleNum()); // use country code with your phone number
                        String url = "https://api.whatsapp.com/send?phone=" + contact;
                        try {
                            PackageManager pm = Room_Detais_Activity.this.getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            //i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");

                            startActivity(i);
                        } catch (Exception e) {
                            Log.d("TAG", "img_whatsapp: "+e);
                            Toast.makeText(Room_Detais_Activity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }





            }
        });

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

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        });

        tv_owner_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_id_owner.equalsIgnoreCase("")) {
                    Intent intent = new Intent(Room_Detais_Activity.this, OwnerProfile.class);
                    intent.putExtra(AppSession.USER_ID_ROOM_OWNER, user_id_owner);
                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                }
            }
        });

        view_location_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Room_Detais_Activity.this, MapDetailsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }


    private void fullImageView( final int pos) {
        counter = 0;
        final Dialog dialog = new Dialog(Room_Detais_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.view_full_image_dialouge);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//          dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
//        TouchImageView main_image = dialog.findViewById(R.id.imViewedImage);
        ImageView main_image = dialog.findViewById(R.id.imViewedImage);
        Glide.with(Room_Detais_Activity.this).load(roomDetailsImages.get(pos).getImgName()).placeholder(R.drawable.placeholder).into(main_image);
        dialog.show();

        int max = roomDetailsImages.size();
         curPos = pos;

        ImageView img_close = dialog.findViewById(R.id.img_close);
        ImageView count_left = dialog.findViewById(R.id.count_left);
        ImageView count_right = dialog.findViewById(R.id.count_right);
        CardView card_left = dialog.findViewById(R.id.card_left);
        CardView card_right = dialog.findViewById(R.id.card_right);
        card_left.setVisibility(View.INVISIBLE);
        card_right.setVisibility(View.INVISIBLE);


        if(max>1)
        {
            card_left.setVisibility(View.VISIBLE);
            card_right.setVisibility(View.VISIBLE);

            if(pos==0)
                card_left.setVisibility(View.INVISIBLE);
            if(pos==(max-1))
                card_right.setVisibility(View.INVISIBLE);
        }



        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        count_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                       if(curPos!=0)
                       {

                           curPos = curPos-1;
                           Glide.with(Room_Detais_Activity.this).load(roomDetailsImages.get((curPos)).getImgName()).into(main_image);
                           if(max>1)
                           {
                               card_left.setVisibility(View.VISIBLE);
                               card_right.setVisibility(View.VISIBLE);

                               if(curPos==0)
                                   card_left.setVisibility(View.INVISIBLE);
                               if(curPos==(max-1))
                                   card_right.setVisibility(View.INVISIBLE);
                           }
                       }


            }
        });

        count_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(curPos!=(max-1))
                {
                    curPos = curPos+1;
                    Glide.with(Room_Detais_Activity.this).load(roomDetailsImages.get((curPos)).getImgName()).into(main_image);
                    card_left.setVisibility(View.VISIBLE);
                    card_right.setVisibility(View.VISIBLE);

                    if(curPos==0)
                        card_left.setVisibility(View.INVISIBLE);
                    if(curPos==(max-1))
                        card_right.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    private void intiView(BannerAdapter.ClicktPost clicktPost) {
        all_pic_rec = (RecyclerView) findViewById(R.id.all_pic_rec);
        rl_map_layout = (RelativeLayout) findViewById(R.id.rl_map_layout);
        img_back = (AppCompatImageView) findViewById(R.id.img_back);
        img_whatsapp = (AppCompatImageView) findViewById(R.id.img_whatsapp);
        img_call  = (AppCompatImageView) findViewById(R.id.img_call );
        room_image = (AppCompatImageView) findViewById(R.id.room_image);
        room_image_second = (AppCompatImageView) findViewById(R.id.room_image_second);
        view_location_details = (TextView) findViewById(R.id.view_location_details);
        tv_owner_profile = (TextView) findViewById(R.id.tv_owner_profile);
        tv_parking = (TextView) findViewById(R.id.tv_parking);
        tv_address = (TextView) findViewById(R.id.tv_address);
        which_flor = (TextView) findViewById(R.id.which_flor);
        tv_dependency = (TextView) findViewById(R.id.tv_dependency);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_furnised = (TextView) findViewById(R.id.tv_furnised);
        availbility = (TextView) findViewById(R.id.availbility);
        tv_owner_name = (TextView) findViewById(R.id.tv_owner_name);
        tv_review = (TextView) findViewById(R.id.tv_review);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_rent = (TextView) findViewById(R.id.tv_rent);
        img_handleMapSize = (ImageView) findViewById(R.id.img_handleMapSize);
        heart_empty = (ImageView) findViewById(R.id.heart_empty);
        heart_fill = (ImageView) findViewById(R.id.heart_fill);
        vpHomeFirstBanner = (ViewPager) findViewById(R.id.banner);
        rl_fav = (RelativeLayout) findViewById(R.id.rl_fav);
        progressDialog = new ProgressDialog(Room_Detais_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading details....");
        progressDialog.show();
        dotsIndicator = (WormDotsIndicator) findViewById(R.id.dots_indicator);
//        name = getIntent().getStringExtra("name");
        ViewGroup.LayoutParams params = rl_map_layout.getLayoutParams();
        params.height = 600;
        rl_map_layout.setLayoutParams(params);
        room_id = getIntent().getStringExtra("room_id");
//        mobile = getIntent().getStringExtra("mobile");
        data = getIntent().getStringExtra("come");
        if (data != null) {
            if (data.equalsIgnoreCase(AppSession.FROM_PROFILE)) {
                tv_owner_profile.setVisibility(View.INVISIBLE);
            } else {
                tv_owner_profile.setVisibility(View.VISIBLE);
            }
        }

        if (room_id != null)
            loadRoom(room_id, clicktPost);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(origin).title(name));
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 12.0f));
        // mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
    }

    @Override
    public void UpdateImage(int pos) {
        Glide.with(Room_Detais_Activity.this).load(roomDetailsImages.get(pos).getImgName()).into(room_image);
    }


    public void loadRoom(String room_id, BannerAdapter.ClicktPost clicktPost) {


        try {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("room_id", room_id);
            jsonObject.addProperty("user_id", new AppSession(Room_Detais_Activity.this).getUserID());


            ApiClient.getClient().getRoomDetails(jsonObject).enqueue(new Callback<RoomDetailsMain>() {
                @Override
                public void onResponse(Call<RoomDetailsMain> call, Response<RoomDetailsMain> response) {

                    progressDialog.dismiss();

                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == true) {
                             roomDetailsMainData = response.body().getData();

                             if(!response.body().getMessage().equalsIgnoreCase("Room details not found.")) {
                                 user_id_owner = String.valueOf(roomDetailsMainData.getRmUsrFkey());


                                 loadUI(roomDetailsMainData, clicktPost);
                             }else {
                                 onBackPressed();
                                 Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                             }

                        } else
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<RoomDetailsMain> call, Throwable t) {

                    progressDialog.dismiss();
//                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
//            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);

        }

    }

    private void loadUI(RoomDetailsMainData roomDetailsMainData, BannerAdapter.ClicktPost clicktPost) {

//        tv_parking,which_flor,tv_dependency,tv_furnised,availbility,tv_owner_name ,tv_mobile;
        roomDetailsImages = roomDetailsMainData.getImages();

        if (roomDetailsImages.size() > 0) {
            Glide.with(Room_Detais_Activity.this).load(roomDetailsImages.get(0).getImgName()).into(room_image_second);
            Glide.with(Room_Detais_Activity.this).load(roomDetailsImages.get(0).getImgName()).into(room_image);

            bannerAdapter = new AdapterRoomDetailsViewPager(Room_Detais_Activity.this, clicktPost, roomDetailsImages);
            vpHomeFirstBanner.setAdapter(bannerAdapter);
            dotsIndicator.setViewPager(vpHomeFirstBanner);
            scrooling(roomDetailsImages.size());
//           Image_Show_Adapter image_show_adapter = new Image_Show_Adapter(Room_Detais_Activity.this, roomDetailsImages, this);
//           all_pic_rec.setAdapter(image_show_adapter);
        }
        tv_parking.setText(roomDetailsMainData.getRmPrkingAvblity());
        which_flor.setText(roomDetailsMainData.getRmFlor());
        tv_rent.setText("Rent :- " +getString(R.string.price_Samle) + roomDetailsMainData.getRmRent());
        tv_address.setText(roomDetailsMainData.getRmHouseNo() + " ," + " " + roomDetailsMainData.getRmColny() + " ," + " " + roomDetailsMainData.getRmCity());
        tv_dependency.setText(roomDetailsMainData.getRmDepndecy());
        tv_description.setText(roomDetailsMainData.getRmDescription());
        tv_furnised.setText(roomDetailsMainData.getRmFurnisdStatus());
        availbility.setText(roomDetailsMainData.getRmAvailble());
        tv_owner_name.setText(roomDetailsMainData.getRmOwnFullname());
        tv_mobile.setText(roomDetailsMainData.getRmOwnMbleNum());
        lat = Double.parseDouble(roomDetailsMainData.getRmLatitude());
        lon = Double.parseDouble(roomDetailsMainData.getRmLongitude());
        String userid = new AppSession(Room_Detais_Activity.this).getUserID();
        String id = String.valueOf(roomDetailsMainData.getRmUsrFkey());
        if(!userid.equalsIgnoreCase(id))
            rl_fav.setVisibility(View.VISIBLE);
        origin = new LatLng(lat, lon);
        // origin = new LatLng( 22.4298713, 77.42529669999999);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(roomDetailsMainData.getFavoriteKey()==true)
        {
            heart_fill.setVisibility(View.VISIBLE);
            heart_empty.setVisibility(View.GONE);
        }else {
            heart_fill.setVisibility(View.GONE);
            heart_empty.setVisibility(View.VISIBLE
            );
        }
    }

    @Override
    public void clickPostListner(int pos) {

        fullImageView(pos);


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


    private void setHeigth(boolean isFull) {
        ViewGroup.LayoutParams params = rl_map_layout.getLayoutParams();
        if (isFull) {
           isFillMap = false;
            params.height = 1000;
            img_handleMapSize.setImageResource(R.drawable.ic_baseline_fullscreen_exit_24);
        }
        else {
            isFillMap = true;
            params.height = 600;
            img_handleMapSize.setImageResource(R.drawable.ic_baseline_fullscreen_24);
        }

        rl_map_layout.setLayoutParams(params);
    }
}