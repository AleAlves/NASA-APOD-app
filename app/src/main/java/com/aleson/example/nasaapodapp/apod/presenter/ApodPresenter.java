package com.aleson.example.nasaapodapp.apod.presenter;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;

public interface ApodPresenter {

    void getTodayApod();

    void getRandomApod(String date);

    void getChosenApod(String date);

    void responseSucess(ApodModel model);

    void responseError(ApodModel model);

    void chooseWallpaper(String url);

    int getMediaType();

    String getDataSelecionada();
}
