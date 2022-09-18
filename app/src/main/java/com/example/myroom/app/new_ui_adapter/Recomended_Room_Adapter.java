package com.example.myroom.app.new_ui_adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myroom.R;
import com.example.myroom.app.fav.FavModel;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.new_ui.Room_Detais_Activity;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recomended_Room_Adapter extends RecyclerView.Adapter<Recomended_Room_Adapter.MyViewHolder> {
    private final static int FADE_DURATION = 300;
    Context context;
    Activity activity;
    String comefrom;
    String name;
    private AppSession appSession;
    ArrayList<RoomDetailsData> data = new ArrayList<>();
//    OnClickInterface onClickInterface
    public Recomended_Room_Adapter(Context context ,Activity activity,ArrayList<RoomDetailsData> data, String comefrom) {
        this.context = context;
        this.activity = activity;
        this.data = data;
        this.comefrom = comefrom;
//        this.onClickInterface = onClickInterface;
        appSession = new AppSession(context);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = comefrom==AppSession.FROM_HOME?LayoutInflater.from(context).inflate(R.layout.recomended_row,parent,false):LayoutInflater.from(context).inflate(R.layout.userpostrow,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Recomended_Room_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        setFadeAnimation(holder.itemView);

        holder.tv_room_type.setText(data.get(position).getRmSize());
//        holder.tv_ratting.setText(data.get(position).getRm());
        holder.tv_location.setText(data.get(position).getRmHouseNo()+", "+data.get(position).getRmColny()+", "+data.get(position).getRmCity());
        holder.tv_rent.setText(context.getString(R.string.price)+data.get(position).getRmRent());
        if(data.get(position).getImages().size()>0)
             name = data.get(position).getImages().get(0).getImgName();


        if(data.get(position).getFavoriteKey().equalsIgnoreCase("false"))
        {
            holder.heart_fill.setVisibility(View.GONE);
            holder.heart_empty.setVisibility(View.VISIBLE);
        }else {
            holder.heart_fill.setVisibility(View.VISIBLE);
            holder.heart_empty.setVisibility(View.GONE);
        }
        if(data.get(position).getImages().size()!=0)
        Glide.with(context).load(data.get(position).getImages().get(0).getImgName()).placeholder(R.drawable.placeholder).into(holder.img_room_image);


        holder.heart_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favMethod("0",position,holder);
            }
        });
        holder.heart_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favMethod("1",position,holder);

            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent intent = new Intent(context, Room_Detais_Activity.class);
         intent.putExtra("room_id",String.valueOf(data.get(position).getRmPkey()));
         intent.putExtra("come" , comefrom);
         context.startActivity(intent);
         activity.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);


     }
 });
    }

    private void favMethod(String i,int pos,Recomended_Room_Adapter.MyViewHolder holder) {


    try {


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",appSession.getUserID());
        jsonObject.addProperty("room_id", String.valueOf(data.get(pos).getRmPkey()));
        jsonObject.addProperty("fav_type", i);
        ApiClient.getClient().addToFav(jsonObject).enqueue(new Callback<FavModel>() {
            @Override
            public void onResponse(Call<FavModel> call, Response<FavModel> response) {

                if(response.isSuccessful())
                {
                   if(response.body().getStatus()==true)
                   {
                       Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                       if(i.equals("1"))
                       {
                           holder.heart_empty.setVisibility(View.INVISIBLE);
                           holder.heart_fill.setVisibility(View.VISIBLE);

                       }else {
                           holder.heart_empty.setVisibility(View.VISIBLE);
                           holder.heart_fill.setVisibility(View.INVISIBLE);
                       }

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

//    public  interface OnClickInterface{
//         public void onClick(int pos);
//      }
    @Override
    public int getItemCount() {

    return data.size()>=10?10:data.size();
      }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.5f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        AppCompatTextView tv_room_type,tv_location,tv_rent,tv_ratting;
        ImageView heart_empty,heart_fill,img_room_image;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView;
            tv_room_type = (AppCompatTextView) itemView.findViewById(R.id.tv_room_type);
            tv_location = (AppCompatTextView) itemView.findViewById(R.id.tv_location);
            tv_rent = (AppCompatTextView) itemView.findViewById(R.id.tv_rent);
            tv_ratting = (AppCompatTextView) itemView.findViewById(R.id.tv_ratting);
            heart_empty = (ImageView) itemView.findViewById(R.id.heart_empty);
            heart_fill = (ImageView) itemView.findViewById(R.id.heart_fill);
            img_room_image = (ImageView) itemView.findViewById(R.id.img_room_image);
        }
    }
    public String addChar(String str, char ch, int position) {
        return str.substring(0, position) + ch + str.substring(position);
    }
}

