package com.aleson.example.nasaapodapp.feature.login.presentation;

import com.aleson.example.nasaapodapp.common.view.BaseView;

public interface LoginView extends BaseView {

    void startHome();

    void onError();

    void onError(String message);

}
