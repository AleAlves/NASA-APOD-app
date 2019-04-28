package br.com.aleson.nasa.apod.app.feature.home.presenter;

import br.com.aleson.nasa.apod.app.common.view.BasePresenterView;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;

public interface APODPresenter extends BasePresenterView {

    void loadAPOD(APOD apod);

    void onError();

    void onError(String message);
}
