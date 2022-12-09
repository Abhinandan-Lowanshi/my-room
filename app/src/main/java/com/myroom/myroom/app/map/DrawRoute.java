package com.myroom.myroom.app.map;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;


import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import appsession.AppSession;

/**
 * Created by ocittwo on 11/14/16.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */

public class DrawRoute extends AsyncTask<String, Void, String> {

    private GoogleMap mMap;
    int status;
    AppSession appSession;
    String data = "";
    RunCode runCode;
    Activity activity;

    public DrawRoute(GoogleMap mMap,int status, Activity activity ) {
        this.mMap = mMap;
        this.status = status;
        this.activity = activity;
        this.runCode = (RunCode) activity;
    }

    @Override
    protected String doInBackground(String... url) {
        String data = "";
        try {
            data = getJsonRoutePoint(url[0]);
            Log.d("Background Task data", data);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        RouteDrawerTask routeDrawerTask = new RouteDrawerTask(mMap,status);
        routeDrawerTask.execute(result);
    }

    /**
     * A method to download json data from url
     */
    private String getJsonRoutePoint(String strUrl) throws IOException {
         data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
           appSession = new AppSession(activity.getApplicationContext());
           appSession.setData(data);
            runCode.runCode(data);
          //Model_Route_Data data1 = new Gson().fromJson(data, Model_Route_Data.class);
            //Log.d("getJsonRoutePoint", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public interface RunCode
    {
         public void runCode(String data);
    }
//    public Model_Route_Data getRouteData()
//    {
//        if(data=="")
//        {
//            Model_Route_Data data1 = new Model_Route_Data();
//            data1.setStatus("False");
//            return data1;
//
//        }else {
//            Model_Route_Data data1 = new Gson().fromJson(data, Model_Route_Data.class);
//            return data1;
//        }
//
//    }

}
