package com.aleson.example.nasaapodapp.apod.presenter;

import android.app.Activity;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.domain.Media;
import com.aleson.example.nasaapodapp.apod.presentation.MainActivityView;
import com.aleson.example.nasaapodapp.apod.repository.ApodRepository;
import com.aleson.example.nasaapodapp.apod.repository.ApodRepositoryImpl;

public class ApodPresenterImpl implements ApodPresenter {

    private String date;
    private ApodRepository apodRepository;
    private MainActivityView mainActivityView;
    private int mediaType;


    public ApodPresenterImpl(Activity mActivity, String date) {
        this.apodRepository = new ApodRepositoryImpl(mActivity, this);
        mainActivityView = (MainActivityView) mActivity;
        this.date = date;
    }

    @Override
    public void getTodayApod() {
        apodRepository.requestData(date);
    }

    @Override
    public void getRandomApod(String date) {
        this.date = date;
        apodRepository.requestData(date);
    }

    @Override
    public void getChosenApod(String date) {
        apodRepository.requestData(date);
    }

    @Override
    public void responseSucess(Apod model) {
        if (model.getUrl().contains(".jpg") || model.getUrl().contains(".jpeg") || model.getUrl().contains(".png")) {
            this.mediaType = Media.IMAGE;
        } else if (model.getUrl().contains(".gif")) {
            this.mediaType = Media.GIF;
        } else {
            this.mediaType = Media.VIDEO;
        }
        mainActivityView.onFinishLoad();
        mainActivityView.setContent(model);
    }

    @Override
    public void responseError(Apod model) {
        mainActivityView.onError("");
    }

    @Override
    public void chooseWallpaper(String url) {
        apodRepository.requestBitamp(url);
    }

    @Override
    public int getMediaType() {
        return mediaType;
    }

    @Override
    public String getDataSelecionada() {
        return date;
    }
}
