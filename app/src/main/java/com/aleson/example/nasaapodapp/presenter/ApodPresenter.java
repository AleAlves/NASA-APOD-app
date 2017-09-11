package com.aleson.example.nasaapodapp.presenter;

/**
 * Created by Santander on 11/09/17.
 */

public interface ApodPresenter {

    void getTodayApod();
    void getRandomApod(String date);
    void getChosenApod(String date);

}
