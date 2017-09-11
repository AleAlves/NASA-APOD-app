package com.aleson.example.nasaapodapp.repository;

import android.app.Activity;

public interface ApodRepository {

    void requestData(Activity activity, String date);

    void onSucess(String response);

    void onError(String response);

    void serviceError();

}
