package com.aleson.example.nasaapodapp.apod.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

public interface ApodPresenter {

    void getTodayApod();

    void getRandomApod(String date);

    void getChosenApod(String date);

    void responseSucess(Apod model);

    void responseError(Apod model);

    void chooseWallpaper(String url);

    int getMediaType();

    String getDataSelecionada();
}
