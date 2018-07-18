package buyer.bizzcity.bizzcityinfobuyer.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 2/7/2017.
 */

public class MyPrefrences {

    static SharedPreferences loginPreferences;
    public static SharedPreferences userIdPrefences;
    static SharedPreferences cataPrefences;
    static SharedPreferences subCataPrefences;
    static SharedPreferences username;
    static SharedPreferences emaildid;
    static SharedPreferences Mobile;
    static SharedPreferences Notification;
    static SharedPreferences City;
    static SharedPreferences CityName;
    static SharedPreferences CityDialog;
    static SharedPreferences Image;

    public static String USER_LOGIN = "userlogin";
    public static String USER_ID = "user_id";
    public static String CATA_ID = "CATA_ID";
    public static String SCATA_ID = "SCATA_ID";
    public static String USERNAME = "USERNAME";
    public static String EMAILID = "EMAILID";
    public static String MOBILE = "MOBILE";
    public static String NOTI = "NOTI";
    public static String CITY = "CITY";
    public static String CITYNAME = "CITYNAME";
    public static String CITYDIA = "CITYDIA";
    public static String IMAGE = "IMAGE";


    public static void resetPrefrences(Context context) {

        setUserLogin(context, false);
        setUserID(context, "");
        setCatID(context, "");
        setSCatID(context, "");
        setUSENAME(context, "");
        setEMAILID(context, "");
        setMobile(context, "");
        setImage(context, "");
     //   setCityID(context, "");
      //  setCityName(context, "");


    }



    public static void setUserLogin(Context context, boolean is) {
        loginPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = loginPreferences.edit();
        editor.putBoolean(USER_LOGIN, is);
        editor.commit();
    }

    public static boolean getUserLogin(Context context) {
        loginPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return loginPreferences.getBoolean(USER_LOGIN,false);

    }


    public static void setCityDialog(Context context, boolean is) {
        CityDialog = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = CityDialog.edit();
        editor.putBoolean(CITYDIA, is);
        editor.commit();
    }

    public static boolean getCityDialog(Context context) {
        CityDialog = PreferenceManager.getDefaultSharedPreferences(context);
        return CityDialog.getBoolean(CITYDIA,false);

    }



    public static void setNotiStatus(Context context, boolean is) {
        Notification = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = Notification.edit();
        editor.putBoolean(NOTI, is);
        editor.commit();
    }

    public static boolean getNotiStatus(Context context) {
        Notification = PreferenceManager.getDefaultSharedPreferences(context);
        return Notification.getBoolean(NOTI,true);
    }



    public static void setUserID(Context context, String is) {
        userIdPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = userIdPrefences.edit();
        editor.putString(USER_ID, is);
        editor.commit();
    }

    public static String getUserID(Context context) {
        userIdPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        return userIdPrefences.getString(USER_ID,"");
    }

    public static void setCatID(Context context, String is) {
        cataPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = cataPrefences.edit();
        editor.putString(CATA_ID, is);
        editor.commit();
    }

    public static String getCatID(Context context) {
        cataPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        return cataPrefences.getString(CATA_ID,"");
    }


    public static void setSCatID(Context context, String is) {
        subCataPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = subCataPrefences.edit();
        editor.putString(SCATA_ID, is);
        editor.commit();
    }

    public static String getSCatID(Context context) {
        subCataPrefences = PreferenceManager.getDefaultSharedPreferences(context);
        return subCataPrefences.getString(SCATA_ID,"");
    }



    public static void setUSENAME(Context context, String is) {
        username = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = username.edit();
        editor.putString(USERNAME, is);
        editor.commit();
    }

    public static String getUSENAME(Context context) {
        username = PreferenceManager.getDefaultSharedPreferences(context);
        return username.getString(USERNAME,"");
    }



    public static void setImage(Context context, String is) {
        Image = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = Image.edit();
        editor.putString(IMAGE, is);
        editor.commit();
    }

    public static String getImage(Context context) {
        Image = PreferenceManager.getDefaultSharedPreferences(context);
        return Image.getString(IMAGE,"");
    }





    public static void setEMAILID(Context context, String is) {
        emaildid = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = emaildid.edit();
        editor.putString(EMAILID, is);
        editor.commit();
    }

    public static String getEMAILID(Context context) {
        emaildid = PreferenceManager.getDefaultSharedPreferences(context);
        return emaildid.getString(EMAILID,"");
    }


    public static void setMobile(Context context, String is) {
        Mobile = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = Mobile.edit();
        editor.putString(MOBILE, is);
        editor.commit();
    }

    public static String getMobile(Context context) {
        Mobile = PreferenceManager.getDefaultSharedPreferences(context);
        return Mobile.getString(MOBILE,"");
    }

    public static void setCityID(Context context, String is) {
        City = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = City.edit();
        editor.putString(CITY, is);
        editor.commit();
    }

    public static String getCityID(Context context) {
        City = PreferenceManager.getDefaultSharedPreferences(context);
        return City.getString(CITY,"");
    }



    public static void setCityName(Context context, String is) {
        CityName = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = CityName.edit();
        editor.putString(CITYNAME, is);
        editor.commit();
    }

    public static String getCityName(Context context) {
        CityName = PreferenceManager.getDefaultSharedPreferences(context);
        return CityName.getString(CITYNAME,"");
    }




}
