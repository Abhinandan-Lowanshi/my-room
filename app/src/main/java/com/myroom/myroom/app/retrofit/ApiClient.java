package com.myroom.myroom.app.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myroom.myroom.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
  public static String BASE_URL = BuildConfig.BASE_URL;
  public static String IMAGE_PATH = BuildConfig.IMAGE_PATH;


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