package com.myroom.myroom.app.new_ui_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myroom.myroom.R;

import org.jetbrains.annotations.NotNull;

public class Room_type_adapter extends RecyclerView.Adapter<Room_type_adapter.MyViewHolder> {
    Context context;
    private final static int FADE_DURATION = 300;
    public Room_type_adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_type_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Room_type_adapter.MyViewHolder holder, int position) {
        setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return 100;
    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.5f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
