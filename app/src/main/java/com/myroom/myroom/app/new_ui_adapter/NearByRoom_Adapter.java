package com.myroom.myroom.app.new_ui_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myroom.myroom.R;
import com.myroom.myroom.app.new_ui.Room_Detais_Activity;

import org.jetbrains.annotations.NotNull;

public class NearByRoom_Adapter extends RecyclerView.Adapter<NearByRoom_Adapter.MyViewHolder> {
    Context context;
    Activity activity;
    private final static int FADE_DURATION = 300;
    public NearByRoom_Adapter(Context context,Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nearbylocation_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NearByRoom_Adapter.MyViewHolder holder, int position) {
        setFadeAnimation(holder.itemView);
     holder.view.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View view) {
             Intent intent = new Intent(context, Room_Detais_Activity.class);
             context.startActivity(intent);
//           activity.  overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

         }
     });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.3f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        View view ;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}

