package com.aleson.example.nasaapodapp.repository;

import android.app.Activity;
import android.graphics.Bitmap;

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

    public ApodRepositoryImpl(Activity mActivity, ApodPresenter apodPresenter) {
        this.mActivity = mActivity;
        mainActivityView = (MainActivityView) mActivity;
        this.apodPresenter = apodPresenter;
    }

    @Override
    public void requestData(String date) {
        mainActivityView.onLoading(true);
        Service service = new Service(mActivity, date, this);
        service.execute();
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
    public void serviceError() {
        mainActivityView.onFinishLoad();
        mainActivityView.onServiceError();
    }

    @Override
    public void setWallpaper(Bitmap bitmap) {
        mainActivityView.onFinishLoad();
        mainActivityView.setWallpaper(bitmap);
    }
}
