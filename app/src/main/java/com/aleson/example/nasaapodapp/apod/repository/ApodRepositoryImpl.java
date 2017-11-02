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
        ConnectivityManager cm =
                (ConnectivityManager) mActivity.getSystemService(mActivity.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void requestData(String date) {
        if (isOnline()) {
            attempt++;
            new ApodRequest(this, date);
        } else {
            mainActivityView.onFinishLoad();
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
        if (model == null || model.getUrl() == null || model.getCode() == "400" || model.getDate() == null) {
            apodPresenter.responseError(model);
        } else {
            model.setId(Long.parseLong(model.getDate().replace("-", "")));
            apodPresenter.responseSucess(model);
        }
    }

    @Override
    public void onError(String response) {
        mainActivityView.onFinishLoad();
        mainActivityView.onError("");
    }

    @Override
    public void serviceError(String date) {
        if (attempt < 3) {
            requestData(date);
        } else {
            mainActivityView.onFinishLoad();
            mainActivityView.onServiceError();
        }
    }

    @Override
    public void setWallpaper(Bitmap bitmap) {
        mainActivityView.onFinishLoad();
        mainActivityView.setWallpaper(bitmap);
    }
}
