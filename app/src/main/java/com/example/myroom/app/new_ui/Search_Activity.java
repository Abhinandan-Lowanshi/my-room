package com.example.myroom.app.new_ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.FavModelTmp;
import com.example.myroom.app.demo.DemoSearchAdapter;
import com.example.myroom.app.demo.Helper;
import com.example.myroom.app.demo.RoomDataForSearch;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.home.RoomDetailsModel;
import com.example.myroom.app.retrofit.ApiClient;
import com.example.myroom.app.searhelper.RecentAdapter;
import com.example.myroom.app.searhelper.RecentSearchManager;
import com.example.myroom.app.searhelper.SearchListData;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import appsession.AppSession;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_Activity extends AppCompatActivity implements RecentAdapter.RecentInterface {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 100;
    private static final String TAG = "hello";
    Boolean i = false;
    AppCompatImageView img_back;
    LinearLayout ll_filter_drop_down;
    AppCompatImageView img_filter;
    TextView tv_no_data_found;
    RecyclerView rec_search;
    DemoSearchAdapter adapter;
    Boolean check = true;
    Helper helper;
    private CountDownTimer timer;
    Geocoder geocoder;
    GifImageView no_data_found;
    CheckBox ck_size, ck_rent, ck_ratting;
    Button apply_filter;
    boolean chech_size, chech_ratting, chech_rent;
    AppCompatEditText tv_max, tv_min, ed_search, ed_raddius;
    String size = "", ratting = "", price_range = "";
    ArrayList<String> arrayList_price = new ArrayList<>();
    ArrayList<String> arrayList_rating = new ArrayList<>();
    ArrayList<String> arrayList_size = new ArrayList<>();
    Spinner sp_Size, sp_price, sp_Rating;
    private RecyclerView rec_recent;
    private String raadius = "";
    private boolean isFilter = false;
    ArrayList<RoomDetailsData> roomDataForSearchArrayList = new ArrayList<>();
    ArrayList<RecentSearchManager> recentSearchManager = new ArrayList<>();
    ArrayList<RoomDetailsData> roomdatafilterdlist = new ArrayList<>();
    ArrayList<RoomDataForSearch> roomdataTempList = new ArrayList<>();
    private double delivery_latitude = 0.00000;
    private double delivery_longitude = 0.00000;
    private ArrayList<RoomDetailsData> roomDetailsData;
    private ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        geocoder = new Geocoder(this, Locale.getDefault());
        Places.initialize(Search_Activity.this, "AIzaSyD8HnhMQpIt9ZGaPnkexNlGomWHOYerTVc");
     new AppSession(Search_Activity.this).setFavModel(new FavModelTmp("0", false , false));

        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(Search_Activity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        ed_raddius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                try {


                    if (timer != null) {
                        timer.cancel();
                    }

                    timer = new CountDownTimer(1500, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            if (charSequence.toString().isEmpty()) {
                                if(delivery_latitude!=0.00000)
                                getSearchResult(delivery_latitude, delivery_longitude, "3");
                            }
                            else {
                                if(Integer.parseInt(charSequence.toString())<=15) {
                                    if(delivery_latitude!=0.00000)
                                    getSearchResult(delivery_latitude, delivery_longitude, charSequence.toString());
                                }
                                else ed_raddius.setError("You can't set raddius more than 15 km");

                            }

                        }

                    }.start();



//
//                    timer.schedule(
//                            new TimerTask(){
//                                @Override
//                                public void run(){
//                                    if(!raadius.equalsIgnoreCase(charSequence.toString()))
//                                        raadius=charSequence.toString();
//                                    Log.d(TAG, "charSequence.toString()"+charSequence.toString());
//                                    // you cannot touch the UI from another thread. This thread now calls a function on the main thread
//
//                                }
//                            }, 2000);



//                    timer.scheduleAtFixedRate(new TimerTask() {
//                        @Override
//                        public void run() {
//                            if (Looper.myLooper()==null)
//                                Looper.prepare();
//
//
//
////                            if (charSequence.toString().isEmpty()) {
////                                getSearchResult(delivery_latitude, delivery_longitude, "3");
////                            }
////                            else {
////                                getSearchResult(delivery_latitude, delivery_longitude, charSequence.toString());
////
////                            }
//                        }
//                    }, 3000, 1000);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.d(TAG, "afterTextChanged: "+editable.toString());
            }
        });
        sp_Size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                size = arrayList_size.get(i);
                roomdatafilterdlist.clear();
                applyfilter();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tv_min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                roomdatafilterdlist.clear();
                applyfilter();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tv_max.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                roomdatafilterdlist.clear();
                applyfilter();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sp_Rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                Log.d("TAG", "onItemSelected: " + arrayList_rating.get(i));
                ratting = arrayList_rating.get(i);
                roomdatafilterdlist.clear();
                applyfilter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp_price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                Log.d("TAG", "onItemSelected: " + arrayList_price.get(i));
                price_range = arrayList_price.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ck_size.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chech_size = b;
                applyfilter();
            }
        });

        ck_rent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chech_rent = b;
                roomdatafilterdlist.clear();
                applyfilter();
            }
        });
        ck_ratting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chech_ratting = b;
                roomdatafilterdlist.clear();
                applyfilter();
            }
        });
        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == true) {
                    ll_filter_drop_down.setVisibility(View.VISIBLE);
                    img_filter.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    check = false;
                } else {
                    ll_filter_drop_down.setVisibility(View.GONE);
                    img_filter.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    check = true;

                }

