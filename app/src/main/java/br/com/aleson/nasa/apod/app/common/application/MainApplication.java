package br.com.aleson.nasa.apod.app.common.application;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;

import com.github.android.aleson.slogger.SLogger;
import com.google.firebase.FirebaseApp;

import br.com.aleson.nasa.apod.app.common.constants.Constants;
import br.com.aleson.nasa.apod.app.common.firebase.FirebaseCloudMessaging;
import br.com.aleson.nasa.apod.app.common.util.AndroidHelper;
import br.com.aleson.nasa.apod.app.common.util.StorageHelper;
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
