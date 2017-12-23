package com.aleson.example.nasaapodapp.about.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.about.presenter.AboutPrensenterImpl;
import com.aleson.example.nasaapodapp.about.presenter.AboutPresenter;

public class AboutActivity extends AppCompatActivity implements AboutView{

    private TextView textViewServiceVersion;
    private AboutPresenter aboutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        init();
        aboutPresenter = new AboutPrensenterImpl(this);
    }

    private void init(){
        textViewServiceVersion = (TextView) findViewById(R.id.text_view_service_version);
    }

    @Override
    public void loadServiceVersion(String version) {
        textViewServiceVersion.setText(version);
    }
}
