package com.aleson.example.nasaapodapp.common.application;

import android.app.Application;

import com.aleson.example.nasaapodapp.common.constants.Constants;
import com.aleson.example.nasaapodapp.common.util.AndroidHelper;
import com.github.android.aleson.slogger.SLogger;
import com.google.firebase.FirebaseApp;

import com.aleson.example.nasaapodapp.common.firebase.FirebaseCloudMessaging;
import com.aleson.example.nasaapodapp.common.util.StorageHelper;
import br.com.connector.aleson.android.connector.Connector;

public class MainApplication extends Application {

    public MainApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);

        SLogger.init(true);

        AndroidHelper.currentContext = this;

        verifyDailyNotificationsSubscribe();

        Connector.init(Constants.SERVICE.MAIN_SERVER);
    }

    private void verifyDailyNotificationsSubscribe() {

        if (StorageHelper.readData(Constants.NOTIFICATIONS.DAILY_NOTIFICATION, true)) {

            FirebaseCloudMessaging.subscribeDailyNotification();
        } else {

            FirebaseCloudMessaging.unsubscribeDailyNotification();
        }
    }
}
