package com.aleson.example.nasaapodapp.about.presentation;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.about.presenter.AboutPrensenterImpl;
import com.aleson.example.nasaapodapp.about.presenter.AboutPresenter;

public class AboutActivity extends AppCompatActivity implements AboutView{

    private TextView textViewServiceVersion;
    private TextView getTextViewAppVersion;

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
            e.printStackTrace();
        }
    }
}
