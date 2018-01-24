// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkingUtils {

    private NetworkingUtils(){}

    public static boolean isOnline(Activity mActivity) {
        ConnectivityManager cm = (ConnectivityManager) mActivity.getSystemService(mActivity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
