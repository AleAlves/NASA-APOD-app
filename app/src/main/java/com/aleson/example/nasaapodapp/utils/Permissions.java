// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.utils;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

public class Permissions {

    private Activity mActivity;
    public static final int COMMON = 1;

    public Permissions(Activity activity) {
        this.mActivity = activity;
    }

    public void permissions() {

        ActivityCompat.requestPermissions(mActivity, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
        }, COMMON);

    }

}
