package com.example.myroom.app.owerprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.LoginFinal;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.home.RoomDetailsModel;
import com.example.myroom.app.myaccount.MyAccountModel;
import com.example.myroom.app.myaccount.MyAccountModelData;
import com.example.myroom.app.mypost.MyPostAdapter;
import com.example.myroom.app.new_ui.Room_Detais_Activity;
import com.example.myroom.app.new_ui_adapter.Recomended_Room_Adapter;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;

import java.security.spec.ECField;
import java.util.ArrayList;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerProfile extends AppCompatActivity implements MyPostAdapter.Delete {
private TextView tv_owner_name , tv_owner_phone ,tv_present_address ,tv_permanent_address ,empty_text ;
private AppCompatImageView img_back ,img_call ,img_whatsapp;
private AppSession appSession;
private RecyclerView rec;
String userIdOwner = "";
    private MyAccountModelData myAccountModelData;
    private ArrayList<RoomDetailsData> roomDetailsData;
    private ProgressDialog progressDialog;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);

        initView();
        if(userIdOwner!="")
       loadData(this);

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    if(mobile!=null&&mobile!="" )
                    {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" +mobile));
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
                    if(mobile!=null&&mobile!="" )
                    {
                        String contact = "+91"+mobile; // use country code with your phone number
                        String url = "https://api.whatsapp.com/send?phone=" + contact;
                        try {
                            PackageManager pm = OwnerProfile.this.getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            //i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");

                            startActivity(i);
                        } catch (PackageManager.NameNotFoundException e) {
                            Toast.makeText(OwnerProfile.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }





            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });

    }

    private void loadData( MyPostAdapter.Delete delete) {
        loadProfile();
        getMyUploadedRooms(delete);
    }
    private void getMyUploadedRooms(MyPostAdapter.Delete delete) {

        progressDialog.show();
        try {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("user_id",userIdOwner);
//        progressDialog.show();


            ApiClient.getClient().getMyUploadedRooms(jsonObject).enqueue(new Callback<RoomDetailsModel>() {
                @Override
                public void onResponse(Call<RoomDetailsModel> call, Response<RoomDetailsModel> response) {


                    try {

//                    MyPostAdapter adapter = new MyPostAdapter(getContext(),getActivity(),null ,delete);
//                    rec.setAdapter(adapter);
//                    rec.scheduleLayoutAnimation();

                        if (response.isSuccessful()) {
                       progressDialog.dismiss();
                            RoomDetailsModel roomDetailsModel = response.body();

                            if (roomDetailsModel.getStatus() == true) {

                                if(roomDetailsModel.getData().size()!=0)
                                {
                                    rec.setVisibility(View.VISIBLE);
                                    empty_text.setVisibility(View.GONE);
                                    roomDetailsData = roomDetailsModel.getData();
                                    Recomended_Room_Adapter adapter = new Recomended_Room_Adapter(OwnerProfile.this,OwnerProfile.this,roomDetailsData , AppSession.FROM_PROFILE);
                                    rec.setAdapter(adapter);
                                    rec.scheduleLayoutAnimation();

                                }else {
                                    rec.setVisibility(View.INVISIBLE);
                                    empty_text.setVisibility(View.VISIBLE);
                                    Toast.makeText(OwnerProfile.this, "Room list not found", Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                rec.setVisibility(View.INVISIBLE);
                                empty_text.setVisibility(View.VISIBLE);
                                Toast.makeText(OwnerProfile.this.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                       progressDialog.dismiss();
                            empty_text.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity().getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();

                        }
                    }catch (Exception e)
                    {
//                    progressDialog.dismiss();
                        empty_text.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<RoomDetailsModel> call, Throwable t) {
                    empty_text.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                    //  Toast.makeText(getActivity().getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e)
        {
            progressDialog.dismiss();
            e.printStackTrace();
        }

    }
    private void loadProfile()
    {
        try {

            if(userIdOwner!="")
            {
                JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("user_id", userIdOwner);
                ApiClient.getClient().myAccountDetails(jsonObject).enqueue(new Callback<MyAccountModel>() {
                    @Override
                    public void onResponse(Call<MyAccountModel> call, Response<MyAccountModel> response) {
//                        mSwipeRefreshLayout.setRefreshing(false);

                        if(response.isSuccessful())
                        {
                            if(response.body().getStatus()==true)
                            {

                                myAccountModelData = response.body().getData();
                                tv_owner_name.setText(myAccountModelData.getUsrFirstName()+" "+myAccountModelData.getUsrLastName());
                                tv_owner_phone.setText(myAccountModelData.getUsrPhone());
                                 mobile= myAccountModelData.getUsrPhone();
                                tv_present_address.setText(myAccountModelData.getUsrCurrentAdrss());
                                tv_permanent_address.setText(myAccountModelData.getUsrParmentAdrss());


                            }else Toast.makeText(OwnerProfile.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(OwnerProfile.this.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<MyAccountModel> call, Throwable t) {
//                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }else {
//                mSwipeRefreshLayout.setRefreshing(false);
                Intent intent = new Intent(OwnerProfile.this, LoginFinal.class);
                startActivity(intent);
               overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
               finishAffinity();


            }


        }catch (Exception e)
        {
//            mSwipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
        }


    }
    private void initView() {

        try {
            userIdOwner = getIntent().getStringExtra( AppSession.USER_ID_ROOM_OWNER);
        }catch (Exception e)
        {
             e.printStackTrace();
        }
        img_whatsapp = (AppCompatImageView) findViewById(R.id.img_whatsapp);
        img_call  = (AppCompatImageView) findViewById(R.id.img_call );
        progressDialog = new ProgressDialog(OwnerProfile.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting rooms....");
        progressDialog.show();
        img_back  =(AppCompatImageView) findViewById(R.id.img_back);
        tv_owner_name = (TextView) findViewById(R.id.tv_owner_name);
        rec = (RecyclerView) findViewById(R.id.rec);
        tv_owner_phone = (TextView) findViewById(R.id.tv_owner_phone);
        empty_text = (TextView) findViewById(R.id.empty_text);
        tv_present_address = (TextView) findViewById(R.id.tv_present_address);
        tv_permanent_address = (TextView) findViewById(R.id.tv_permanent_address);
        appSession = new AppSession(OwnerProfile.this);
        rec.setLayoutManager(new LinearLayoutManager(OwnerProfile.this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }

    @Override
    public void deleletListner(int pos) {

    }
}