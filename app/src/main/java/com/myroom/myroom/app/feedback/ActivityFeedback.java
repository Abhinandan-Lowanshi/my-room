package com.myroom.myroom.app.feedback;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.myroom.myroom.R;
import com.myroom.myroom.app.new_ui.Notification_Activity;

public class ActivityFeedback extends AppCompatActivity {

    private  AppCompatImageView img_back ,img_attachement;
    private  AppCompatImageView img_notification;
    private TextView txt_attachement;
    private EditText ed_message;
    private Button submit;
    private ActivityResultLauncher<String> mGetContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();


//        mGetContent = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(), new ActivityResultCallback<List<Uri>>() {
//            @Override
//            public void onActivityResult(List<Uri> result) {
//
//            }
//        });
//         mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//             @Override
//             public void onActivityResult(Uri result) {
//
//             }
//         });

        img_attachement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//             mGetContent.launch("video/*");
                showDialogue();
            }


        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ActivityFeedback.this, Notification_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            }
        });
    }

    private void openSomeActivityForResult() {
    }

    private void initView() {
        img_back = (AppCompatImageView)findViewById(R.id.img_back);
        img_attachement = (AppCompatImageView)findViewById(R.id.img_attachement);
        img_notification = (AppCompatImageView) findViewById(R.id.img_notification);
        ed_message =  (EditText) findViewById(R.id.ed_message);
        txt_attachement =  (TextView) findViewById(R.id.txt_attachement);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

    }
    private void showDialogue()
    {
        final Dialog dialog = new Dialog(ActivityFeedback.this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.bottomdialogue);
      dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//          dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        ImageView img_image = dialog.findViewById(R.id.img_image);
        ImageView img_video = dialog.findViewById(R.id.img_video);

        img_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
                dialog.dismiss();
            }
        });
        img_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("video/*");
                dialog.dismiss();
            }
        });



    }
}