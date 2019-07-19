package com.aleson.example.nasaapodapp.common.callback;

public interface BasePresenter {

    void showLoading();

    void hideLoading();

    void onError();

    void onError(String message);
}
