package com.aleson.example.nasaapodapp.common.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.common.util.AndroidHelper;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.android.aleson.slogger.SLogger;

public class BaseActivity extends DialogActivity implements BaseView {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidHelper.currentContext = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initToolbar();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    private void initToolbar() {

        toolbar = findViewById(R.id.apod_default_toolbar);
        if (toolbar != null) {
            toolbar.setContentInsetsAbsolute(0, 0);

            try {
                ActivityInfo activityInfo = getPackageManager()
                        .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                String toolbarTitleText = activityInfo.loadLabel(getPackageManager()).toString();
                toolbarTitle = findViewById(R.id.apod_toolbar_title);
                toolbarTitle.setText(toolbarTitleText);
                toolbarTitle.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));

                setSupportActionBar(toolbar);

                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayShowTitleEnabled(false);
                }

            } catch (PackageManager.NameNotFoundException e) {
                SLogger.e(e);
            }
        }
    }

    @Override
    public void onClick(View v) {
        SLogger.d(v.getId());
    }

}
