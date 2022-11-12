package com.example.myroom.app.new_ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.demo.DemoSearchAdapter;
import com.example.myroom.app.fragment.activity.NewHomeActivityFR;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.home.RoomDetailsModel;
import com.example.myroom.app.new_ui_adapter.NearByRoom_Adapter;
import com.example.myroom.app.retrofit.ApiClient;
import com.example.myroom.app.serializable.DataSerializable;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_Roon_Activity extends AppCompatActivity  {
 RecyclerView rec;
 AppCompatImageView img_back;
    DemoSearchAdapter adapter;
    String lat,lon;
    private RelativeLayout no_internet_rl,rl_main_ui;
    private NewHomeActivityFR newHomeActivityFR;
    ProgressDialog progressDialog;
    ArrayList<RoomDetailsData> data = null;
    private ArrayList<RoomDetailsData> roomDataForSearchArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_roon);
        iniView();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
//                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });

    }

    private void iniView() {
        no_internet_rl = (RelativeLayout)findViewById(R.id.no_internet_rl);
        rl_main_ui = (RelativeLayout)findViewById(R.id.rl_main_ui);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
           rl_main_ui.setVisibility(View.VISIBLE);
           no_internet_rl.setVisibility(View.GONE);
        }else {

            rl_main_ui.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.VISIBLE);
        }
        rec = (RecyclerView)findViewById(R.id.rec);
        img_back = (AppCompatImageView) findViewById(R.id.img_back);
        progressDialog = new ProgressDialog(All_Roon_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting more rooms");

        Intent intent = getIntent();
        lat = String.valueOf(intent.getStringExtra("lat"));
        lon = String.valueOf(intent.getStringExtra("lon"));

        if(lat!=null&&lon!=null)
        {
             if(lat.equalsIgnoreCase("0.0000")||lon.equalsIgnoreCase("0.00000"))
             {

             }else {
                 getRoom(lat,lon,"5");

             }        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }

    public void getRoom(String lat ,String lon , String rd) {
        progressDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", String.valueOf(new AppSession(getApplicationContext()).getUserID()));
        jsonObject.addProperty("latitude", String.valueOf(lat));
        jsonObject.addProperty("longitude", String.valueOf(lon));
        jsonObject.addProperty("radius", rd);


        ApiClient.getClient().getNearByRedius(jsonObject).enqueue(new Callback<RoomDetailsModel>() {
            @Override
            public void onResponse(Call<RoomDetailsModel> call, Response<RoomDetailsModel> response) {


                try {


                    if (response.isSuccessful()) {

                        RoomDetailsModel roomDetailsModel = response.body();

                        if (roomDetailsModel.getStatus() == true) {

                            if (roomDetailsModel.getData().size() != 0) {

                                roomDataForSearchArrayList = roomDetailsModel.getData();
                                adapter = new DemoSearchAdapter(All_Roon_Activity.this, All_Roon_Activity.this, roomDataForSearchArrayList);
                                rec.setAdapter(adapter);
                                rec.scheduleLayoutAnimation();
                                progressDialog.dismiss();


                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(All_Roon_Activity.this, "Room list not found", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(All_Roon_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(All_Roon_Activity.this, response.code(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(All_Roon_Activity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RoomDetailsModel> call, Throwable t) {
                progressDialog.dismiss();
                //  Toast.makeText(All_Roon_Activity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}