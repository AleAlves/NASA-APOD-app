package com.aleson.example.nasaapodapp.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Santander on 11/11/17.
 */

public class NetworkingUtils {

    private NetworkingUtils(){}

    public static boolean isOnline(Activity mActivity) {
        ConnectivityManager cm = (ConnectivityManager) mActivity.getSystemService(mActivity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
