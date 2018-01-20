// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.utils;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Aleson on 23-Dec-17.
 */

public class FirebaseUtil {

    SettingsUtil settings;

    public FirebaseUtil(SettingsUtil settingsUtil) {
        this.settings = settingsUtil;
        try {
            Log.i("firebaseID:", FirebaseInstanceId.getInstance().getToken());
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        updateSettings();
    }

    public void updateSettings() {
        if (settings.getSharedPreferences().getBoolean("dailyNotification", true)) {
            subscribeDailyNotification();
        } else {
            unsubscribeDailyNotification();
        }
    }

    public void subscribeDailyNotification() {
        FirebaseMessaging.getInstance().subscribeToTopic("apod");
    }

    public void unsubscribeDailyNotification() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("apod");
    }
}
