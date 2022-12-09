package com.myroom.myroom.app.loginmanage;

import android.content.Context;

import appsession.AppSession;

public class ManageSession {

    public static boolean logOut(Context context)
    {
        try {
//           AppSession appSession = new AppSession(context);
//            appSession.setIsLogin("0");
//            appSession.setUserID("");
//            appSession.setFname("");
//            appSession.setLname("");
//            appSession.setPermanentAddress("");
//            appSession.setPresentAddress("");
//            appSession.setEmail("");
//            appSession.setMobile("");
//            appSession.setCityCurrent("");
//            appSession.setIsCustomeLocation(false);

            return new AppSession(context).clearUserData();
        }catch (Exception e)
        {
             e.printStackTrace();
            return false;
        }

    }

  public static UserData getUserData(Context context)
  {

       AppSession appSession = new AppSession(context);
       UserData data = new UserData(appSession.getFname(),appSession.getLname(),appSession.getMobile(),appSession.getEmail()
       ,appSession.getPresentAddress(),appSession.getPermanentAddress());


  return data;
  }


  public static void updateUserData(Context context ,UserData userData)
  {
       AppSession appSession = new AppSession(context);
      appSession.setFname(userData.getFname());
      appSession.setLname(userData.getLname());
      appSession.setPermanentAddress(userData.getPermanetadd());
      appSession.setPresentAddress(userData.getCurrentadd());
      appSession.setMobile(userData.getPhone());
  }


    public static boolean Login(Context context,String user_id,String f_name, String l_name,String email ,
                             String phone ,String peresentaddress, String permanentaddress)

    {
        try {

            AppSession appSession = new AppSession(context);
            appSession.setIsLogin("1");
            appSession.setUserID(user_id);
            appSession.setFname(f_name);
            appSession.setLname(l_name);
            appSession.setPermanentAddress(permanentaddress);
            appSession.setPresentAddress(peresentaddress);
            appSession.setEmail(email);
            appSession.setMobile(phone);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;

        }



    }


}
