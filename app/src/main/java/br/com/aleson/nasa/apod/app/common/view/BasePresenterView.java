package br.com.aleson.nasa.apod.app.common.view;

public interface BasePresenterView {

    void showLoading();

    void hideLoading();

    void onError();

    void onError(String message);
}
