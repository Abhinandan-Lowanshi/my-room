package com.example.myroom.app.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myroom.R;
import com.example.myroom.app.LoginFinal;
import com.example.myroom.app.feedback.ActivityFeedback;
import com.example.myroom.app.loginmanage.ManageSession;
import com.example.myroom.app.loginmanage.UserData;
import com.example.myroom.app.myaccount.MyAccountModel;
import com.example.myroom.app.myaccount.MyAccountModelData;
import com.example.myroom.app.new_ui.AboutUs;
import com.example.myroom.app.new_ui.ChangePasword;
import com.example.myroom.app.new_ui.Contact_Us;
import com.example.myroom.app.new_ui.Edit_Profile;
import com.example.myroom.app.new_ui.PrivacyPolicy;
import com.example.myroom.app.notificationsetting.NotificationSetting;
import com.example.myroom.app.retrofit.ApiClient;
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
                UserData userData = ManageSession.getUserData(getActivity().getApplicationContext());
                myAccountModelData.setUsrFirstName(userData.getFname());
                myAccountModelData.setUsrLastName(userData.getLname());
                myAccountModelData.setUsrPhone(userData.getPhone());
                myAccountModelData.setUsrParmentAdrss(userData.getPermanetadd());
                myAccountModelData.setUsrCurrentAdrss(userData.getCurrentadd());
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
                Button yes = dialog.findViewById(R.id.yes);
                Button no = dialog.findViewById(R.id.no);
                no.setOnClickListener(new View.OnClickListener() {
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
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ManageSession.logOut(getActivity().getApplicationContext())) {
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(), LoginFinal.class);
//                         getActivity(). overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
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


    private void loadProfile(int status) {

        try {
            UserData userData = ManageSession.getUserData(getActivity().getApplicationContext());
            tv_user_name.setText(userData.getFname() + "  " + userData.getLname());
            tv_user_email.setText(userData.getEmail());
            tv_mobile_no.setText("Mobile No :- " + userData.getPhone());
            tv_email.setText("Email Address :- " + userData.getEmail());
            tv_current_address.setText("Current Address :- " + userData.getCurrentadd());
            tv_permanent_address.setText("Permanent Address :- " + userData.getPermanetadd());
        } catch (Exception e) {

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if (appSession.getUserID() != null && appSession.getUserID() != "") {
            loadProfile(1);

        } else {
            Intent intent = new Intent(getActivity(), LoginFinal.class);
            startActivity(intent);
//                 getActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
            getActivity().finishAffinity();
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
        progressDialog.setMessage("Logging out");
        ChipNavigationBar chipNavigationBar;
        chipNavigationBar = (ChipNavigationBar) getActivity().findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.MyAccount, true);
    }
}