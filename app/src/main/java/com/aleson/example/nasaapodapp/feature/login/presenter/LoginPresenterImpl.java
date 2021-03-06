package com.aleson.example.nasaapodapp.feature.login.presenter;

import com.aleson.example.nasaapodapp.feature.login.presentation.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;

    public LoginPresenterImpl(LoginView view) {

        this.view = view;
        this.view.hideLoading();
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
    public void onError() {

        this.view.onError();
    }

    @Override
    public void onError(String message) {

        this.view.onError(message);
    }

    @Override
    public void startHome() {

        this.view.startHome();
    }
}
