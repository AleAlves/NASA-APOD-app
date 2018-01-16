// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.apod.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

public interface ApodPresenter {

    void getTodayApod();

    void getRandomApod(String date);

    void getChosenApod(String date);

    void responseSucess(Apod model);

    void serviceError(String code);

    void chooseWallpaper(String url);

    int getMediaType();

    void badRequest(String code);
}
