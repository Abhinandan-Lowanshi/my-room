package com.myroom.myroom.app.new_ui_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myroom.myroom.R;
import com.myroom.myroom.app.roomdetails.RoomDetailsImage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Image_Show_Adapter extends RecyclerView.Adapter<Image_Show_Adapter.MyViewHolder> {
    Context context;
    ArrayList<RoomDetailsImage> roomDetailsImages ;
    UpdateImageInterafce updateImageInterafce;
    int selectedPosition = -1;
    int selected = 5;


    public Image_Show_Adapter(Context context, ArrayList<RoomDetailsImage> roomDetailsImages, UpdateImageInterafce updateImageInterafce) {
        this.context = context;
        this.roomDetailsImages = roomDetailsImages;
        this.updateImageInterafce = updateImageInterafce;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_image_row,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull Image_Show_Adapter.MyViewHolder holder, int position) {
        if(roomDetailsImages.size()>0)
        {
            Glide.with(context).load(roomDetailsImages.get(position).getImgName()).into(holder.room_image);

        }
        if(selected==5)
        {
            holder.card_main.setBackgroundColor(Color.GREEN);
            selected=0;

        }else {
            if(selectedPosition == position)
                holder.card_main.setBackgroundColor(Color.GREEN); //selected
            else
                holder.card_main.setBackgroundColor(Color.WHITE); //not selected
        }

//        if(position==0)
//        {
//             holder.card_main.setCardBackgroundColor(Color.GREEN);
//        }
//        holder.card_main.setCardBackgroundColor(Color.WHITE);

      holder.room_image.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              updateImageInterafce.UpdateImage(position);


              if(selectedPosition == position)
              {
                  selectedPosition = -1;
                  notifyDataSetChanged();
                  return;
              }

              selectedPosition = position;
              notifyDataSetChanged();
//              for(int i =0 ;i<roomDetailsImages.size();i++)
//              {
//                   if(i==position){
//
//                   }
//
//              }
             }
      });
    }

    @Override
    public int getItemCount() {
        return roomDetailsImages.size();
    }
    public interface UpdateImageInterafce
    {
         public void UpdateImage(int pos);
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tv_count;
        ImageView room_image;
        CardView card_main;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
//            tv_count = itemView.findViewById(R.id.tv_count);
            room_image = itemView.findViewById(R.id.room_image);
            card_main = itemView.findViewById(R.id.card_main);
        }
    }
}

