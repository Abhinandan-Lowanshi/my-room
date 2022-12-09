package com.myroom.myroom.app.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.Toast;

import com.myroom.myroom.R;
import com.myroom.myroom.app.LoginFinal;
import com.myroom.myroom.app.feedback.ActivityFeedback;
import com.myroom.myroom.app.loginmanage.ManageSession;
import com.myroom.myroom.app.loginmanage.UserData;
import com.myroom.myroom.app.myaccount.MyAccountModel;
import com.myroom.myroom.app.myaccount.MyAccountModelData;
import com.myroom.myroom.app.new_ui.AboutUs;
import com.myroom.myroom.app.new_ui.ChangePasword;
import com.myroom.myroom.app.new_ui.Contact_Us;
import com.myroom.myroom.app.new_ui.Edit_Profile;
import com.myroom.myroom.app.new_ui.PrivacyPolicy;
import com.myroom.myroom.app.notificationsetting.NotificationSetting;
import com.myroom.myroom.app.retrofit.ApiClient;
import com.google.gson.JsonObject;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import appsession.AppSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewMyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewMyAccountFragment extends Fragment {

    private ProgressDialog progressDialog;
    private AppSession appSession;
    private MyAccountModelData myAccountModelData;
    private AppCompatTextView tv_user_name, tv_user_email, tv_mobile_no, tv_current_address, tv_email, tv_permanent_address;
    private CardView card_password, card_contact_us, card_privacy, card_about_us, card_logout, card_edit_profile, card_feedback, card_notification_setting;
    private Switch switch_notification;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewMyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewMyAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewMyAccountFragment newInstance(String param1, String param2) {
        NewMyAccountFragment fragment = new NewMyAccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_my_account, container, false);
        initview(view);
        loadProfile();

        card_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasword.class);
                startActivity(intent);
//               getActivity(). overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                UserData userData = ManageSession.getUserData(getActivity().getApplicationContext());
//                myAccountModelData.setUsrFirstName(userData.getFname());
//                myAccountModelData.setUsrLastName(userData.getLname());
//                myAccountModelData.setUsrPhone(userData.getPhone());
//                myAccountModelData.setUsrParmentAdrss(userData.getPermanetadd());
//                myAccountModelData.setUsrCurrentAdrss(userData.getCurrentadd());
                if (myAccountModelData != null) {
                    Intent intent = new Intent(getActivity(), Edit_Profile.class);
                    intent.putExtra(appSession.USER_DATA, myAccountModelData);
                    startActivity(intent);
//                    getActivity(). overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                } else {

                }


            }
        });
        card_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Contact_Us.class);
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PrivacyPolicy.class);
                startActivity(intent);
//               getActivity(). overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutUs.class);
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityFeedback.class);
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });

        card_notification_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotificationSetting.class);
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);

            }
        });
        card_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.logout_dialogue);
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                //  dialog.getWindow().setFlags(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                AppCompatImageView img_close = dialog.findViewById(R.id.img_close);
                CardView card_logout = dialog.findViewById(R.id.card_logout);
                CardView card_Cancel = dialog.findViewById(R.id.card_Cancel);
                card_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                card_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ManageSession.logOut(getActivity().getApplicationContext())) {
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(), LoginFinal.class);
                            startActivity(intent);
                            getActivity().finishAffinity();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong,try again later.", Toast.LENGTH_LONG);
                        }


                        ;

                    }
                });


                dialog.show();


            }
        });


        return view;
    }


    private void loadProfile() {


        if (appSession.getUserID() != null && appSession.getUserID() != "") {
            progressDialog.show();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", new AppSession(getActivity().getApplicationContext()).getUserID());
            ApiClient.getClient().myAccountDetails(jsonObject).enqueue(new Callback<MyAccountModel>() {
                @Override
                public void onResponse(Call<MyAccountModel> call, Response<MyAccountModel> response) {
//                        mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        if (response.body().getStatus() == true) {

                            myAccountModelData = response.body().getData();

                            tv_user_name.setText(myAccountModelData.getUsrFirstName()+"  "+myAccountModelData.getUsrLastName());
                            tv_user_email.setText(myAccountModelData.getUsrEmail());
                            tv_mobile_no.setText("Mobile No :- "+myAccountModelData.getUsrPhone());
                            tv_email.setText("Email Address :- "+myAccountModelData.getUsrEmail());
                            tv_current_address.setText("Current Address :- "+myAccountModelData.getUsrCurrentAdrss());
                            tv_permanent_address.setText("Permanent Address :- "+myAccountModelData.getUsrParmentAdrss());


                        } else
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<MyAccountModel> call, Throwable t) {
//                        mSwipeRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                }
            });
        }else{
                     progressDialog.dismiss();
//                mSwipeRefreshLayout.setRefreshing(false);
            ManageSession.logOut(getActivity().getApplicationContext());
            Intent intent = new Intent(getActivity().getApplicationContext(), LoginFinal.class);
            startActivity(intent);
//               overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            getActivity().finishAffinity();


        }




}





    @Override
    public void onResume() {
        super.onResume();
            if( appSession.getIsProfileUpdated()=="1") {
                progressDialog.setMessage("Loading profile");
                progressDialog.show();
                loadProfile();
            }
    }

    private void initview(View view) {

        card_password = (CardView) view.findViewById(R.id.card_password);
        myAccountModelData = new MyAccountModelData();
        card_feedback = (CardView) view.findViewById(R.id.card_feedback);
        card_notification_setting = (CardView) view.findViewById(R.id.card_notification_setting);
        card_contact_us = (CardView) view.findViewById(R.id.card_contact_us);
        card_privacy = (CardView) view.findViewById(R.id.card_privacy);
        card_edit_profile = (CardView) view.findViewById(R.id.card_edit_profile);
        card_about_us = (CardView) view.findViewById(R.id.card_about_us);
        card_logout = (CardView) view.findViewById(R.id.card_logout);
        tv_user_name = (AppCompatTextView) view.findViewById(R.id.tv_user_name);
        tv_email = (AppCompatTextView) view.findViewById(R.id.tv_email);
        tv_user_email = (AppCompatTextView) view.findViewById(R.id.tv_user_email);
        tv_permanent_address = (AppCompatTextView) view.findViewById(R.id.tv_permanent_address);
        tv_mobile_no = (AppCompatTextView) view.findViewById(R.id.tv_mobile_no);
        tv_current_address = (AppCompatTextView) view.findViewById(R.id.tv_current_address);
        appSession = new AppSession(getContext());
        appSession.setIsProfileUpdated("0");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        ChipNavigationBar chipNavigationBar;
        chipNavigationBar = (ChipNavigationBar) getActivity().findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.MyAccount, true);
    }
}