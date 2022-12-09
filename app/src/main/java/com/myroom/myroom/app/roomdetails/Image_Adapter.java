package com.myroom.myroom.app.roomdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myroom.myroom.R;

import java.util.ArrayList;

import zoom.TouchImageView;

public class Image_Adapter extends RecyclerView.Adapter<Image_Adapter.MyViewHolder> {
   private Context context;

    public Image_Adapter(Context context, ArrayList<RoomDetailsImage> roomDetailsImages) {
        this.context = context;
        this.roomDetailsImages = roomDetailsImages;
    }

    private ArrayList<RoomDetailsImage> roomDetailsImages;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_row_details,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(roomDetailsImages.get(position).getImgName()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return roomDetailsImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TouchImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (TouchImageView) itemView.findViewById(R.id.imViewedImage);
        }
    }
}
