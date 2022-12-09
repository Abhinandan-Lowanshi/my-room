package com.myroom.myroom.app.retrofit;

import com.myroom.myroom.app.addroom.RegisterModel;
import com.myroom.myroom.app.changeassword.ChangePasswordModel;
import com.myroom.myroom.app.changepassword.ChangePasswrodModel;
import com.myroom.myroom.app.deleteroom.DeleteRoomModel;
import com.myroom.myroom.app.editprofile.EditProfileModel;
import com.myroom.myroom.app.editroom.EditRoomModel;
import com.myroom.myroom.app.fav.FavModel;
import com.myroom.myroom.app.forgotpassword.ForgotPasswordModel;
import com.myroom.myroom.app.forgotpassword.OtpVerificationModel;
import com.myroom.myroom.app.home.RoomDetailsModel;
import com.myroom.myroom.app.login.LoginModel;
import com.myroom.myroom.app.myaccount.MyAccountModel;
import com.myroom.myroom.app.mypost.RoomStatusModel;
import com.myroom.myroom.app.notificationsetting.NotificationStatusModel;
import com.myroom.myroom.app.pushnotification.Notification.UpdateNotificationModel;
import com.myroom.myroom.app.pushnotification.NotificationModelAPI;
import com.myroom.myroom.app.roomdetails.RoomDetailsMain;
import com.myroom.myroom.app.signup.SignUpModel;
import com.myroom.myroom.app.signup.emailverification.EmailVericationModel;
import com.myroom.myroom.app.signup.emailverification.VerifiyOtpModel;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestService {

    @POST("register")
    Call<SignUpModel>registerUser(@Body JsonObject jsonObject);

    @POST("login")
    Call<LoginModel>login(@Body JsonObject jsonObject);

    @POST("toFavorite")
    Call<FavModel>addToFav(@Body JsonObject jsonObject);

    @POST("forgetPassword")
    Call<ForgotPasswordModel>forgotpassword(@Body JsonObject jsonObject);

    @POST("updatePassword")
    Call<ChangePasswordModel>updatePassword(@Body JsonObject jsonObject);

     @POST("sendEmailOtp")
    Call<EmailVericationModel>sendEmailOtp(@Body JsonObject jsonObject);

     @POST("verifyEmailotp")
    Call<VerifiyOtpModel>verifyEmailotp(@Body JsonObject jsonObject);

     @POST("getUserNotification")
    Call<NotificationStatusModel>getUserNotification(@Body JsonObject jsonObject);

    @POST("verfyOtp")
    Call<OtpVerificationModel>verifyOtp(@Body JsonObject jsonObject);

    @POST("findRoom")
    Call<RoomDetailsModel>getNearByRedius(@Body JsonObject jsonObject);

    @POST("getNotification")
    Call<NotificationModelAPI>getNotification(@Body JsonObject jsonObject);

    @POST("updateUserNotificationDetails")
    Call<UpdateNotificationModel>updateUserNotificationDetails(@Body JsonObject jsonObject);


    @POST("deleteRoom")
    Call<DeleteRoomModel>deleteRoom(@Body JsonObject jsonObject);

    @POST("toRoomStatus")
    Call<RoomStatusModel>toRoomStatus(@Body JsonObject jsonObject);


     @POST("viewRoomDetails")
    Call<RoomDetailsMain>getRoomDetails(@Body JsonObject jsonObject);

    @POST("myRoomList")
    Call<RoomDetailsModel>getMyUploadedRooms(@Body JsonObject jsonObject);

    @POST("editRoom")
    Call<EditRoomModel>editRoom(@Body JsonObject jsonObject);

    @POST("favoriteList")
    Call<RoomDetailsModel>getFevRooms(@Body JsonObject jsonObject);

     @POST("myAccountDetails")
    Call<MyAccountModel>myAccountDetails(@Body JsonObject jsonObject);

      @POST("editUserProfile")
    Call<EditProfileModel>editUserProfile(@Body JsonObject jsonObject);

       @POST("resetPassword")
    Call<ChangePasswrodModel>changePassword(@Body JsonObject jsonObject);



    @Multipart
    @POST("addRoom")
    Call<RegisterModel>registerRoom(@Part MultipartBody.Part  rm_usr_fkey,
                                    @Part MultipartBody.Part rm_own_Fullname,
                                    @Part MultipartBody.Part rm_own_mble_num,
                                    @Part MultipartBody.Part rm_size,
                                    @Part MultipartBody.Part rm_furnisd_status,
                                    @Part MultipartBody.Part rm_availble,
                                    @Part MultipartBody.Part rm_prking_avblity,
                                    @Part MultipartBody.Part rm_depndecy,
                                    @Part MultipartBody.Part rm_flor,
                                    @Part MultipartBody.Part rm_rent,
                                    @Part MultipartBody.Part rm_house_no,
                                    @Part MultipartBody.Part rm_colny,
                                    @Part MultipartBody.Part rm_city,
                                    @Part MultipartBody.Part rm_state,
                                    @Part MultipartBody.Part rm_latitude,
                                    @Part MultipartBody.Part rm_longitude,
                                    @Part MultipartBody.Part rm_description,
                                    @Part MultipartBody.Part Images[]);

}
