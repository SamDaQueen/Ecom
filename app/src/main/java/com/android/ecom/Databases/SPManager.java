package com.android.ecom.Databases;

import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {

    private static final String PREF_NAME = "ECOM";
    private static SharedPreferences sharedPreferences;
    private static String USER_NAME = "name";
    private static String USER_NUMBER = "999";
    private static String USER_ADDRESS = "home";
    private static String IS_FIRST_LAUNCH = "first_launch";

    private static SharedPreferences.Editor editor;

    public static SharedPreferences isEmpty() {
        return sharedPreferences;
    }

    public static void DeleteData(Context context) {
        if (sharedPreferences == null)
            init(context);
        editor.clear();
        editor.commit();
    }

    private static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static String getUserName(Context context) {
        if (sharedPreferences == null)
            init(context);
        return sharedPreferences.getString(USER_NAME, "Mr. X");
    }

    public static void setUserName(Context context, String userName) {
        if (sharedPreferences == null)
            init(context);
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    public static String getUserNumber(Context context) {
        if (sharedPreferences == null)
            init(context);
        return sharedPreferences.getString(USER_NUMBER, "999");
    }

    public static void setUserNumber(Context context, String userNumber) {
        if (sharedPreferences == null)
            init(context);
        editor.putString(USER_NUMBER, userNumber);
        editor.apply();
    }

    public static String getUserAddress(Context context) {
        if (sharedPreferences == null)
            init(context);
        return sharedPreferences.getString(USER_ADDRESS, "home");
    }

    public static void setUserAddress(Context context, String userAddress) {
        if (sharedPreferences == null)
            init(context);
        editor.putString(USER_ADDRESS, userAddress);
        editor.apply();
    }

    public static boolean getIsFirstLaunch(Context context) {
        if (sharedPreferences == null)
            init(context);
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true);
    }

    public static void setIsFirstLaunch(Context context, boolean isFirstLaunch) {
        if (sharedPreferences == null)
            init(context);
        editor.putBoolean(IS_FIRST_LAUNCH, isFirstLaunch);
        editor.apply();
    }

}

