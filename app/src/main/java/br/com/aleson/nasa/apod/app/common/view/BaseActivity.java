package br.com.aleson.nasa.apod.app.common.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.application.MainApplication;
import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.android.aleson.slogger.SLogger;

public class BaseActivity extends DialogActivity implements BaseView {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
