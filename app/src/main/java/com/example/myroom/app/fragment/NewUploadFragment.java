package com.example.myroom.app.fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.addroom.RegisterModel;
import com.example.myroom.app.demo.PicHolder_final;
import com.example.myroom.app.new_ui.Edit_Profile;
import com.example.myroom.app.new_ui.TakeLocationByGoogleMap;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Adapter.ImgaeAdapter;
import Adapter.SupportValidation;
import appsession.AppSession;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewUploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewUploadFragment extends Fragment implements ImgaeAdapter.click {

    AppCompatImageView img_notification;
    AppCompatImageView img_back;
    RecyclerView imageRec;
    ArrayList<Uri> imageArray = new ArrayList<>();
    double latitude, longitude;
    private int check =0;
    private int LC;
    ImgaeAdapter imgaeAdapter;
    AppSession appSession ;
    private String size1,furnised,available,parking_available,indenpence;
    private ImageView m1;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog,pro2;
    private Button submit,upload;
    private Spinner sp_size_of_room,spiiner_available,sp_Furnished_Status,sp_parking,sp_independenc;
    private EditText rent,houseNo,colony,city,ed_onwhichflor,ed_name,ed_state,ed_available,ed_mobiele,ed_description;
    TextView ed_size;
    AppCompatEditText tv_full_address;
    private ArrayList<Uri> imageList = new ArrayList<Uri>();
    private ArrayList<PicHolder_final> image_caption_list = new ArrayList<PicHolder_final>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewUploadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewUploadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewUploadFragment newInstance(String param1, String param2) {
        NewUploadFragment fragment = new NewUploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_upload, container, false);
        initView(view);
        Places.initialize(getContext(),"REDACTED");


        appSession = new AppSession(getContext());

        tv_full_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(getActivity(), TakeLocationByGoogleMap.class);
                startActivityForResult(intent,78);
                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);



