package com.aleson.example.nasaapodapp.presenter;

public interface ApodPresenter {

    void getTodayApod();
    void getRandomApod(String date);
    void getChosenApod(String date);

}
