package com.example.myroom.app.mypost;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myroom.R;
import com.example.myroom.app.demo.DemoSearchAdapter;
import com.example.myroom.app.editroom.Edit_Room_Activity;
import com.example.myroom.app.fav.FavModel;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.new_ui.Room_Detais_Activity;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyViewHolder>{
    Context context;
    Activity activity;
    AppSession appSession;
    Delete delete;
    DemoSearchAdapter.MyViewHolder holder1;
    ArrayList<RoomDetailsData> data = new ArrayList<>();
    private final static int FADE_DURATION = 300;
    public MyPostAdapter(Context context, Activity activity, ArrayList<RoomDetailsData> data , Delete delete) {
        this.context = context;
        this.delete = delete;
        this.activity = activity;
        this.data = data;
        appSession = new AppSession(context);
    }

    @NonNull
    @NotNull
    @Override
    public MyPostAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mypostrec,parent,false);
        return new MyPostAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPostAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        setFadeAnimation(holder.itemView);

        holder.tv_owner_name.setText(data.get(position).getRmSize());
        holder.tv_address.setText("House no: "+data.get(position).getRmHouseNo()+" , colony: "+data.get(position).getRmColny());
//        holder.tv_ratting.setText(data.get(position).getRmRatting());
        holder.ratingBar.setRating(Float.valueOf("2"));
        holder.tv_rent.setText(context.getString(R.string.price)+data.get(position).getRmRent());
//        holder.tv_size.setText(data.get(position).getRmSize());


        if(data.get(position).getFavoriteKey().equalsIgnoreCase("false"))
        {
            holder.heart_fill.setVisibility(View.GONE);
            holder.heart_empty.setVisibility(View.VISIBLE);
        }else {
            holder.heart_fill.setVisibility(View.VISIBLE);
            holder.heart_empty.setVisibility(View.GONE);
        }
        if(data.get(position).getRmStatus().equalsIgnoreCase("false"))
        {
            holder.card_Activate.setVisibility(View.GONE);
            holder.card_De_Activate.setVisibility(View.VISIBLE);
        }else {
            holder.card_Activate.setVisibility(View.VISIBLE);
            holder.card_De_Activate.setVisibility(View.GONE);
        }
        if(data.get(position).getImages().size()>0)
        {
            Glide.with(context).load(data.get(position).getImages().get(0).getImgName()).into(holder.room_image);

        }


        holder.card_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edit_Room_Activity.class);
                intent.putExtra(AppSession.ROOM_ID,String.valueOf(data.get(position).getRmPkey()));
                intent.putExtra(AppSession.USER_NAME,String.valueOf(data.get(position).getRmOwnFullname()));
                intent.putExtra(AppSession.PHONE,String.valueOf(data.get(position).getRmOwnMbleNum()));
                intent.putExtra(AppSession.ROOM_SIZE,String.valueOf(data.get(position).getRmSize()));
                intent.putExtra(AppSession.F_STATUS,String.valueOf(data.get(position).getRmFurnisdStatus()));
                intent.putExtra(AppSession.AVAILABLE,String.valueOf(data.get(position).getRmAvailble()));
                intent.putExtra(AppSession.PARKING,String.valueOf(data.get(position).getRmPrkingAvblity()));
                intent.putExtra(AppSession.INDENDENT,String.valueOf(data.get(position).getRmDepndecy()));
                intent.putExtra(AppSession.FLOR,String.valueOf(data.get(position).getRmFlor()));
                intent.putExtra(AppSession.RENT,String.valueOf(data.get(position).getRmRent()));
                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        holder.card_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete.deleletListner(position);
            }
        });

        holder.card_Activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("room_id",String.valueOf(data.get(position).getRmPkey()));
                    jsonObject.addProperty("status_type","0");
                    ApiClient.getClient().toRoomStatus(jsonObject).enqueue(new Callback<RoomStatusModel>() {
                        @Override
                        public void onResponse(Call<RoomStatusModel> call, Response<RoomStatusModel> response) {

                            if(response.isSuccessful())
                            {

                                if(response.body().getStatus()==true)
                                {
                                     holder.card_Activate.setVisibility(View.GONE);
                                     holder.card_De_Activate.setVisibility(View.VISIBLE);
                                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG);
                                }else  Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);

                            }else   Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);

                        }

                        @Override
                        public void onFailure(Call<RoomStatusModel> call, Throwable t) {
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);
                        }
                    });

                }catch (Exception e)
                {
                     Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);
                     e.printStackTrace();
                }



            }
        });

        holder.card_De_Activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("room_id",String.valueOf(data.get(position).getRmPkey()));
                    jsonObject.addProperty("status_type","1");
                    ApiClient.getClient().toRoomStatus(jsonObject).enqueue(new Callback<RoomStatusModel>() {
                        @Override
                        public void onResponse(Call<RoomStatusModel> call, Response<RoomStatusModel> response) {

                            if(response.isSuccessful())
                            {

                                if(response.body().getStatus()==true)
                                {
                                    holder.card_Activate.setVisibility(View.VISIBLE);
                                    holder.card_De_Activate.setVisibility(View.GONE);
                                    Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG);
                                }else  Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);

                            }else   Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);

                        }

                        @Override
                        public void onFailure(Call<RoomStatusModel> call, Throwable t) {
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);
                        }
                    });

                }catch (Exception e)
                {
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG);
                    e.printStackTrace();
                }


            }
        });
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
                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
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
   public  interface Delete
   {
      public void deleletListner(int pos);
   }
    @Override
    public int getItemCount() {
        return data.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.4f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        View view ;
        TextView tv_ratting,tv_address,tv_owner_name,tv_rent,tv_size;
        ImageView room_image,heart_empty,heart_fill;
        RatingBar ratingBar;
        RelativeLayout rl_main;
        CardView card_Activate,card_De_Activate,card_edit,card_delete;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView;
            tv_rent = itemView.findViewById(R.id.tv_rent);
            card_delete = itemView.findViewById(R.id.card_delete);
            card_edit = itemView.findViewById(R.id.card_edit);
            card_De_Activate = itemView.findViewById(R.id.card_De_Activate);
            card_Activate = itemView.findViewById(R.id.card_Activate);
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

