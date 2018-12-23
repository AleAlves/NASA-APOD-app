// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.about.presentation;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.about.presenter.AboutPrensenterImpl;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

public class AboutActivity extends AppCompatActivity implements AboutView{

    private TextView textViewServiceVersion;
    private TextView getTextViewAppVersion;
    private JustifiedTextView justifiedTextViewAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        init();
        new AboutPrensenterImpl(this);
        loadAppVersion();
    }

    private void init(){
        getTextViewAppVersion = (TextView) findViewById(R.id.text_view_app_version);
        textViewServiceVersion = (TextView) findViewById(R.id.text_view_service_version);
        justifiedTextViewAbout = (JustifiedTextView) findViewById(R.id.about);
        justifiedTextViewAbout.setText("Travel through space/time learning with these pics and get wallpapers. Astronomy Picture of the Day (APOD) is originated, written, coordinated, and edited since 1995 by Robert Nemiroff and Jerry Bonnell. Mobile Project developed by Aleson Alves as a form of study bringing together the passion for technology, science and astronomy. All rights reserved ©");
    }

    @Override
    public void loadServiceVersion(String version) {
        textViewServiceVersion.setText(version);
    }

    private void loadAppVersion(){
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            getTextViewAppVersion.setText(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
			Log.e("Error", e.toString());
        }
    }
}
