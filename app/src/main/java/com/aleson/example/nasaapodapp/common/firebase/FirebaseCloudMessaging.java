package com.aleson.example.nasaapodapp.common.firebase;


import com.google.firebase.messaging.FirebaseMessaging;

public class FirebaseCloudMessaging {

    public static void subscribeDailyNotification() {
        FirebaseMessaging.getInstance().subscribeToTopic("DailyAPOD");
    }

    public static void unsubscribeDailyNotification() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("DailyAPOD");
    }
}
