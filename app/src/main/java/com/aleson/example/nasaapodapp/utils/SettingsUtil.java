package com.aleson.example.nasaapodapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Aleson on 23-Dec-17.
 */

public class SettingsUtil {

    private SharedPreferences.Editor editor;

    private SharedPreferences sharedPreferences;

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SettingsUtil(Context context, String varId){
        sharedPreferences = context.getSharedPreferences(varId, 0);
        editor = sharedPreferences.edit();
    }

    public void updateSettings(){
        new FirebaseUtil(this);
    }
}
