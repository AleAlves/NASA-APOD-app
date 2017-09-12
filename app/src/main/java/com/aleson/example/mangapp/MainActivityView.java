package com.aleson.example.mangapp;

/**
 * Created by Santander on 05/09/17.
 */

public interface MainActivityView {
    void onSucess(String response);
    void onError(String response);
    void onConnectionError();
}
