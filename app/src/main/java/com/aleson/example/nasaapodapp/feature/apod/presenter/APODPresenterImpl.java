package com.aleson.example.nasaapodapp.feature.apod.presenter;

import com.aleson.example.nasaapodapp.feature.apod.domain.APOD;
import com.aleson.example.nasaapodapp.feature.apod.presentation.APODView;

public class APODPresenterImpl implements APODPresenter {

    private APODView view;

    public APODPresenterImpl(APODView view) {

        this.view = view;
    }

    @Override
    public void showLoading() {

        this.view.showLoading();
    }

    @Override
    public void hideLoading() {

        this.view.hideLoading();
    }

    @Override
    public void loadAPOD(APOD apod) {

        this.view.loadAPOD(apod);
    }

    @Override
    public void onAPODUnavailable(String message) {

        this.view.onAPODUnavailable(message);
    }

    @Override
    public void onError() {

        this.view.onError();
    }

    @Override
    public void onError(String message) {

        this.view.onError(message);
    }
}
