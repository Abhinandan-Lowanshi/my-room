package Adapter;

import android.util.Patterns;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupportValidation {

    public  static boolean passwordValidation(String np)
        {
            String password = np;
            if(password.isEmpty())
            {

                return false;
            }else if(password.length()<6)
            {

                return false;
            }else
            {
                return true;
            }
        }
        public static boolean passwordValidation_2(String np) {
            String password = np;
            Pattern p = Pattern.compile("^(?=.{9,32}$)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*");
            Matcher m = p.matcher(password);
            if (m.find())
                return true;
            else return false;
        }

     public static boolean textValidation(String text)
     {

         HashMap<Character, Integer> hMap = new HashMap<>();
         for (int i = text.length() - 1; i >= 0; i--) {
             if (hMap.containsKey(text.charAt(i))) {
                 int count = hMap.get(text.charAt(i));
                 hMap.put(text.charAt(i), ++count);
             } else {
                 hMap.put(text.charAt(i), 1);
             }
         }

       return true;
     }

        public static  boolean emailvalidation(String np)
        {
            String email = np;
            if(email.isEmpty())
            {

                return false;
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {

                return  false;
            }else
            {

                return true;
            }

        }
        public  static boolean mobileValidation(String mobile)
        {
            if(mobile.isEmpty())
            {
                return false;
            }else if(mobile.length()!=10)
            {
                return false;
            }else { return true;}

        }


    }


