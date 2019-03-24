package br.com.aleson.nasa.apod.app.feature.home.presenter;

import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.presentation.APODView;

public class APODPresenterImpl implements APODPresenter {

    private APODView view;

    public APODPresenterImpl(APODView view){
        this.view = view;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDialog(DialogMessage message) {

    }

    @Override
    public void loadAPOD(APOD apod) {
        this.view.loadAPOD(apod);
    }
}
