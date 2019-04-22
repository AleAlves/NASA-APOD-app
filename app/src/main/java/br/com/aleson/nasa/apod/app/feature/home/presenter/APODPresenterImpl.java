package br.com.aleson.nasa.apod.app.feature.home.presenter;

import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.presentation.APODView;

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
    public void showDialog(DialogMessage message) {
        if (message == null) {
            this.view.showDialog();
        } else {
            this.view.showDialog(message, false);
        }
    }

    @Override
    public void loadAPOD(APOD apod) {
        this.view.loadAPOD(apod);
    }

    @Override
    public void onError() {
        this.view.onError();
    }
}
