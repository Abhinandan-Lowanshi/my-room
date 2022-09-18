package com.example.myroom.app.new_ui_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom.R;
import com.example.myroom.app.new_ui.Room_Detais_Activity;
import com.example.myroom.app.pushnotification.NotificationDataAPI;
import com.example.myroom.app.pushnotification.NotificationModelAPI;
import com.example.myroom.app.time.TimeShow;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import appsession.AppSession;


public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.MyViewHolder> {
        Context context;
        Activity activity;
    TimeShow timeShow;
        private ArrayList<NotificationDataAPI> notificationModelAPI;
        private final static int FADE_DURATION = 300;
        public Notification_Adapter(Context context, Activity activity , ArrayList<NotificationDataAPI> notificationModelAPI) {
            this.context = context;
            this.activity = activity;
            this.notificationModelAPI = notificationModelAPI;
            timeShow  = new TimeShow(context);
        }

        @NonNull
        @NotNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_item_row,parent,false);
            return new MyViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
            setFadeAnimation(holder.itemView);

            holder.tv_title.setText(notificationModelAPI.get(position).getPayload().getRmSize());
            holder.tv_content.setText(notificationModelAPI.get(position).getPayload().getTitle());
            holder.tv_time.setText(timeShow.timeAgo(notificationModelAPI.get(position).getCreatedAt()));
            holder.view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Room_Detais_Activity.class);
                    intent.putExtra("room_id",String.valueOf(notificationModelAPI.get(position).getPayload().getRmPkey()));
                    intent.putExtra("come",AppSession.FROM_HOME);
                    context.startActivity(intent);
                    activity.  overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

                }
            });
        }

        @Override
        public int getItemCount() {
            return notificationModelAPI.size();
        }

        private void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.4f, 1.0f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
        }
        public  class MyViewHolder extends RecyclerView.ViewHolder {
            View view ;
            TextView tv_title ,tv_time ,tv_content;
            public MyViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                view = itemView;
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            }
        }
    }

