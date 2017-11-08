package com.aleson.example.nasaapodapp.apod.repository;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.presentation.MainActivityView;
import com.aleson.example.nasaapodapp.apod.presenter.ApodPresenter;
import com.aleson.example.nasaapodapp.apod.repository.task.ApodRequest;
import com.aleson.example.nasaapodapp.apod.repository.task.BitmapService;

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

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mActivity.getSystemService(mActivity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void requestData(String date) {
        if (isOnline()) {
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
