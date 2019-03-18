package br.com.aleson.nasa.apod.app.feature.login.presenter;

import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;

public interface LoginPresenter {

    void showLoading();

    void hideLoading();

    void showDialog(DialogMessage dialogMessage);

    void startHome();
}
