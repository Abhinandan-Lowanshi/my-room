package com.example.myroom.app.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
//    public static String BASE_URL = "https://example.com/api/";
//    public static String CATEGORY_URL = "https://alphawizztest.tk/KiranaBaazaar/uploads/category/";
//    public static String SUB_CATEGORY_URL = "https://alphawizztest.tk/KiranaBaazaar/uploads/subcat/";
//    public static String SUB_CATEGORY_CHILD_URL = "https://alphawizztest.tk/KiranaBaazaar/uploads/childsubcat/";
//    public static String USER_PROFILE_URL = "https://alphawizztest.tk/KiranaBaazaar/uploads/customer_profiles/";
//    public static String VENDOR_PROFILE_URL = "https://alphawizztest.tk/KiranaBaazaar/uploads/profile/";
//    public static String CATEGORY_PRODUCT_URL = "https://alphawizztest.tk/KiranaBaazaar/uploads/";
//    public static String BANNER_URL = "https://alphawizztest.tk/KiranaBaazaar/uploads/sliders/";

  public static String BASE_URL = "https://example.com/api/";
  public static String IMAGE_PATH = "https://example.com/";
   // public static String BASE_URL = "https://example.com/api/";

//    public static String CATEGORY_URL = "https://kiranabaazaar.com/Admin/uploads/category/";
//    public static String SUB_CATEGORY_URL = "https://kiranabaazaar.com/Admin/uploads/subcat/";
//    public static String SUB_CATEGORY_CHILD_URL = "https://kiranabaazaar.com/Admin/uploads/childsubcat/";
//    public static String USER_PROFILE_URL = "https://example.com/api/uploads/customer_profiles/";
//    public static String VENDOR_PROFILE_URL = "https://kiranabaazaar.com/Admin/uploads/profile/";
//    public static String CATEGORY_PRODUCT_URL = "https://kiranabaazaar.com/Admin/uploads/";
//    public static String BANNER_URL = "https://kiranabaazaar.com/Admin/uploads/sliders/";
//    public static String THUMB_URL = "https://kiranabaazaar.com/Admin///uploads/thum_image/";


    private static Retrofit retrofit = null;
    private static OkHttpClient client;

    public static RestService getClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(10, TimeUnit.MINUTES);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        // change your base URL
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(clientBuilder.build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        //Creating object for our interface
        RestService api = retrofit.create(RestService.class);
        return api; // return the APIInterface object
    }
}