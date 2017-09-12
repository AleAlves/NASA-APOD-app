package com.aleson.example.nasaapodapp.repository;

public interface ApodRepository {

    void requestData(String date);

    void onSucess(String response);

    void onError(String response);

    void serviceError();

}
