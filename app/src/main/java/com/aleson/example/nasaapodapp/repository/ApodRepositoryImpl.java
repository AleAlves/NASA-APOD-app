package com.aleson.example.nasaapodapp.repository;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.aleson.example.nasaapodapp.domain.ApodModel;
import com.aleson.example.nasaapodapp.presentation.MainActivityView;
import com.aleson.example.nasaapodapp.presenter.ApodPresenter;
import com.aleson.example.nasaapodapp.repository.task.BitmapService;
import com.aleson.example.nasaapodapp.repository.task.Service;
import com.google.gson.Gson;

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
        attempt++;
        mainActivityView.onLoading(true);
        if(isOnline()) {
            Service service = new Service(mActivity, date, this);
            service.execute();
        }
        else {
            mainActivityView.onFinishLoad();
            mainActivityView.onConnectionError();
        }
    }

    @Override
    public void requestBitamp(String url) {
        mainActivityView.onLoading(true);
        BitmapService bitmapService = new BitmapService(url, this);
        bitmapService.execute();
    }

    @Override
    public void onSucess(String response) {
        Gson gson = new Gson();
        ApodModel model = gson.fromJson(response, ApodModel.class);
        mainActivityView.onFinishLoad();

        if (model.getUrl() == null || model.getCode() == "400") {
            apodPresenter.responseError(model);
        } else {
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
        if(attempt <= 3) {
            requestData(date);
        }
        else {
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
