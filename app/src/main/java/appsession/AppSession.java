package appsession;

import android.annotation.SuppressLint;
import android.content.Context;

import com.myroom.myroom.BuildConfig;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.text.TextUtils;

import com.myroom.myroom.app.FavModelTmp;
import com.myroom.myroom.app.customlatModel.CustomeLatLon;
import com.myroom.myroom.app.reviews.ReviewModel;
import com.myroom.myroom.app.searhelper.SearchListData;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppSession {
    public static final String PACKAGE_NAME = "com.test.test";
    public static final String USER_DATA = "user_data";
    public static final String PATIENT = "2";
    public static final String CURRENT_APPOINTMENT = "1";
    public static final String CANCELED_APPOINTMENT = "2";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    public static String Patient_IMG_URL = BuildConfig.IMAGE_PATH + "Icare/uploads/patients_profiles/";
    public static String DOCTOR_IMG_URL = BuildConfig.IMAGE_PATH + "Icare/uploads/doctor_profiles/";
    public static String ABOUT_IMG_URL = BuildConfig.IMAGE_PATH + "Icare/uploads/about_images/";
    public static String HELP_IMG_URL = BuildConfig.IMAGE_PATH + "Icare/uploads/help_images/";
    public static String BANNER_IMG_URL = BuildConfig.IMAGE_PATH + "Icare/uploads/Banner/";
    public static String ROOM_ID = "room_id";
    public static String USER_NAME = "user_name";
    public static String PHONE = "phone";
    public static String ROOM_SIZE = "room_size";
    public static String F_STATUS = "f_status";
    public static String USER_ID_ROOM_OWNER = "user_id_room_owner";
    public static String AVAILABLE = "available";
    public static String PARKING = "parking";
    public static String FROM_HOME = "fromhome";
    public static String FROM_PROFILE = "fromeprofile";
    public static String INDENDENT = "independent";
    public static String FLOR = "flor";
    public static String RENT = "rent";
    public static String CUSTOM = "custom";
    public static String CURRENT = "current";

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public AppSession(Context context) {
        pref = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

     public boolean clearUserData()
     {
        return pref.edit().clear().commit();
     }

    public static boolean isValidEmail(String target) {
        boolean flag;
        if (TextUtils.isEmpty(target)) {
            return true;
        } else {
            flag = emailValidator(target);
            if (flag) {
                // return flag;
            }
            return flag;
        }
        // return (!TextUtils.isEmpty || Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public static String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
//        String inputPattern = "yyyy-MM-dd";
//        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public boolean getIsCustomeLocation() { return pref.getBoolean("isCustomLocation", false); }
    public void setIsCustomeLocation(boolean data) {
        editor.putBoolean("isCustomLocation", data);
        editor.commit();
    }

    public SearchListData getStoredRoom() {
        Gson gson = new Gson();
        String rowData = pref.getString("StoredRoom", "");
        SearchListData data = gson.fromJson(rowData, SearchListData.class);
        return data; }

    public void setStoredRoom(SearchListData searchListData) {
        Gson gson = new Gson();
        String json = gson.toJson(searchListData);
        editor.putString("StoredRoom", json);
        editor.commit();
    }


    public FavModelTmp getFavModel() {
        Gson gson = new Gson();
        String rowData = pref.getString("favModel", "");
        FavModelTmp data = gson.fromJson(rowData, FavModelTmp.class);
        return data; }

    public void setFavModel(FavModelTmp favModel) {
        Gson gson = new Gson();
        String json = gson.toJson(favModel);
        editor.putString("favModel", json);
        editor.commit();
    }

    public ReviewModel getReview() {
        Gson gson = new Gson();
        String rowData = pref.getString("tempReview", "");
        ReviewModel data = gson.fromJson(rowData, ReviewModel.class);
        return data; }

    public void setReview(ReviewModel reviewModel) {
        Gson gson = new Gson();
        String json = gson.toJson(reviewModel);
        editor.putString("tempReview", json);
        editor.commit();
    }

    public CustomeLatLon getCustomLatLon() {
        Gson gson = new Gson();
        String rowData = pref.getString("customlat", "");
        CustomeLatLon data = gson.fromJson(rowData, CustomeLatLon.class);
        return data; }


    public void setCusomeLatlon(CustomeLatLon customeLatLon) {
        Gson gson = new Gson();
        String json = gson.toJson(customeLatLon);
        editor.putString("customlat", json);
        editor.commit();
    }

    public String getIsFromEdit() { return pref.getString("isFromEdit", ""); }
    public void setIsFromEdit(String loginas) {
        editor.putString("isFromEdit", loginas);
        editor.commit();
    }

    public String getIsLogin() { return pref.getString("isLogin", "0"); }
    public void setIsLogin(String isLogin) {
        editor.putString("isLogin", isLogin);
        editor.commit();
    }

    public String getUserID() { return pref.getString("UserID", "0"); }
    public void setUserID(String UserID) {
        editor.putString("UserID", UserID);
        editor.commit();
    }


     public String getCurrentPassword() { return pref.getString("currentpassword", ""); }
    public void setCurrentPassword(String currentpassword) {
        editor.putString("currentpassword", currentpassword);
        editor.commit();
    }

  public String getlat() { return pref.getString("lat", "0"); }
    public void setlat(String lat) {
        editor.putString("lat", lat);
        editor.commit();
    }
     public String getlon() { return pref.getString("lon", "0"); }
    public void setlon(String lon) {
        editor.putString("lon", lon);
        editor.commit();
    }

    public String getMainlat() { return pref.getString("Mainlat", "0"); }
    public void setMainlat(String lat) {
        editor.putString("Mainlat", lat);
        editor.commit();
    }

    public String getSignalStrenth() { return pref.getString("signalStrenth", "0"); }
    public void setSignalStrenth(String signalStrenth) {
        editor.putString("signalStrenth", signalStrenth);
        editor.commit();
    }

    public String getMainlon() { return pref.getString("Mainlon", "0"); }
    public void setMainlon(String lon) {
        editor.putString("Mainlon", lon);
        editor.commit();
    }
    public String getCityCurrent() { return pref.getString("cityCurrent", "0"); }
    public void setCityCurrent(String city) {
        editor.putString("cityCurrent", city);
        editor.commit();
    }


    public String getFname() { return pref.getString("Fname", ""); }
    public void setFname(String Fname) {
        editor.putString("Fname", Fname);
        editor.commit();
    }
     public String getIsProfileUpdated() { return pref.getString("getIsProfileUpdated", ""); }
    public void setIsProfileUpdated(String getIsProfileUpdated) {
        editor.putString("getIsProfileUpdated", getIsProfileUpdated);
        editor.commit();
    }
    public String getLname() { return pref.getString("Lname", ""); }
    public void setLname(String Lname) {
        editor.putString("Lname", Lname);
        editor.commit();
    }


     public String getToken() { return pref.getString("token", ""); }
    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }
    public String getEmail() { return pref.getString("Email", ""); }
    public void setEmail(String Email) {
        editor.putString("Email", Email);
        editor.commit();
    }


    public String getNotificationStatus() { return pref.getString("notification_st", "true"); }
    public void setNotificationStatus(String notification_tp) {
        editor.putString("notification_st", notification_tp);
        editor.commit();
    }
    public String getNotificationType() { return pref.getString("notification_tp", CURRENT); }
    public void setNotificationType(String notification_tp) {
        editor.putString("notification_tp", notification_tp);
        editor.commit();
    }

    public String getMobile() { return pref.getString("Mobile", ""); }
    public void setMobile(String Mobile) {
        editor.putString("Mobile", Mobile);
        editor.commit();
    }

    public String getPermanentAddress() { return pref.getString("permanentAddress", ""); }
    public void setPermanentAddress(String permanentAddress) {
        editor.putString("permanentAddress", permanentAddress);
        editor.commit();
    }

    public String getPresentAddress() { return pref.getString("PresentAddress", ""); }
    public void setPresentAddress(String permanentAddress) {
        editor.putString("PresentAddress", permanentAddress);
        editor.commit();
    }


    public String getData() { return pref.getString("data", ""); }
    public void setData(String data) {
        editor.putString("data", data);
        editor.commit();
    }


    public String getImage() { return pref.getString("Banner", "0"); }
    public void setImage(String Image) {
        editor.putString("Banner", Image);
        editor.commit();
    }

    public String getState() { return pref.getString("State", ""); }
    public void setState(String State) {
        editor.putString("State", State);
        editor.commit();
    }

    public String getCity() { return pref.getString("City", ""); }
    public void setCity(String City) {
        editor.putString("City", City);
        editor.commit();
    }
    public String getAddress() { return pref.getString("Address", ""); }
    public void setAddress(String Address) {
        editor.putString("Address", Address);
        editor.commit();
    }
    public String getAge() { return pref.getString("Age", ""); }
    public void setAge(String Age) {
        editor.putString("Age", Age);
        editor.commit();
    }

    public String getGender() { return pref.getString("Gender", ""); }
    public void setGender(String Gender) {
        editor.putString("Gender", Gender);
        editor.commit();
    }

    public  void clearAllSharedPreferences(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
}
