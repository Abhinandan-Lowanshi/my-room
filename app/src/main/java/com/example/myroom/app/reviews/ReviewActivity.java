package com.example.myroom.app.reviews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.myroom.R;
import com.example.myroom.app.new_ui.Search_Activity;
import com.example.myroom.app.searhelper.RecentAdapter;

import java.util.ArrayList;

import appsession.AppSession;

public class ReviewActivity extends AppCompatActivity {

    private  ReviewAdapter reviewAdapter ;
    private RecyclerView rec ;
    private RecyclerView.LayoutManager layoutManager ;
    private  AppCompatImageView img_back  ,img_addReview;
    private AppSession appSession;
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        initView();
        img_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_addReview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
               showDialogue();
            }
        });
    }

    private void showDialogue() {
        final Dialog dialog = new Dialog(ReviewActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.addriviewdilogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        AppCompatImageView img_close = (AppCompatImageView) dialog.findViewById(R.id.img_close);
        EditText ed_review = (EditText) dialog.findViewById(R.id.ed_review);
        Button submit = (Button) dialog.findViewById(R.id.submit);
        dialog.show();
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                String review = ed_review.getText().toString();
                if(review.isEmpty())
                {
                    ed_review.setError("Review can't be empty");
                }else {
                     ReviewModel reviewModel = appSession.getReview();
                     if(reviewModel!=null)
                     {
                         if(reviewModel.reviewDataModels.size()>0)
                         {
                         ArrayList<ReviewDataModel> reviewDataModels = reviewModel.getReviewDataModels();
                         ReviewDataModel reviewDataModel = new ReviewDataModel(appSession.getUserID() , roomId , review , "21/20.20/2022" , appSession.getFname());
                         reviewDataModels.add(0 , reviewDataModel);
                         appSession.setReview(new ReviewModel(reviewDataModels));
                             reviewAdapter = new ReviewAdapter(ReviewActivity.this , reviewDataModels);
                             rec.setAdapter(reviewAdapter);
                             reviewAdapter.notifyDataSetChanged();
                             dialog.dismiss();

                         }

                     }else {
                         ArrayList<ReviewDataModel> reviewDataModels = new ArrayList<>();
                         ReviewDataModel reviewDataModel = new ReviewDataModel(appSession.getUserID() , roomId , review , "21/20.20/2022" , appSession.getFname());
                         reviewDataModels.add(reviewDataModel);
                         appSession.setReview(new ReviewModel(reviewDataModels));
                         reviewAdapter = new ReviewAdapter(ReviewActivity.this , reviewDataModels);
                         rec.setAdapter(reviewAdapter);
                         reviewAdapter.notifyDataSetChanged();
                         dialog.dismiss();

                     }


                }

                }catch (Exception e)
                {
                    dialog.dismiss();
                    e.printStackTrace();
                }


            }
        });

    }


    private void initView() {
        rec = (RecyclerView) findViewById(R.id.rec);
        layoutManager = new LinearLayoutManager(ReviewActivity.this);
        rec.setLayoutManager(layoutManager);

        img_back = (AppCompatImageView) findViewById(R.id.img_back);
        img_addReview = (AppCompatImageView) findViewById(R.id.img_addReview);
        appSession = new AppSession(ReviewActivity.this);

        try {
            roomId = getIntent().getStringExtra(AppSession.ROOM_ID);
            if (roomId!=null) {
                ReviewModel reviewModel = appSession.getReview();
                if (reviewModel != null) {
                    if (reviewModel.reviewDataModels.size() > 0) {
                        ArrayList<ReviewDataModel> reviewDataModels = new ArrayList<>();
                         reviewDataModels = reviewModel.getReviewDataModels();
                        reviewAdapter = new ReviewAdapter(ReviewActivity.this , reviewDataModels);
                        rec.setAdapter(reviewAdapter);
                    }
                }

            }
        }catch(Exception e)
                {
                    e.printStackTrace();
                }

            }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
}