package com.aleson.example.nasaapodapp.apod.presentation;

import android.graphics.Bitmap;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;

public interface MainActivityView {

    void onError(String response);

    void onServiceError();

    void onConnectionError();

    void onLoading(boolean content);

    void onFinishLoad();

    void setContent(ApodModel model);

    void setWallpaper(Bitmap bitmap);
}
