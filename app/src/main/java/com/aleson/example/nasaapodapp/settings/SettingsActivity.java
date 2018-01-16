// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.utils.SettingsUtil;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CheckBox checkBoxSubscribe;
    private CheckBox checkBoxSaveImages;
    private Spinner spinnerListSize;
    private SettingsUtil settings;
    private static final String SAVE_IMAGE = "saveImages";
    private static final String DAILY_NOTIFICATION = "dailyNotification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        settings = new SettingsUtil(this, "settings");
        init();
    }

    private void init() {
        checkBoxSubscribe = (CheckBox) findViewById(R.id.checkbox_suscribe);
        checkBoxSubscribe.setChecked(settings.getSharedPreferences().getBoolean(DAILY_NOTIFICATION, true));
        checkBoxSubscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                changeDailyNotification(b);
            }
        });
        checkBoxSaveImages = (CheckBox) findViewById(R.id.checkbox_save_images);
        checkBoxSaveImages.setChecked(settings.getSharedPreferences().getBoolean(SAVE_IMAGE, false));
        checkBoxSaveImages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                changeSaveImages(b);
            }
        });
        spinnerListSize = (Spinner) findViewById(R.id.spinner_list_size);
        spinnerListSize.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("10");
        categories.add("20");
        categories.add("30");
        categories.add("40");
        categories.add("50");
        categories.add("100");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerListSize.setAdapter(dataAdapter);
        spinnerListSize.setSelection(settings.getSharedPreferences().getInt("topRatedListSizeIndex",0));
    }

    private void changeSaveImages(boolean b){
        if (!b) {
            checkBoxSaveImages.setChecked(false);
            settings.getEditor().putBoolean(SAVE_IMAGE, false);
        } else {
            checkBoxSaveImages.setChecked(true);
            settings.getEditor().putBoolean(SAVE_IMAGE, true);
        }
        settings.getEditor().commit();
        settings.updateSettings();
    }

    private void changeDailyNotification(boolean b) {
        if (!b) {
            checkBoxSubscribe.setChecked(false);
            settings.getEditor().putBoolean(DAILY_NOTIFICATION, false);
        } else {
            checkBoxSubscribe.setChecked(true);
            settings.getEditor().putBoolean(DAILY_NOTIFICATION, true);
        }
        settings.getEditor().commit();
        settings.updateSettings();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        settings.getEditor().putInt("topRatedListSizeIndex", i);
        settings.getEditor().putString("topRatedListSize", item);
        settings.getEditor().commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //TODO
    }
}
