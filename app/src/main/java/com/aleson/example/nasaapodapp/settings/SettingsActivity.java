package com.aleson.example.nasaapodapp.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.utils.FirebaseUtil;
import com.aleson.example.nasaapodapp.utils.SettingsUtil;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox checkBoxSubscribe;
    private SettingsUtil settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        init();
        settings = new SettingsUtil(this, "settings");
        handleDailyNotificationCheckbox();
    }

    private void init() {
        checkBoxSubscribe = (CheckBox) findViewById(R.id.checkbox_suscribe);
        checkBoxSubscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                changeDailyNotification(b);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    private void changeDailyNotification(boolean b) {
        if (!b) {
            checkBoxSubscribe.setChecked(false);
            settings.getEditor().putBoolean("dailyNotification", false);
        } else {
            checkBoxSubscribe.setChecked(true);
            settings.getEditor().putBoolean("dailyNotification", true);
        }
        settings.getEditor().commit();
        settings.updateSettings();
    }

    public void handleDailyNotificationCheckbox(){
        if(settings.getSharedPreferences().getBoolean("dailyNotification", true)){
            checkBoxSubscribe.setChecked(true);
        }
        else{
            checkBoxSubscribe.setChecked(false);
        }
    }
}