//                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);
//                Intent intent = new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.FULLSCREEN, fields)
//                        .build(Register_House_activity.this);
//                startActivityForResult(intent, 100);
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissions(new String[]{READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        } else {
            Toast.makeText(getContext().getApplicationContext(), "Permission Deny", Toast.LENGTH_LONG).show();

        }
//        img_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//               getActivity(). overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
//
//            }
//        });
//        img_notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getActivity(), Notification_Activity.class);
//                startActivity(intent);
//               getActivity().  overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
//            }
//        });



        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (getActivity().checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
                    } else {
                        requestPermissions(new String[]{READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    }
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Permission Deny", Toast.LENGTH_LONG).show();

                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                try {


                    MultipartBody.Part[] Images = new MultipartBody.Part[image_caption_list
                            .size()];

                    for (int index = 0; index < image_caption_list.size(); index++) {
                        File file = new File(getPathFromUri(getContext(), image_caption_list.get(index).getUri()));
                        RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),
                                file);
                        Images[index] = MultipartBody.Part.createFormData("Images",
                                file.getName(),
                                surveyBody);
                    }
                    String name1 = ed_name.getText().toString();
                    String mobile1 = ed_mobiele.getText().toString();
                    String flor = ed_onwhichflor.getText().toString();
                    String rent1 = rent.getText().toString();
                    String houseno = houseNo.getText().toString();
                    String colony1 = colony.getText().toString();
                    String city1 = city.getText().toString();
                    String state = ed_state.getText().toString();
                    String fullAdrress = tv_full_address.getText().toString();
                    String description = ed_description.getText().toString();


                    if(name1.isEmpty())
                    {
                        ed_name.setError("Name can't be empty");
                        ed_name.setFocusable(true);
                        ed_name.setFocusableInTouchMode(true);
                        ed_name.requestFocus();
                    } else if(mobile1.isEmpty())
                    {
                        ed_mobiele.setError("Mobile can't be empty");
                        ed_mobiele.setFocusable(true);
                        ed_mobiele.setFocusableInTouchMode(true);
                        ed_mobiele.requestFocus();
                    } else  if (!SupportValidation.mobileValidation(mobile1))
                    {
                        ed_mobiele.setError("Invalid mobile no");
                        ed_mobiele.setFocusable(true);
                        ed_mobiele.setFocusableInTouchMode(true);
                        ed_mobiele.requestFocus();

                    }else  if (!SupportValidation.mobileValidation(mobile1))
                    {
                        ed_mobiele.setError("Invalid Mobile no" +
                                "");
                        ed_mobiele.setFocusable(true);
                        ed_mobiele.setFocusableInTouchMode(true);
                        ed_mobiele.requestFocus();

                    } else if (flor.isEmpty())
                    {

                        ed_onwhichflor.setError("Flor can't be empty");
                        ed_onwhichflor.setFocusable(true);
                        ed_onwhichflor.setFocusableInTouchMode(true);
                        ed_onwhichflor.requestFocus();
                    }else if (rent1.isEmpty())
                    {

                        rent.setError("Rent can't be empty");
                        rent.setFocusable(true);
                        rent.setFocusableInTouchMode(true);
                        rent.requestFocus();
                    }
                    else if (fullAdrress.isEmpty())
                    {
                        tv_full_address.setError("Full Adrress can't be empty");
                        tv_full_address.setFocusable(true);
                        tv_full_address.setFocusableInTouchMode(true);
                        tv_full_address.requestFocus();
                    }else if (houseno.isEmpty())
                    {

                        houseNo.setError("House No can't be empty");
                        houseNo.setFocusable(true);
                        houseNo.setFocusableInTouchMode(true);
                        houseNo.requestFocus();
                    }else if (colony1.isEmpty())
                    {

                        colony.setError("Colony can't be empty");
                        colony.setFocusable(true);
                        colony.setFocusableInTouchMode(true);
                        colony.requestFocus();
                    }
//                else if (colony1.isEmpty())
//                {
//
//                    colony.setError("Colony can't be empty");
//                    colony.setFocusable(true);
//                    colony.setFocusableInTouchMode(true);
//                    colony.requestFocus();
//                }
                    else if (city1.isEmpty())
                    {

                        city.setError("City can't be empty");
                        city.setFocusable(true);
                        city.setFocusableInTouchMode(true);
                        city.requestFocus();
                    }else if (state.isEmpty())
                    {
                        ed_state.setError("State can't be empty");
                        ed_state.setFocusable(true);
                        ed_state.setFocusableInTouchMode(true);
                        ed_state.requestFocus();
                    }else if (description.isEmpty())
                    {
                        ed_description.setError("State can't be empty");
                        ed_description.setFocusable(true);
                        ed_description.setFocusableInTouchMode(true);
                        ed_description.requestFocus();
                    }else if(!(Images.length>2))
                    {
                        Toast.makeText(getActivity(), "Select more than two images", Toast.LENGTH_SHORT).show();
                    } else {

                        progressDialog.show();
                        // Toast.makeText(Register_House_activity.this, "Done", Toast.LENGTH_SHORT).show();
                        MultipartBody.Part rm_usr_fkey = MultipartBody.Part.createFormData("rm_usr_fkey", appSession.getUserID());
                        MultipartBody.Part rm_own_Fullname = MultipartBody.Part.createFormData("rm_own_Fullname", String.valueOf(name1));
                        MultipartBody.Part rm_own_mble_num = MultipartBody.Part.createFormData("rm_own_mble_num", String.valueOf(mobile1));
                        MultipartBody.Part rm_size = MultipartBody.Part.createFormData("rm_size", String.valueOf(size1));
                        MultipartBody.Part rm_furnisd_status = MultipartBody.Part.createFormData("rm_furnisd_status", String.valueOf(furnised));
                        MultipartBody.Part rm_availble = MultipartBody.Part.createFormData("rm_availble", String.valueOf(available));
                        MultipartBody.Part rm_prking_avblity = MultipartBody.Part.createFormData("rm_prking_avblity", String.valueOf(parking_available));
                        MultipartBody.Part rm_depndecy = MultipartBody.Part.createFormData("rm_depndecy", String.valueOf(indenpence));
                        MultipartBody.Part rm_flor = MultipartBody.Part.createFormData("rm_flor", String.valueOf(flor));
                        MultipartBody.Part rm_rent = MultipartBody.Part.createFormData("rm_rent", String.valueOf(rent1));
                        MultipartBody.Part rm_house_no = MultipartBody.Part.createFormData("rm_house_no", String.valueOf(houseno));
                        MultipartBody.Part rm_colny = MultipartBody.Part.createFormData("rm_colny", String.valueOf(colony1));
                        MultipartBody.Part rm_city = MultipartBody.Part.createFormData("rm_city", String.valueOf(city1));
                        MultipartBody.Part rm_state = MultipartBody.Part.createFormData("rm_state", String.valueOf(state));
                        MultipartBody.Part rm_description = MultipartBody.Part.createFormData("rm_description", String.valueOf(description));
                        MultipartBody.Part rm_latitude = MultipartBody.Part.createFormData("rm_latitude", String.valueOf(latitude));
                        MultipartBody.Part rm_longitude = MultipartBody.Part.createFormData("rm_longitude", String.valueOf(longitude
                        ));


                        try {


                            ApiClient.getClient().registerRoom(rm_usr_fkey, rm_own_Fullname, rm_own_mble_num, rm_size, rm_furnisd_status, rm_availble, rm_prking_avblity
                                            , rm_depndecy, rm_flor, rm_rent, rm_house_no, rm_colny, rm_city, rm_state, rm_latitude, rm_longitude,rm_description, Images)
                                    .enqueue(new Callback<RegisterModel>() {
                                        @Override
                                        public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {


                                            if (response.isSuccessful()) {
                                                progressDialog.dismiss();
                                                if (response.body().getStatus() == true) {


                                                    transactFragment(new NewMyPostFragment() ,true);
                                                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();



                                                } else
                                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                                            } else
                                                progressDialog.dismiss();
                                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<RegisterModel> call, Throwable t) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                                        }
                                    });
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            Log.d("TAG", "Exception on api: " + e.getLocalizedMessage());
                        }
                    }


                }catch (Exception e)
                {
                    progressDialog.dismiss();
                    Log.d("TAG", "Exception in main: "+e.getLocalizedMessage()) ;
                }


            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {


                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(int i = 0; i < count; i++) {
                        if(i<9&&image_caption_list.size()<9){
                            PicHolder_final ob = new PicHolder_final();
                            ob.setUri(data.getClipData().getItemAt(i).getUri());
                            image_caption_list.add(ob);
                        }else {
                            Toast.makeText(getActivity().getApplicationContext(),"Cant select more than 8 images",Toast.LENGTH_LONG);
                        }
                    }

                    imgaeAdapter = new ImgaeAdapter(image_caption_list,NewUploadFragment.this);
                    imageRec.setAdapter(imgaeAdapter);
                    imgaeAdapter.notifyDataSetChanged();
                }
            }
        } else
        if(requestCode==100)
        {
            Place place = Autocomplete.getPlaceFromIntent(data);

            double sender_lat = place.getLatLng().latitude;
            double  sender_long = place.getLatLng().longitude;
            Log.d("TAG", "onActivityResult: "+sender_lat+"  "+sender_long);

            ed_name.setText(place.getAddress());
        }
        if(requestCode==78)
        {
            if(appSession.getlat().equalsIgnoreCase("")||appSession.getlat()==null||appSession.getlon().equalsIgnoreCase("")||appSession.getlon()==null||appSession.getlat().isEmpty()||appSession.getlon().isEmpty())
            {

            }else {
                getadressByLatLon();
            }

        }
    }

    private void getadressByLatLon() {
        latitude =Double.valueOf(appSession.getlat());
        longitude = Double.valueOf(appSession.getlon());
        Geocoder geocoder = new Geocoder(getContext(),
                Locale.getDefault());
        //Inilize address list
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    latitude, longitude, 1

            );
            tv_full_address.setText(addresses.get(0).getAddressLine(0));
            tv_full_address.setFocusable(true);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "onComplete: ");
        }

    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_LONG).show();
            }
        }
    }



    public void show(int position){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.edit_caption_dialogue);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        AppCompatEditText edit_text_caption_dialuge = dialog.findViewById(R.id.edit_text_caption_dialuge);
        Button submit = dialog.findViewById(R.id.submit);

        edit_text_caption_dialuge.setText(image_caption_list.get(position).getCaption());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_caption_list.get(position).setCaption(edit_text_caption_dialuge.getText().toString());
                imgaeAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void initView(View view)
    {

        ChipNavigationBar chipNavigationBar;
        chipNavigationBar = (ChipNavigationBar) getActivity().findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.forever_rent,true);
        submit = (Button)view.findViewById(R.id.submit);
        // img_notification = (AppCompatImageView) view.findViewById(R.id.img_notification);

        // submit.setEnabled(false);

        linearLayout = (LinearLayout)view.findViewById(R.id.image_layout);
        upload =(Button) view.findViewById(R.id.upload);
        m1 = (ImageView)view.findViewById(R.id.m1);
        sp_size_of_room = (Spinner)view. findViewById(R.id.sp_size_of_room);
        sp_Furnished_Status = (Spinner) view.findViewById(R.id.sp_Furnished_Status);
        sp_independenc = (Spinner) view.findViewById(R.id.sp_independenc);
        sp_parking = (Spinner)view. findViewById(R.id.sp_parking);
        spiiner_available = (Spinner) view.findViewById(R.id.spiiner_available);
        // img_back = (AppCompatImageView)view.findViewById(R.id.img_back);
        imageRec = (RecyclerView) view.findViewById(R.id.imageRec);
        imageRec.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        ed_name = (EditText)view. findViewById(R.id.ed_naam);
        ed_description = (EditText)view. findViewById(R.id.ed_description);
        ed_mobiele= (EditText)view.findViewById(R.id.ed_no);
        ed_available = (EditText)view.findViewById(R.id.ed_available);
        ed_onwhichflor = (EditText)view.findViewById(R.id.ed_onwhichflor);
        ed_size = (TextView)view.findViewById(R.id.ed_size);
        tv_full_address = (AppCompatEditText)view.findViewById(R.id.tv_full_address);
        rent = (EditText)view.findViewById(R.id.ed_rent1);
        houseNo = (EditText)view.findViewById(R.id.ed_houseNO1);
        colony = (EditText)view.findViewById(R.id.ed_colony1);
        city = (EditText)view.findViewById(R.id.ed_city1);
        ed_state = (EditText)view.findViewById(R.id.ed_state);
        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Uploading........");
        progressDialog.setCancelable(false);


        String name_Ftemp ="" ,name_Ltemp ="" , mobile_temp ="";
        name_Ftemp = new AppSession(getActivity().getApplicationContext()).getFname();
        name_Ltemp = new AppSession(getActivity().getApplicationContext()).getLname();
        mobile_temp = new AppSession(getActivity().getApplicationContext()).getMobile();

         if(name_Ftemp!=""&&name_Ltemp!="")
         {
              ed_name.setText(name_Ftemp+" "+ name_Ltemp);
         }
         if(mobile_temp!="")
         {
             ed_mobiele.setText(mobile_temp);
             ed_mobiele.setEnabled(false);

         }
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Single Room");
        spinnerArray.add("1RK");
        spinnerArray.add("1BHK");
        spinnerArray.add("2BHK");
        spinnerArray.add("3BHK");


        List<String> spinner_array_available =  new ArrayList<String>();
        spinner_array_available.add("Only girls");
        spinner_array_available.add("Only boys");
        spinner_array_available.add("Only family");
        spinner_array_available.add("Family and girls");
        spinner_array_available.add("All");
        spinner_array_available.add("Only for Student");
        spinner_array_available.add("Only for Student( Only Boys )");
        spinner_array_available.add("Only for Student ( Only Girls )");

        List<String> sp_Furnished =  new ArrayList<String>();
        sp_Furnished.add("Furnished");
        sp_Furnished.add("Unfurnished");
        sp_Furnished.add("Semi-Furnished");


        List<String> SP_Availability =  new ArrayList<String>();
        SP_Availability.add("Yes");
        SP_Availability.add("No");


        List<String> SP_Independent =  new ArrayList<String>();
        SP_Independent.add("Yes");
        SP_Independent.add("No");


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, SP_Availability);
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



        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, SP_Independent);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_independenc.setAdapter(adapter4);
        sp_independenc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // ed_size.setText(spinnerArray.get(i));

                indenpence = SP_Independent.get(i);
                Log.d("TAG", "onItemSelected: "+SP_Availability.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sp_Furnished);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
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


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinner_array_available);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }






    @Override
    public void updateUI(int pos) {
        image_caption_list.remove(pos);
        imgaeAdapter.notifyDataSetChanged();
        Log.d("TAG", "updateUI: "+pos);
    }



    @Override
    public void editCaption(int pos) {
        show(pos);
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    public void transactFragment(Fragment fragment, boolean reload) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (reload) {
            getActivity(). getSupportFragmentManager().popBackStack();
        }
//        transaction.replace(R.id.main_activity_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}