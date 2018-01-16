// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.apod.repository;

import android.graphics.Bitmap;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

public interface ApodRepository {

    void requestData(String date);

    void requestBitamp(String url);

    void onSucess(Apod response);

    void onError(String response);

    void setWallpaper(Bitmap bitmap);

    void badRequest(String code);
}
