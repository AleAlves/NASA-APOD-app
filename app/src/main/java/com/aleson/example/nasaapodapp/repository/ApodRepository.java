package com.aleson.example.nasaapodapp.repository;

import android.graphics.Bitmap;

public interface ApodRepository {

    void requestData(String date);

    void requestBitamp(String url);

    void onSucess(String response);

    void onError(String response);

    void serviceError();

    void setWallpaper(Bitmap bitmap);

}
