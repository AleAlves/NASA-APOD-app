package br.com.aleson.nasa.apod.app.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageHelper extends AndroidHelper {

    private static String ID = "USER_PREFS";

    public static boolean saveData(String key, String data) {

        try {
            SharedPreferences sharedPref = currentContext.getSharedPreferences(ID, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, data);
            editor.commit();
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public static boolean saveData(String key, boolean value) {

        try {
            SharedPreferences sharedPref = currentContext.getSharedPreferences(ID, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(key, value);
            editor.commit();
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public static String readData(String key, String defaultValue) {
        SharedPreferences sharedPref = currentContext.getSharedPreferences(ID, Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValue);
    }

    public static boolean readData(String key) {
        SharedPreferences sharedPref = currentContext.getSharedPreferences(ID, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    public static boolean readData(String key, boolean defaultValue) {
        SharedPreferences sharedPref = currentContext.getSharedPreferences(ID, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, defaultValue);
    }
}
