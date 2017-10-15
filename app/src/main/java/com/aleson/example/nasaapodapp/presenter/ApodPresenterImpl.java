package com.aleson.example.nasaapodapp.presenter;

import android.app.Activity;

import com.aleson.example.nasaapodapp.domain.ApodModel;
import com.aleson.example.nasaapodapp.domain.Media;
import com.aleson.example.nasaapodapp.presentation.MainActivityView;
import com.aleson.example.nasaapodapp.repository.ApodRepository;
import com.aleson.example.nasaapodapp.repository.ApodRepositoryImpl;

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
    public void responseSucess(ApodModel model) {
        if (model.getUrl().contains(".jpg") || model.getUrl().contains(".jpeg") || model.getUrl().contains(".png")) {
            this.mediaType = Media.IMAGE;
        } else if (model.getUrl().contains(".gif")) {
            this.mediaType = Media.GIF;
        } else {
            this.mediaType = Media.VIDEO;
        }
        mainActivityView.setContent(model);
    }

    @Override
    public void responseError(ApodModel model) {
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
