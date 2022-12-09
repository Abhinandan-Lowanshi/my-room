package com.myroom.myroom.app.editroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myroom.myroom.R;
import com.myroom.myroom.app.demo.PicHolder_final;
import com.myroom.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import Adapter.ImgaeAdapter;
import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Edit_Room_Activity extends AppCompatActivity {


    AppCompatImageView img_notification;
    AppCompatImageView img_back;
    RecyclerView imageRec;
    ArrayList<Uri> imageArray = new ArrayList<>();
    double latitude, longitude;
    private int check =0;
    private int LC;
    private String   room_id , name ,size1,furnised,available,parking_available,indenpence ,rent1 , flor ,phone;
    private ImageView m1;

    private LinearLayout linearLayout;
    private ProgressDialog progressDialog,pro2;
    private Button submit,upload;
    private Spinner sp_size_of_room,spiiner_available,sp_Furnished_Status,sp_parking,sp_independenc;
    private EditText rent,houseNo,colony,city,ed_onwhichflor,ed_name,ed_state,ed_available,ed_mobiele;
    TextView ed_size;
    AppCompatEditText tv_full_address;
    private ArrayList<Uri> imageList = new ArrayList<Uri>();
    private ArrayList<PicHolder_final> image_caption_list = new ArrayList<PicHolder_final>();
    ArrayList<String> spinnerArray =  new ArrayList<String>();
    ArrayList<String> spinner_array_available =  new ArrayList<String>();
    ArrayList<String> sp_Furnished =  new ArrayList<String>();
    ArrayList<String> SP_Availability =  new ArrayList<String>();
    ArrayList<String> SP_Independent =  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        initView();
        getFromInent();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upDateData();
            }
        });
    }


    public  void upDateData()
    {
        try {
            if(ed_name.getText().toString().isEmpty())
            {
                ed_name.setError("name can't be empty");
                ed_name.setFocusable(true);
                ed_name.setFocusableInTouchMode(true);
                ed_name.requestFocus();
            }else if(ed_mobiele.getText().toString().isEmpty() || ed_mobiele.getText().toString().length()<10)
            {
                ed_mobiele.setError("Invalid mobile no");
                ed_mobiele.setFocusable(true);
                ed_mobiele.setFocusableInTouchMode(true);
                ed_mobiele.requestFocus();
            }else if(ed_onwhichflor.getText().toString().isEmpty())
            {
                ed_onwhichflor.setError("floor can't be empty");
                ed_onwhichflor.setFocusable(true);
                ed_onwhichflor.setFocusableInTouchMode(true);
                ed_onwhichflor.requestFocus();
            }else if(rent.getText().toString().isEmpty())
            {
                rent.setError("rent can't be empty");
                rent.setFocusable(true);
                rent.setFocusableInTouchMode(true);
                rent.requestFocus();

            }else {
                progressDialog.show();
                String name , phone , flor , rent2 ,size_l , f_status_l ,availble_l ,parking_l ,independent_l;
                name= ed_name.getText().toString();
                phone= ed_mobiele.getText().toString();
                flor= ed_onwhichflor.getText().toString();
                rent2= rent.getText().toString();
                size_l = size1;
                f_status_l = furnised;
                availble_l = available;
                parking_l = parking_available;
                independent_l = indenpence;


                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("rm_own_fullname",name);
                jsonObject.addProperty("rm_own_mble_num",phone);
                jsonObject.addProperty("rm_flor",flor);
                jsonObject.addProperty("rm_rent",rent2);
                jsonObject.addProperty("rm_size",size_l);
                jsonObject.addProperty("rm_furnisd_status",f_status_l);
                jsonObject.addProperty("rm_depndecy",independent_l);
                jsonObject.addProperty("rm_prking_avblity",parking_l);
                jsonObject.addProperty("rm_availble",availble_l);

                JsonObject jsonObjectMain = new JsonObject();
                jsonObjectMain.addProperty("room_id",room_id);
                jsonObjectMain.addProperty("data", String.valueOf(jsonObject));

                ApiClient.getClient().editRoom(jsonObjectMain).enqueue(new Callback<EditRoomModel>() {
                    @Override
                    public void onResponse(Call<EditRoomModel> call, Response<EditRoomModel> response) {

                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            if (response.body().getStatus() == true) {
                                new AppSession(Edit_Room_Activity.this).setIsFromEdit("1");
                                onBackPressed();
                                Toast.makeText(Edit_Room_Activity.this, response.body().getMessage(), Toast.LENGTH_LONG);
                            } else
                                Toast.makeText(Edit_Room_Activity.this, "Something went wrong", Toast.LENGTH_LONG);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Edit_Room_Activity.this, "Something went wrong", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onFailure(Call<EditRoomModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(Edit_Room_Activity.this,"Something went wrong",Toast.LENGTH_LONG);
                    }
                });


            }

        }catch (Exception e)
        {
            progressDialog.dismiss();
            e.printStackTrace();
        }

    }


   public void getFromInent()
   {
       Intent intent = getIntent();
       name = intent.getStringExtra(AppSession.USER_NAME);
       room_id = intent.getStringExtra(AppSession.ROOM_ID);
       phone = intent.getStringExtra(AppSession.PHONE);
       size1 = intent.getStringExtra(AppSession.ROOM_SIZE);
       furnised = intent.getStringExtra(AppSession.F_STATUS);
       available= intent.getStringExtra(AppSession.AVAILABLE);
       parking_available= intent.getStringExtra(AppSession.PARKING);
       indenpence = intent.getStringExtra(AppSession.INDENDENT);
       flor = intent.getStringExtra(AppSession.FLOR);
       rent1 = intent.getStringExtra(AppSession.RENT);
       phone = intent.getStringExtra(AppSession.PHONE);

       ed_name.setText(name);
       ed_mobiele.setText(phone);
       ed_onwhichflor.setText(flor);
       rent.setText(rent1);
       ed_onwhichflor.setText(flor);
       selectSpinnerItemByValue(sp_size_of_room,spinnerArray,size1);
       selectSpinnerItemByValue(sp_Furnished_Status,sp_Furnished,furnised);
       selectSpinnerItemByValue(spiiner_available,spinner_array_available,available);
       selectSpinnerItemByValue(sp_parking,SP_Availability,parking_available);
       selectSpinnerItemByValue(sp_independenc,SP_Independent,indenpence);




   }
    private void initView()
    {

        new AppSession(Edit_Room_Activity.this).setIsFromEdit("0");
        img_back =(AppCompatImageView) findViewById(R.id.img_back);
        submit = (Button)findViewById(R.id.submit);
        // img_notification = (AppCompatImageView) view.findViewById(R.id.img_notification);

        // submit.setEnabled(false);


        upload =(Button)findViewById(R.id.upload);

        sp_size_of_room = (Spinner) findViewById(R.id.sp_size_of_room);
        sp_Furnished_Status = (Spinner) findViewById(R.id.sp_Furnished_Status);
        sp_independenc = (Spinner) findViewById(R.id.sp_independenc);
        sp_parking = (Spinner) findViewById(R.id.sp_parking);
        spiiner_available = (Spinner) findViewById(R.id.spiiner_available);
        // img_back = (AppCompatImageView)view.findViewById(R.id.img_back);

        ed_name = (EditText) findViewById(R.id.ed_naam);
        ed_mobiele= (EditText)findViewById(R.id.ed_no);
        ed_available = (EditText)findViewById(R.id.ed_available);
        ed_onwhichflor = (EditText)findViewById(R.id.ed_onwhichflor);
        ed_size = (TextView)findViewById(R.id.ed_size);
        rent = (EditText)findViewById(R.id.ed_rent1);
        progressDialog = new ProgressDialog(Edit_Room_Activity.this);

        progressDialog.setMessage("Uploading........");
        progressDialog.setCancelable(false);

        spinnerArray.add("Single Room");
        spinnerArray.add("1RK");
        spinnerArray.add("1BHK");
        spinnerArray.add("2BHK");
        spinnerArray.add("3BHK");


        spinner_array_available.add("Only girls");
        spinner_array_available.add("Only boys");
        spinner_array_available.add("Only family");
        spinner_array_available.add("Family and girls");
        spinner_array_available.add("All");
        spinner_array_available.add("Only for Student");
        spinner_array_available.add("Only for Student( Only Boys )");
        spinner_array_available.add("Only for Student ( Only Girls )");



        sp_Furnished.add("Furnished");
        sp_Furnished.add("Unfurnished");
        sp_Furnished.add("Semi-Furnished");



        SP_Availability.add("Yes");
        SP_Availability.add("No");



        SP_Independent.add("Yes");
        SP_Independent.add("No");


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Edit_Room_Activity.this, android.R.layout.simple_spinner_item, SP_Availability);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_parking.setAdapter(adapter3);
        sp_parking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                parking_available = SP_Availability.get(i);
                Log.d("TAG", "onItemSelected: "+SP_Availability.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Edit_Room_Activity.this, android.R.layout.simple_spinner_item, SP_Independent);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_independenc.setAdapter(adapter4);
        sp_independenc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));

                indenpence = SP_Independent.get(i);
//                Log.d("TAG", "onItemSelected: "+SP_Availability.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Edit_Room_Activity.this, android.R.layout.simple_spinner_item, sp_Furnished);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Furnished_Status.setAdapter(adapter2);
        sp_Furnished_Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                furnised = sp_Furnished.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Edit_Room_Activity.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_size_of_room.setAdapter(adapter);
        sp_size_of_room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                size1  = spinnerArray.get(i);
                Log.d("TAG", "onItemSelected: "+spinnerArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Edit_Room_Activity.this    , android.R.layout.simple_spinner_item, spinner_array_available);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiiner_available.setAdapter(adapter1);
        spiiner_available.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));
                available = spinner_array_available.get(i);
//                Log.d("TAG", "onItemSelected: "+spinnerArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    public static void selectSpinnerItemByValue(Spinner spnr, ArrayList<String> list ,String data) {

        for (int position = 0; position < list.size(); position++) {
            if(list.get(position).equalsIgnoreCase(data) ) {
                spnr.setSelection(position);
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
    }
}