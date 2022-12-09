package com.myroom.myroom.app.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myroom.myroom.R;

import java.util.ArrayList;

import appsession.AppSession;

public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.MyView> {
    private final static int FADE_DURATION = 300;
    private Context context;
    private  ArrayList<ReviewDataModel> reviewDataModels;
    AppSession appSession ;

    public ReviewAdapter(Context context, ArrayList<ReviewDataModel> reviewDataModels) {
        this.context = context;
        this.reviewDataModels = reviewDataModels;
        appSession  = new AppSession(context);
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.reviwerow
                ,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        setFadeAnimation(holder.itemView);
         holder.tv_review.setText(reviewDataModels.get(position).getReview());
         holder.tv_username.setText(reviewDataModels.get(position).getUserName());
         if(appSession.getUserID().equalsIgnoreCase(reviewDataModels.get(position).userID))
         {
             holder.img_delete_review.setVisibility(View.VISIBLE);
         }else {
             holder.img_delete_review.setVisibility(View.GONE);
         }
           holder.img_delete_review.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

               }
           });
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.5f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return reviewDataModels.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView tv_username ,tv_review;
        private ImageView img_delete_review;
        public MyView(@NonNull View itemView) {
            super(itemView);
            tv_review = (TextView) itemView.findViewById(R.id.tv_review);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            img_delete_review = (ImageView) itemView.findViewById(R.id.img_delete_review);
        }
    }
}
