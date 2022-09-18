package com.example.myroom.app.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.LoginFinal;
import com.example.myroom.app.deleteroom.DeleteRoomModel;
import com.example.myroom.app.demo.DemoSearchAdapter;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.home.RoomDetailsModel;
import com.example.myroom.app.loginmanage.ManageSession;
import com.example.myroom.app.mypost.MyPostAdapter;
import com.example.myroom.app.new_ui_adapter.NearByRoom_Adapter;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewMyPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewMyPostFragment extends Fragment implements MyPostAdapter.Delete {
    RecyclerView rec;
    TextView empty_text;
    private ProgressDialog progressDialog ,deleteDialoge;
    private AppSession appSession;
    MyPostAdapter  adapter;
    NearByRoom_Adapter nearByRoom_adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<RoomDetailsData> roomDetailsData;

    public NewMyPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewMyPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewMyPostFragment newInstance(String param1, String param2) {
        NewMyPostFragment fragment = new NewMyPostFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_my_post, container, false);
        iniView(view);
        getMyUploadedRooms(this::deleletListner);


         return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(new AppSession(getContext()).getIsFromEdit().equalsIgnoreCase("1")) {
            getMyUploadedRooms(this::deleletListner);
            Log.d("getMyUploadedRooms", "onResume: ");
        }
    }

    private void showDialogue(int pos)
     {
         final Dialog dialog = new Dialog(getContext());
         dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
         dialog.setCancelable(true);
         dialog.setContentView(R.layout.delete_layout);
         dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
         dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
         //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
         dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                 WindowManager.LayoutParams.MATCH_PARENT);
         AppCompatImageView img_close = dialog.findViewById(R.id.img_close);
         Button yes = dialog.findViewById(R.id.yes);
         Button no = dialog.findViewById(R.id.no);
         no.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dialog.dismiss();
             }
         });  img_close.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             dialog.dismiss();
         }
     });
         yes.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                dialog.dismiss();
                deleteRoom(pos);

             }
         });


         dialog.show();
     }

    private void deleteRoom(int pos) {

  try {

      deleteDialoge.show();
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("room_id",String.valueOf(roomDetailsData.get(pos).getRmPkey()));
      ApiClient.getClient().deleteRoom(jsonObject).enqueue(new Callback<DeleteRoomModel>() {
          @Override
          public void onResponse(Call<DeleteRoomModel> call, Response<DeleteRoomModel> response) {

              if(response.isSuccessful())
              {
                  deleteDialoge.dismiss();
                  if(response.body().getStatus()==true) {


                      Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                      onResume();
                      removeRoom(roomDetailsData.get(pos).getRmPkey(),roomDetailsData);
                  }else {
                      Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                  }
              }else {
                  deleteDialoge.dismiss();
              }
          }

          @Override
          public void onFailure(Call<DeleteRoomModel> call, Throwable t) {
              deleteDialoge.dismiss();
              Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
          }
      });

  }catch (Exception e)
  {
      deleteDialoge.dismiss();
       e.printStackTrace();
  }

    }

    private void removeRoom(Integer rmPkey, ArrayList<RoomDetailsData> roomDetailsData) {
          for(int i=0 ; i<roomDetailsData.size() ; i++) {
              if (roomDetailsData.get(i).getRmPkey() == rmPkey)
              {
                   roomDetailsData.remove(i);
              }
          }

        adapter.notifyDataSetChanged();
    }

    private void getMyUploadedRooms(MyPostAdapter.Delete delete) {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("user_id",String.valueOf(appSession.getUserID()));
             progressDialog.show();


        ApiClient.getClient().getMyUploadedRooms(jsonObject).enqueue(new Callback<RoomDetailsModel>() {
            @Override
            public void onResponse(Call<RoomDetailsModel> call, Response<RoomDetailsModel> response) {


                try {

//                    MyPostAdapter adapter = new MyPostAdapter(getContext(),getActivity(),null ,delete);
//                    rec.setAdapter(adapter);
//                    rec.scheduleLayoutAnimation();

                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        RoomDetailsModel roomDetailsModel = response.body();

                        if (roomDetailsModel.getStatus() == true) {

                            if(roomDetailsModel.getData().size()!=0)
                            {
                                rec.setVisibility(View.VISIBLE);
                                empty_text.setVisibility(View.GONE);
                                roomDetailsData = roomDetailsModel.getData();
                               adapter = new MyPostAdapter(getContext(),getActivity(),roomDetailsData ,delete);
                                rec.setAdapter(adapter);
                                rec.scheduleLayoutAnimation();

                            }else {
                                rec.setVisibility(View.INVISIBLE);
                                empty_text.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity().getApplicationContext(), "Room list not found", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            rec.setVisibility(View.VISIBLE);
                            empty_text.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        progressDialog.dismiss();
                        empty_text.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity().getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e)
                {
                    progressDialog.dismiss();
                    empty_text.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity().getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RoomDetailsModel> call, Throwable t) {
                empty_text.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
              //  Toast.makeText(getActivity().getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void iniView(View view) {
        rec = (RecyclerView)view.findViewById(R.id.rec);
        empty_text = (TextView) view.findViewById(R.id.empty_text);
        appSession = new AppSession(getActivity());
        ChipNavigationBar chipNavigationBar;
        chipNavigationBar = (ChipNavigationBar) getActivity().findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.Saved,true);
        progressDialog = new ProgressDialog(getContext());
        deleteDialoge = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("searching....");

        deleteDialoge.setCancelable(false);
        deleteDialoge.setMessage("Deleting....");


    }

    @Override
    public void deleletListner(int pos) {
        showDialogue(pos);
    }
}