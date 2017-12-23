package com.aleson.example.nasaapodapp.utils;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Aleson on 23-Dec-17.
 */

public class FirebaseUtil {

    SettingsUtil settings;

    public FirebaseUtil(Context context, SettingsUtil settingsUtil){
        this.settings = settingsUtil;
        String IID_TOKEN = FirebaseInstanceId.getInstance().getToken();
        updateSettings();
    }

    public void updateSettings(){
        if(settings.getSharedPreferences().getBoolean("dailyNotification",true)){
            subscribeDailyNotification();
        }
        else{
            unsubscribeDailyNotification();
        }
    }

    public void subscribeDailyNotification(){
        FirebaseMessaging.getInstance().subscribeToTopic("apod");
    }

    public void unsubscribeDailyNotification(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("apod");
    }
}
