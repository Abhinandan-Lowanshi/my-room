package com.myroom.myroom.app.time;//package com.example.myroom.app.time;
//
//import android.util.Log;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TimeZone;
//import java.util.concurrent.TimeUnit;
//public class TimeShow
//{
//    public static String gettime(String target) {
//        String time = "";
//        String formattedDate = null;
//
//        try
//        {
//
//
//
//            String dateStr = "Jul 16, 2013 12:08:59 AM";
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
//            df.setTimeZone(TimeZone.getTimeZone("UTC"));
//             Date date = df.parse(target);
//             df.setTimeZone(TimeZone.getDefault());
//             formattedDate = df.format(date);
//
//
////            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss");
////            format.setTimeZone(TimeZone.getTimeZone("UTC"));
////            Date date = null;
////            try {
////                date = format.parse(target);
////            } catch (ParseException e) {
////                e.printStackTrace();
////            }
////            format.setTimeZone(TimeZone.getDefault());
////             formattedDate = format.format(date);
////            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
////            formatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); // Or whatever IST is supposed to be
//
////            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
//            Date past = format.parse(formattedDate);
//            Date now = new Date();
//            long seconds=TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
//            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
//            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
//            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
////
////          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
////          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
////          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
////          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");
//
//            if(seconds<60)
//            {
//                time = seconds+" seconds ago";
//            }
//            else if(minutes<60)
//            {
//                time =minutes+" minutes ago";
//            }
//            else if(hours<24)
//            {
//                time =hours+" hours ago";
//            }
//            else
//            {
//                time =days+" days ago";
//            }
//
//        }
//        catch (Exception j){
//            Log.d("Rdfgffgfgfgf", j.getLocalizedMessage());
//        }
//        return  time;
//    }
//}
//


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeShow {

    protected Context context;

    public TimeShow(Context context) {
        this.context = context;
    }

//    public String timeAgo(String date) {
//        return timeAgo(date);
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("StringFormatInvalid")
    public String timeAgo(String data) {


        Instant s = Instant.parse(data);
        ZoneId.of("Asia/Kolkata");
        LocalDateTime ldt = LocalDateTime.ofInstant(s, ZoneId.of("Asia/Kolkata"));

        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        Date output = Date.from(zdt.toInstant());




        long millls = output.getTime();
        long diff = new Date().getTime() - millls;

        Resources r = context.getResources();

//        String prefix = r.getString("R.string.time_ago_prefix");
//        String suffix = r.getString(R.string.time_ago_suffix);
      String prefix = "Pre";
      String suffix = "Post";

//        double seconds = Math.abs(diff) / 1000;
//        double minutes = seconds / 60;
//        double hours = minutes / 60;
//        double days = hours / 24;
//        double years = days / 365;



//        Date past = format.parse(formattedDate);
            Date now = new Date();
            long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - output.getTime());
            long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - output.getTime());
            long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - output.getTime());
            long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - output.getTime());

        String words;

        if(seconds<60)
            {
                words = seconds+" seconds ago";
            }
            else if(minutes<60)
            {
                words =minutes+" minutes ago";
            }
            else if(hours<24)
            {
                words =hours+" hours ago";
            }
            else
            {
                words =days+" days ago";
            }


//        if (seconds < 45) {
//            words = r.getString(R.string.time_ago_seconds, Math.round(seconds));
//        } else if (seconds < 90) {
//            words = r.getString(R.string.time_ago_minute, 1);
//        } else if (minutes < 45) {
//            words = r.getString(R.string.time_ago_minutes, Math.round(minutes));
//        } else if (minutes < 90) {
//            words = r.getString(R.string.time_ago_hour, 1);
//        } else if (hours < 24) {
//            words = r.getString(R.string.time_ago_hours, Math.round(hours));
//        } else if (hours < 42) {
//            words = r.getString(R.string.time_ago_day, 1);
//        } else if (days < 30) {
//            words = r.getString(R.string.time_ago_days, Math.round(days));
//        } else if (days < 45) {
//            words = r.getString(R.string.time_ago_month, 1);
//        } else if (days < 365) {
//            words = r.getString(R.string.time_ago_months, Math.round(days / 30));
//        } else if (years < 1.5) {
//            words = r.getString(R.string.time_ago_year, 1);
//        } else {
//            words = r.getString(R.string.time_ago_years, Math.round(years));
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        if (prefix != null && prefix.length() > 0) {
//            sb.append(prefix).append(" ");
//        }
//
//        sb.append(words);
//
//        if (suffix != null && suffix.length() > 0) {
//            sb.append(" ").append(suffix);
//        }

        return words;
    }
}


