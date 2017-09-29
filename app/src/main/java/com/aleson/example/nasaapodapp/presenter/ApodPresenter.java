package com.aleson.example.nasaapodapp.presenter;

import com.aleson.example.nasaapodapp.domain.ApodModel;

public interface ApodPresenter {

    void getTodayApod();

    void getRandomApod(String date);

    void getChosenApod(String date);

    void responseSucess(ApodModel model);

    void responseError(ApodModel model);

    void chooseWallpaper(String url);

    int getMediaType();
}
