package com.aleson.example.nasaapodapp.presentation;


import com.aleson.example.nasaapodapp.domain.ApodModel;

public interface MainActivityView {

    void onSucess(ApodModel response);

    void onError(String response);

    void onServiceError();

    void onLoading();

    void onFinishLoad();

    void loadImage(ApodModel model);

    void loadGif(ApodModel model);

    void loadVideo(ApodModel model);
}
