package com.example.myroom.app.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.demo.DemoSearchAdapter;
import com.example.myroom.app.home.RoomDetailsData;
import com.example.myroom.app.home.RoomDetailsModel;
import com.example.myroom.app.new_ui_adapter.NearByRoom_Adapter;
import com.example.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


import java.util.ArrayList;
import java.util.List;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewFavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewFavFragment extends Fragment {
    NearByRoom_Adapter adapter;
    private ProgressDialog progressDialog;
    private AppSession appSession;
    private RecyclerView recyclerView;
    private int i = 0;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    TextView empty_text;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<RoomDetailsData> roomDetailsData;

    public NewFavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewFavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewFavFragment newInstance(String param1, String param2) {
        NewFavFragment fragment = new NewFavFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_fav, container, false);
          initview(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view. findViewById(R.id.container);
        mSwipeRefreshLayout.setColorScheme(R.color.red,
                R.color.card_green, R.color.quantum_orange, R.color.purple_200);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyFavRooms();
            }
        });
          getMyFavRooms();
         return view;
    }

    private void getMyFavRooms() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("user_id",appSession.getUserID());

        progressDialog.show();

        ApiClient.getClient().getFevRooms(jsonObject).enqueue(new Callback<RoomDetailsModel>() {
            @Override
            public void onResponse(Call<RoomDetailsModel> call, Response<RoomDetailsModel> response) {


                try {
                    mSwipeRefreshLayout.setRefreshing(false);


                    if (response.isSuccessful()) {


                        progressDialog.dismiss();
                        RoomDetailsModel roomDetailsModel = response.body();

                        if (roomDetailsModel.getStatus() == true) {

                            if(roomDetailsModel.getData().size()!=0)
                            {
                                recyclerView.setVisibility(View.VISIBLE);
                                empty_text.setVisibility(View.GONE);
                                roomDetailsData = roomDetailsModel.getData();
                                DemoSearchAdapter adapter = new DemoSearchAdapter(getContext(),getActivity(),roomDetailsData);
                                recyclerView.setAdapter(adapter);

                            }else {
                                recyclerView.setVisibility(View.GONE);
                                empty_text.setVisibility(View.VISIBLE);
                               Toast.makeText(getContext(), "Room list not found", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            recyclerView.setVisibility(View.GONE);
                            empty_text.setVisibility(View.VISIBLE);
                           Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        recyclerView.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        empty_text.setVisibility(View.VISIBLE);
                       Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e)
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    empty_text.setVisibility(View.VISIBLE);
//                   Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RoomDetailsModel> call, Throwable t) {
//                Toast.makeText(getActivity().getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                empty_text.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                recyclerView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void initview(View view) {
        recyclerView = (RecyclerView)view.findViewById(R.id.likeRecycler);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("searching....");
        recyclerView.setItemViewCacheSize(20);
        empty_text = (TextView) view.findViewById(R.id.empty_text);
        recyclerView.setDrawingCacheEnabled(true);
        appSession  =new AppSession(getContext());
        ChipNavigationBar chipNavigationBar;
        chipNavigationBar = (ChipNavigationBar) getActivity().findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.favourite,true);
        //likeNotshow.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

    }
}