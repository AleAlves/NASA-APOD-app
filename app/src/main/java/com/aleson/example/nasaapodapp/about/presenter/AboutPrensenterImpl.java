// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.about.presenter;

import com.aleson.example.nasaapodapp.about.presentation.AboutActivity;
import com.aleson.example.nasaapodapp.about.presentation.AboutView;
import com.aleson.example.nasaapodapp.about.repository.AboutRepository;
import com.aleson.example.nasaapodapp.about.repository.AboutRepositoryImpl;

/**
 * Created by Aleson on 23-Dec-17.
 */

public class AboutPrensenterImpl implements AboutPresenter {

    private AboutRepository apodRepository;
    private AboutView aboutView;

    public AboutPrensenterImpl(AboutActivity aboutActivity){
        this.aboutView = (AboutView) aboutActivity;
        this.apodRepository = new AboutRepositoryImpl(this);
        this.getServiceVersion();
    }

    @Override
    public void getServiceVersion() {
        apodRepository.requestServiceVersion();
    }

    @Override
    public void setServiceVersion(String version) {
        aboutView.loadServiceVersion(version);
    }
}
