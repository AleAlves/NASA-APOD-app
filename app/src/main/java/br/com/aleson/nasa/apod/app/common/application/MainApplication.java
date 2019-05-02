package br.com.aleson.nasa.apod.app.common.application;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;

import com.github.android.aleson.slogger.SLogger;
import com.google.firebase.FirebaseApp;

import br.com.aleson.nasa.apod.app.common.firebase.FirebaseCloudMessaging;
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
        Connector.init("https://nasa-apod-server.herokuapp.com/");
        verifyDailyNotificationsSubscribe();
    }

    private void verifyDailyNotificationsSubscribe() {

        if (true) {
            FirebaseCloudMessaging.subscribeDailyNotification();
        } else {
            FirebaseCloudMessaging.unsubscribeDailyNotification();
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }

    @Override
    public void registerOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
        super.registerOnProvideAssistDataListener(callback);
    }

    @Override
    public void unregisterOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
        super.unregisterOnProvideAssistDataListener(callback);
    }
}
