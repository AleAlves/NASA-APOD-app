package com.aleson.example.nasaapodapp.presenter;

import android.app.Activity;

import com.aleson.example.nasaapodapp.domain.ApodModel;
import com.aleson.example.nasaapodapp.presentation.MainActivityView;
import com.aleson.example.nasaapodapp.repository.ApodRepository;
import com.aleson.example.nasaapodapp.repository.ApodRepositoryImpl;

public class ApodPresenterImpl implements ApodPresenter {

    private String date;
    private ApodRepository apodRepository;
    private MainActivityView mainActivityView;
    private MEDIA mediaType;

    public enum MEDIA {

        IMAGE(1),
        GIF(2),
        VIDEO(3);

        public int media;
        MEDIA(int valor) {
            media = valor;
        }
    }

    public ApodPresenterImpl(Activity mActivity, String date){
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
        apodRepository.requestData(date);
    }

    @Override
    public void getChosenApod(String date) {
        apodRepository.requestData(date);
    }

    @Override
    public void responseSucess(ApodModel model) {
        if(model.getUrl().contains(".gif")){
            this.mediaType = MEDIA.GIF;
        } else {
            if (model.getUrl().contains(".jpg") || model.getUrl().contains(".jpeg") || model.getUrl().contains(".png")) {
                this.mediaType = MEDIA.IMAGE;
            }
            else{
                this.mediaType = MEDIA.VIDEO;
            }
        }
        mainActivityView.loadImage(model);
    }

    @Override
    public void responseError(ApodModel model) {
        mainActivityView.onError("");
    }

    @Override
    public MEDIA getMediaType() {
        return mediaType;
    }
}
