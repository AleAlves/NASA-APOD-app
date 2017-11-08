package com.aleson.example.nasaapodapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Santander on 01/11/17.
 */

public class Permissions {

    private Activity mActivity;
    private final int COMMON = 1;

    public Permissions(Activity activity) {
        this.mActivity = activity;
    }

    public void permissions() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mActivity, new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
            }, COMMON);
        }
        else{
            ActivityCompat.requestPermissions(mActivity, null, COMMON);
        }
    }

}
