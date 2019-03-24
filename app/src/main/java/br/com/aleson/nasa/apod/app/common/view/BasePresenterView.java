package br.com.aleson.nasa.apod.app.common.view;

import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;

public interface BasePresenterView {

    void showLoading();

    void hideLoading();

    void showDialog(DialogMessage message);
}
