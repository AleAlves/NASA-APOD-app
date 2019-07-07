package br.com.aleson.nasa.apod.app.common.callback;

public interface BasePresenter {

    void showLoading();

    void hideLoading();

    void onError();

    void onError(String message);
}