//                show_Dialogue();
            }
        });


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

//        apply_filter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            // apply_filter();
//                roomdatafilterdlist.clear();
//              //  applyfilter();
//            }
//        });


    }

    private String getCurrentTIme() {
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        return  currentTime;
    }

    private int getTimeDffence(String current , String past) throws ParseException {
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        Date date1 = simpleDateFormat.parse(current);
         Date date2 = simpleDateFormat.parse(past);

         long difference = date2.getTime() - date1.getTime();
         int days = (int) (difference / (1000 * 60 * 60 * 24));
         int  hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
         int  min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
        return   hours = (hours < 0 ? -hours : hours);


     }

    private  void isPresentData(String isPresent , double lat , double lon , RecentAdapter.RecentInterface recentInterface)
    {
        SearchListData searchListData1 = new AppSession(Search_Activity.this).getStoredRoom();
        if(searchListData1!=null){
        boolean check = false;
        ArrayList<RecentSearchManager >recentSearchManager = searchListData1.getRecentSearchManagers();

        for(int i =0 ; i<recentSearchManager.size() ; i++)
        {

       if(isPresent.equalsIgnoreCase(recentSearchManager.get(i).getKey()))
       {
           check = true;
       }

        }

         if(check==false)
         {
             RecentSearchManager recentSearchManager1 = new RecentSearchManager();
             recentSearchManager1.setKey(isPresent);
             recentSearchManager1.setLat(lat);
             recentSearchManager1.setLon(lon);

             if(recentSearchManager.size()>=10)
             {
                 recentSearchManager.remove(9);
             }
             recentSearchManager.add(0,recentSearchManager1);
             SearchListData searchListData = new SearchListData();
             searchListData.setRecentSearchManagers(recentSearchManager);
             RecentAdapter recentAdapter = new RecentAdapter(Search_Activity.this,recentSearchManager ,recentInterface);
             rec_recent.setAdapter(recentAdapter);
             new AppSession(Search_Activity.this).setStoredRoom(searchListData);

         }else {

         }
        }
        else{
            ArrayList<RecentSearchManager >recentSearchManager = new ArrayList<>();
            RecentSearchManager recentSearchManager1 = new RecentSearchManager();
            recentSearchManager1.setKey(isPresent);
            recentSearchManager1.setLat(lat);
            recentSearchManager1.setLon(lon);
            recentSearchManager.add(recentSearchManager1);
            SearchListData searchListData = new SearchListData();
            searchListData.setRecentSearchManagers(recentSearchManager);
            RecentAdapter recentAdapter = new RecentAdapter(Search_Activity.this,recentSearchManager ,recentInterface);
            rec_recent.setAdapter(recentAdapter);
            new AppSession(Search_Activity.this).setStoredRoom(searchListData);

        }

    }

    private void setData(String isPresent ,double lat , double lon) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            FavModelTmp favModelTmp = new AppSession(Search_Activity.this).getFavModel();
            if(favModelTmp!=null && favModelTmp.isUpdated()!=false)
            {
                updateFaveStatus(favModelTmp);
            }
        }catch (Exception e)
        {
             e.printStackTrace();
        }

      
    }

    private void updateFaveStatus(FavModelTmp favModelTmp) {
        try {
            ArrayList <RoomDetailsData> tmp = new ArrayList<>();
            if(isFilter)
            {
                tmp = roomdatafilterdlist;
            }else {
                tmp = roomDataForSearchArrayList;
            }
            for(int i = 0 ; i <tmp.size() ; i++)
            {
                if(tmp.get(i).getRmPkey().toString().equalsIgnoreCase(favModelTmp.getId()))
                {
                    tmp.get(i).setFavoriteKey(String.valueOf(favModelTmp.isStatus()));
                }

            }
            adapter = new DemoSearchAdapter(Search_Activity.this,Search_Activity.this,tmp);
            rec_search.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }catch (Exception e)
        {   ArrayList <RoomDetailsData> tmp = new ArrayList<>();
            if(isFilter)
            {
                tmp = roomdatafilterdlist;
            }else {
                tmp = roomDataForSearchArrayList;
            }
            adapter = new DemoSearchAdapter(Search_Activity.this,Search_Activity.this,tmp);
            rec_search.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }


    }

    private void apply_filter() {

        //for size
//        roomDataForSearchArrayList.clear();
//       Helper helper1 = new Helper();
//        roomDataForSearchArrayList = helper1.getRoomDataForSearch();
//        roomdatafilterdlist.clear();
//        roomdatafilterdlist = roomDataForSearchArrayList;
//
//        if(chech_size==true) {
//            //roomdatafilterdlist.clear();
//
//            for (int i = 0; i < roomDataForSearchArrayList.size(); i++) {
//                if (roomDataForSearchArrayList.get(i).getSize().equalsIgnoreCase(size)) {
//                    roomdatafilterdlist.add(roomDataForSearchArrayList.get(i));
//                }
//            }
//        }
//
//          //for ratting
//        if(chech_ratting==true) {
//
//            for (int i = 0; i < roomdatafilterdlist.size(); i++) {
//                if (roomdatafilterdlist.get(i).getRatting().equalsIgnoreCase(ratting)) {
//                    roomdataTempList.add(roomdatafilterdlist.get(i));
//                }
//            }
//            roomdatafilterdlist.clear();
//            roomdatafilterdlist = roomdataTempList;
//
//        }
//
//            if(chech_rent==true) {
//                int max, min;
//                if (!tv_min.getText().toString().isEmpty() && !tv_max.getText().toString().isEmpty()) {
//
//                    min = Integer.parseInt(tv_min.getText().toString());
//                    max = Integer.parseInt(tv_max.getText().toString());
//                } else {
//                    max = 40000;
//                    min = 0;
//                }
//                for (int i = 0; i < roomdatafilterdlist.size(); i++) {
//                    if (min <= Integer.parseInt(roomdatafilterdlist.get(i).getRent()) && max >= Integer.parseInt(roomdatafilterdlist.get(i).getRent())) {
//                        roomdataTempList.add(roomDataForSearchArrayList.get(i));
//                        roomdatafilterdlist.clear();
//                        roomdatafilterdlist = roomdataTempList;
//                    }
//                }
//            }
//
//            DemoSearchAdapter  adapter = new DemoSearchAdapter(getApplicationContext(),this,roomdatafilterdlist);
//            rec_search.setAdapter(adapter);
//        roomdataTempList.clear();


//        roomdatafilterdlist.clear();
//        int max,min;
//          if(!tv_min.getText().toString().isEmpty()&&!tv_max.getText().toString().isEmpty()) {
//              min = Integer.parseInt(tv_min.getText().toString());
//              max = Integer.parseInt(tv_max.getText().toString());
//          }else {
//               max = 40000;
//               min =0;
//          }
//
//            for(int i=0; i<roomDataForSearchArrayList.size();i++)
//                 {
//                      if(roomDataForSearchArrayList.get(i).getSize().equalsIgnoreCase(size))
//                     {
//                         if(ratting.equalsIgnoreCase(roomDataForSearchArrayList.get(i).getRatting()))
//                             if(min<=Integer.parseInt(roomDataForSearchArrayList.get(i).getRent())&&max>=Integer.parseInt(roomDataForSearchArrayList.get(i).getRent()))
//                              roomdatafilterdlist.add(roomDataForSearchArrayList.get(i));
//                     }
//
//
//        }
//        DemoSearchAdapter  adapter = new DemoSearchAdapter(getApplicationContext(),this,roomdatafilterdlist);
//        rec_search.setAdapter(adapter);


    }

    public void applyfilter() {
        String resutl = null;
        isFilter = true;
        // for A
        roomdatafilterdlist.clear();
        if (chech_size == true) {
            if (chech_rent == false) {
                for (int i = 0; i < roomDataForSearchArrayList.size(); i++) {
                    if (roomDataForSearchArrayList.get(i).getRmSize().equalsIgnoreCase(size)) {
                        roomdatafilterdlist.add(roomDataForSearchArrayList.get(i));
                    }
                }

            }
        }
        // for B
        if (chech_rent == true) {
            if (chech_size == false) {


                int max = 40000, min = 0;

                if (!tv_min.getText().toString().isEmpty()) {
                    min = Integer.parseInt(tv_min.getText().toString());
                }
                if (!tv_max.getText().toString().isEmpty()) {
                    max = Integer.parseInt(tv_max.getText().toString());
                }
                for (int i = 0; i < roomDataForSearchArrayList.size(); i++) {

                    if (min <= Integer.parseInt(roomDataForSearchArrayList.get(i).getRmRent()) && max >= Integer.parseInt(roomDataForSearchArrayList.get(i).getRmRent()))
                        roomdatafilterdlist.add(roomDataForSearchArrayList.get(i));
                }

            }
        }

        // C

        //    AB
        if (chech_size == true) {
            if (chech_rent == true) {


                int max = 40000, min = 0;

                if (!tv_min.getText().toString().isEmpty()) {
                    min = Integer.parseInt(tv_min.getText().toString());
                }
                if (!tv_max.getText().toString().isEmpty()) {
                    max = Integer.parseInt(tv_max.getText().toString());
                }
                for (int i = 0; i < roomDataForSearchArrayList.size(); i++) {
                    if (roomDataForSearchArrayList.get(i).getRmSize().equalsIgnoreCase(size) && min <= Integer.parseInt(roomDataForSearchArrayList.get(i).getRmRent()) && max >= Integer.parseInt(roomDataForSearchArrayList.get(i).getRmRent())) {
                        roomdatafilterdlist.add(roomDataForSearchArrayList.get(i));
                    }
                }


            }
        }


        //    CA

//               if (chech_size==true){
//               if (chech_rent == false) {
//
//                   for (int i = 0; i < roomDataForSearchArrayList.size(); i++) {
//                       if ( roomDataForSearchArrayList.get(i).getRmSize().equalsIgnoreCase(size)) {
//                           roomdatafilterdlist.add(roomDataForSearchArrayList.get(i));
//                       }
//                   }
//
//
//           }
//           }

//           //    BC
//           if(chech_rent==true) {
//
//               if (chech_size == false) {
//
//                   int max = 40000, min = 0;
//
//                   if (!tv_min.getText().toString().isEmpty())
//                   {
//                        min=Integer.parseInt(tv_min.getText().toString());
//                   }
//                   if(!tv_max.getText().toString().isEmpty())
//                   {
//                       max=Integer.parseInt(tv_max.getText().toString());
//                   }
//
//
//                   for (int i = 0; i < roomDataForSearchArrayList.size(); i++) {
//                       if ( min <= Integer.parseInt(roomDataForSearchArrayList.get(i).getRmRent()) && max >= Integer.parseInt(roomDataForSearchArrayList.get(i).getRmRent())) {
//                           roomdatafilterdlist.add(roomDataForSearchArrayList.get(i));
//                       }
//                   }
//
//
//
//           }
//           }

        //    ABC

//               if (chech_size==true) {
//
//                   if (chech_rent==true) {
//
//
//
//                       int max=40000, min=0;
//
//                       if (!tv_min.getText().toString().isEmpty())
//                       {
//                           min=Integer.parseInt(tv_min.getText().toString());
//                       }
//                       if(!tv_max.getText().toString().isEmpty())
//                       {
//                           max=Integer.parseInt(tv_max.getText().toString());
//                       }
//
//                       for (int i = 0; i < roomDataForSearchArrayList.size(); i++) {
//                           if (roomDataForSearchArrayList.get(i).getRmSize().equalsIgnoreCase(size)) {
//
//                                   if (min <= Integer.parseInt(roomDataForSearchArrayList.get(i).getRmRent()) && max >= Integer.parseInt(roomDataForSearchArrayList.get(i).getRmRent()))
//                                       roomdatafilterdlist.add(roomDataForSearchArrayList.get(i));
//                           }
//
//
//                       }
//                   }
//
//           }
        int i = 0;
        //    ABC
        if (chech_size == false) {
            if (chech_rent == false) {
                isFilter = false;
                DemoSearchAdapter adapter = new DemoSearchAdapter(getApplicationContext(), this, roomDataForSearchArrayList);
                rec_search.setAdapter(adapter);
                i = 1;
                if (roomDataForSearchArrayList.size() == 0) {
//                    no_data_found.setVisibility(View.VISIBLE);
//                    tv_no_data_found.setVisibility(View.VISIBLE);


                } else {
//                    no_data_found.setVisibility(View.GONE);
//                    tv_no_data_found.setVisibility(View.GONE);
                }
            }


        }
        if (i == 0) {
            if (roomdatafilterdlist.size() == 0) {
//                no_data_found.setVisibility(View.VISIBLE);
//                tv_no_data_found.setVisibility(View.VISIBLE);


            } else {
//                no_data_found.setVisibility(View.GONE);
//                tv_no_data_found.setVisibility(View.GONE);
            }
            DemoSearchAdapter adapter = new DemoSearchAdapter(getApplicationContext(), this, roomdatafilterdlist);
            rec_search.setAdapter(adapter);
            //  roomdatafilterdlist.clear();
        }

    }

    private void initView() {

//        new AppSession(Search_Activity.this).setStoredRoom(null);
        progressDialog = new ProgressDialog(Search_Activity.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("searching....");
        img_filter = (AppCompatImageView) findViewById(R.id.img_filter);
        rec_recent = (RecyclerView) findViewById(R.id.rec_recent);
        img_back = (AppCompatImageView) findViewById(R.id.img_back);
        no_data_found = (GifImageView) findViewById(R.id.no_data_found);
        ll_filter_drop_down = (LinearLayout) findViewById(R.id.ll_filter_drop_down);
        rec_search = (RecyclerView) findViewById(R.id.rec_search);
        sp_Size = (Spinner) findViewById(R.id.sp_Size);
        apply_filter = (Button) findViewById(R.id.apply_filter);
        sp_price = (Spinner) findViewById(R.id.sp_price);
        tv_max = (AppCompatEditText) findViewById(R.id.tv_max);
        ed_search = (AppCompatEditText) findViewById(R.id.ed_search);
        ed_raddius = (AppCompatEditText) findViewById(R.id.ed_raddius);
        tv_min = (AppCompatEditText) findViewById(R.id.tv_min);
        sp_Rating = (Spinner) findViewById(R.id.sp_Rating);
        ck_size = (CheckBox) findViewById(R.id.ck_size);
        ck_rent = (CheckBox) findViewById(R.id.ck_rent);
        tv_no_data_found = (TextView) findViewById(R.id.tv_no_data_found);
        ck_ratting = (CheckBox) findViewById(R.id.ck_ratting);

        try {

            SearchListData searchListData = new AppSession(Search_Activity.this).getStoredRoom();
            if(searchListData!=null) {
                if(searchListData.getRecentSearchManagers().size()!=0) {
                    RecentAdapter recentAdapter = new RecentAdapter(Search_Activity.this, searchListData.getRecentSearchManagers(), this);
                    rec_recent.setAdapter(recentAdapter);
                }else {

                }
            }
        }catch (Exception e)
        {

        }


        helper = new Helper();
//        roomDataForSearchArrayList = helper.getRoomDataForSearch();


        arrayList_size.add("Single Room");
        arrayList_size.add("1RK");
        arrayList_size.add("1BHK");
        arrayList_size.add("2BHK");
        arrayList_size.add("3BHK");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_size);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Size.setAdapter(adapter);


        arrayList_price.add("1000-3000");
        arrayList_price.add("3000-6000");
        arrayList_price.add("6000-10000");
        arrayList_price.add("10000-20000");
        arrayList_price.add("above 20000");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_price);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_price.setAdapter(adapter1);


        arrayList_rating.add("1");
        arrayList_rating.add("2");
        arrayList_rating.add("3");
        arrayList_rating.add("4");
        arrayList_rating.add("5");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_rating);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Rating.setAdapter(adapter2);


    }

    private void show_Dialogue() {

        final Dialog dialog = new Dialog(Search_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.search_dialogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        AppCompatEditText search = dialog.findViewById(R.id.ed_search);
        Spinner size = dialog.findViewById(R.id.sp_Size);
        Spinner price = dialog.findViewById(R.id.sp_price);
        Spinner rating = dialog.findViewById(R.id.sp_Rating);
        Button submit = dialog.findViewById(R.id.submit);
        LinearLayout rl_top = dialog.findViewById(R.id.ll_top2);
        // View line  = dialog.findViewById(R.id.line);
        AppCompatImageView img_filter = dialog.findViewById(R.id.img_filter);
        dialog.show();

        // Arraylist of spinner and size spinner adapter
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Log.d("TAG", "hhhhhhhhh");
            }
        });


        // price adapter


        //rating adapter


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        img_filter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {
                if (i == false) {
                    // line.setVisibility(View.VISIBLE);
                    img_filter.setAlpha(.3f);
                    rl_top.setAlpha(.3f);

                    i = true;
                } else {
                    //  line.setVisibility(View.GONE);
                    img_filter.setAlpha(.9f);
                    rl_top.setAlpha(.9f);

                    i = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
               String fullAddress =  place.getAddress();
               String name =  place.getName();
               List<String> add2 =  place.getAttributions();



                delivery_latitude = place.getLatLng().latitude;
                delivery_longitude = place.getLatLng().longitude;
                isPresentData(name , delivery_latitude ,delivery_longitude ,this);
                Log.e(TAG, delivery_latitude + " onActivityResult: deli " + delivery_longitude);
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocation(delivery_latitude, delivery_longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String state = addresses.get(0).getAdminArea();
                    String currentLocation = address;
                    // String city = addresses.get(0).getLocality() + "," + state;
                    String city = addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() + "," + state : addresses.get(0).getSubAdminArea();

                    delivery_latitude = addresses.get(0).getLatitude();
                    delivery_longitude = addresses.get(0).getLongitude();
                    ed_search.setText(name);
                    if (ed_raddius.getText().toString().isEmpty())
                        getSearchResult(delivery_latitude, delivery_longitude, "3");
                    else
                        getSearchResult(delivery_latitude, delivery_longitude, ed_raddius.getText().toString());
                } catch (IOException e) {
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

    public void getSearchResult(Double lat, Double lon, String rd) {

        delivery_latitude = lat;
        delivery_longitude=lon;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", String.valueOf(new AppSession(getApplicationContext()).getUserID()));
        jsonObject.addProperty("latitude", String.valueOf(lat));
        jsonObject.addProperty("longitude", String.valueOf(lon));
        jsonObject.addProperty("radius", rd);

        progressDialog.show();
        ApiClient.getClient().getNearByRedius(jsonObject).enqueue(new Callback<RoomDetailsModel>() {
            @Override
            public void onResponse(Call<RoomDetailsModel> call, Response<RoomDetailsModel> response) {

                rec_search.setVisibility(View.VISIBLE);
                try {
                    progressDialog.dismiss();


                    if (response.isSuccessful()) {

                        RoomDetailsModel roomDetailsModel = response.body();

                        if (roomDetailsModel.getStatus() == true) {

                            if (roomDetailsModel.getData().size() != 0) {
                                rec_search.setVisibility(View.VISIBLE);
//                                no_data_found.setVisibility(View.GONE);
//                                tv_no_data_found.setVisibility(View.GONE);
                                roomDataForSearchArrayList = roomDetailsModel.getData();
                                adapter = new DemoSearchAdapter(Search_Activity.this, Search_Activity.this, roomDataForSearchArrayList);
                                rec_search.setAdapter(adapter);


                            } else {
                                rec_search.setVisibility(View.GONE);
//                                no_data_found.setVisibility(View.VISIBLE);
//                                tv_no_data_found.setVisibility(View.VISIBLE);

                                Toast.makeText(Search_Activity.this, "Room list not found", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            rec_search.setVisibility(View.GONE);
//                            no_data_found.setVisibility(View.VISIBLE);
//                            tv_no_data_found.setVisibility(View.VISIBLE);
                            rec_search.clearFocus();
                            Toast.makeText(Search_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        rec_search.setVisibility(View.GONE);
//                        no_data_found.setVisibility(View.VISIBLE);
//                        tv_no_data_found.setVisibility(View.VISIBLE);
                        rec_search.clearFocus();
                        Toast.makeText(Search_Activity.this, response.code(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    rec_search.setVisibility(View.GONE);
//                    no_data_found.setVisibility(View.VISIBLE);
//                    tv_no_data_found.setVisibility(View.VISIBLE);
                    rec_search.clearFocus();
                    Toast.makeText(Search_Activity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RoomDetailsModel> call, Throwable t) {
                progressDialog.dismiss();
                rec_search.clearFocus();
                rec_search.setVisibility(View.GONE);
//                no_data_found.setVisibility(View.VISIBLE);
//                tv_no_data_found.setVisibility(View.VISIBLE);
//                Toast.makeText(Search_Activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void clickRecent(RecentSearchManager recentSearchManager) {
        ed_search.setText(recentSearchManager.getKey());
        getSearchResult(recentSearchManager.getLat(),recentSearchManager.getLon(),"3");
    }
}