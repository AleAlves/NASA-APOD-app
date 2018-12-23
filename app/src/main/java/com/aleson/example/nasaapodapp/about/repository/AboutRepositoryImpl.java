// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.about.repository;

import com.aleson.example.nasaapodapp.about.presenter.AboutPresenter;
import com.aleson.example.nasaapodapp.about.repository.task.ServiceVersionRequest;

public class AboutRepositoryImpl implements AboutRepository {

    private AboutPresenter aboutPresenter;

    public AboutRepositoryImpl(AboutPresenter aboutPresenter){
        this.aboutPresenter = aboutPresenter;
    }
    @Override
    public void requestServiceVersion() {
        new ServiceVersionRequest(this);
    }

    @Override
    public void onSucessesServiceVersion(String version) {
        this.aboutPresenter.setServiceVersion(version);
    }
}
