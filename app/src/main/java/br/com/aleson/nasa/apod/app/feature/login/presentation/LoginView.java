package br.com.aleson.nasa.apod.app.feature.login.presentation;

import br.com.aleson.nasa.apod.app.common.view.BaseView;

public interface LoginView extends BaseView {

    void startHome();

    void onError();

    void onError(String message);

}
