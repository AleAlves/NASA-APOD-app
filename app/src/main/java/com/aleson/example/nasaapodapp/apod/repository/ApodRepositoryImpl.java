// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.apod.repository;

import android.app.Activity;
import android.graphics.Bitmap;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.presentation.MainActivityView;
import com.aleson.example.nasaapodapp.apod.presenter.ApodPresenter;
import com.aleson.example.nasaapodapp.apod.repository.task.ApodRequest;
import com.aleson.example.nasaapodapp.apod.repository.task.BitmapService;
import com.aleson.example.nasaapodapp.utils.NetworkingUtils;

public class ApodRepositoryImpl implements ApodRepository {

    private Activity mActivity;
    private MainActivityView mainActivityView;
    private ApodPresenter apodPresenter;
    private int attempt;

    public ApodRepositoryImpl(Activity mActivity, ApodPresenter apodPresenter) {
        this.mActivity = mActivity;
        mainActivityView = (MainActivityView) mActivity;
        this.apodPresenter = apodPresenter;
        attempt = 0;
    }

    @Override
    public void requestData(String date) {
        if (NetworkingUtils.isOnline(mActivity)) {
            attempt++;
            new ApodRequest(this, date);
        } else {
            mainActivityView.onConnectionError();
        }
    }

    @Override
    public void requestBitamp(String url) {
        BitmapService bitmapService = new BitmapService(url, this);
        bitmapService.execute();
    }

    @Override
    public void onSucess(Apod model) {
            model.setId(Long.parseLong(model.getDate().replace("-", "")));
            apodPresenter.responseSucess(model);
    }

    @Override
    public void onError(String code) {
        if (attempt < 3) {
            requestData(code);
        } else {
            apodPresenter.serviceError(code);
        }
    }

    @Override
    public void setWallpaper(Bitmap bitmap) {
        mainActivityView.setWallpaper(bitmap);
    }

    @Override
    public void badRequest(String code) {
        apodPresenter.badRequest(code);
    }
}
