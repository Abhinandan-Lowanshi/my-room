package com.example.myroom.app.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myroom.R;
import com.example.myroom.app.fav.FavModel;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.new_ui.Room_Detais_Activity;
import com.example.myroom.app.new_ui_adapter.NearByRoom_Adapter;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoSearchAdapter extends RecyclerView.Adapter<DemoSearchAdapter.MyViewHolder> {
    Context context;
    Activity activity;
    AppSession appSession;
    DemoSearchAdapter.MyViewHolder holder1;
    ArrayList<RoomDetailsData> data = new ArrayList<>();
    private final static int FADE_DURATION = 300;
    public DemoSearchAdapter(Context context, Activity activity, ArrayList<RoomDetailsData> data) {
        this.context = context;
        this.activity = activity;
        this.data = data;
        appSession = new AppSession(context);
    }

    @NonNull
    @NotNull
    @Override
    public DemoSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nearbylocation_row,parent,false);
        return new DemoSearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DemoSearchAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setFadeAnimation(holder.itemView);
        holder1 = holder;

        holder.tv_owner_name.setText(data.get(position).getRmSize());
       holder.tv_address.setText("House no: "+data.get(position).getRmHouseNo()+" , colony: "+data.get(position).getRmColny());
//        holder.tv_ratting.setText(data.get(position).getRmRatting());
        holder.ratingBar.setRating(Float.valueOf("2"));
        holder.tv_rent.setText(context.getString(R.string.price)+data.get(position).getRmRent());
//        holder.tv_size.setText(data.get(position).getRmSize());


        if(data.get(position).getFavoriteKey().equalsIgnoreCase("false"))
        {
            holder1.heart_fill.setVisibility(View.GONE);
            holder1.heart_empty.setVisibility(View.VISIBLE);
        }else {
            holder1.heart_fill.setVisibility(View.VISIBLE);
            holder1.heart_empty.setVisibility(View.GONE);
        }
         if(data.get(position).getImages().size()>0)
         {
             Glide.with(context).load(data.get(position).getImages().get(0).getImgName()).into(holder.room_image);

         }
        holder.heart_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("user_id",appSession.getUserID());
                    jsonObject.addProperty("room_id", String.valueOf(data.get(position).getRmPkey()));
                    jsonObject.addProperty("fav_type", "0");
                    ApiClient.getClient().addToFav(jsonObject).enqueue(new Callback<FavModel>() {
                        @Override
                        public void onResponse(Call<FavModel> call, Response<FavModel> response) {

                            if(response.isSuccessful())
                            {
                                if(response.body().getStatus()==true)
                                {
                                    holder.heart_fill.setVisibility(View.GONE);
                                    holder.heart_empty.setVisibility(View.VISIBLE);
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                }else Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }else  Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();



                        }

                        @Override
                        public void onFailure(Call<FavModel> call, Throwable t) {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }catch (Exception e)
                {
                    e.printStackTrace();
                }





            }
        });

         holder.view.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(context, Room_Detais_Activity.class);
                 intent.putExtra("room_id",String.valueOf(data.get(position).getRmPkey()));
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(intent);
//                 activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
             }
         });
        holder.heart_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("user_id",appSession.getUserID());
                    jsonObject.addProperty("room_id", String.valueOf(data.get(position).getRmPkey()));
                    jsonObject.addProperty("fav_type", "1");
                    ApiClient.getClient().addToFav(jsonObject).enqueue(new Callback<FavModel>() {
                        @Override
                        public void onResponse(Call<FavModel> call, Response<FavModel> response) {

                            if(response.isSuccessful())
                            {
                                if(response.body().getStatus()==true)
                                {
                                    holder.heart_fill.setVisibility(View.VISIBLE);
                                    holder.heart_empty.setVisibility(View.GONE);
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                }else Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }else  Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();



                        }

                        @Override
                        public void onFailure(Call<FavModel> call, Throwable t) {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });
    }
    private void favMethod(String i,int pos) {



    }
    @Override
    public int getItemCount() {
       return data.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.5f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        View view ;
        TextView tv_ratting,tv_address,tv_owner_name,tv_rent,tv_size;
        ImageView room_image,heart_empty,heart_fill;
        RatingBar ratingBar;
        RelativeLayout rl_main;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView;
            tv_rent = itemView.findViewById(R.id.tv_rent);
            tv_ratting = itemView.findViewById(R.id.tv_ratting);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_owner_name = itemView.findViewById(R.id.tv_owner_name);
            tv_size = itemView.findViewById(R.id.tv_size);
            room_image = itemView.findViewById(R.id.room_image);
            heart_empty = (ImageView) itemView.findViewById(R.id.heart_empty);
            heart_fill = (ImageView) itemView.findViewById(R.id.heart_fill);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            rl_main = (RelativeLayout) itemView.findViewById(R.id.rl_main);
        }
    }
}


