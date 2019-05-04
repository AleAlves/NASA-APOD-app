package br.com.aleson.nasa.apod.app.feature.apod.presenter;

import br.com.aleson.nasa.apod.app.common.callback.BasePresenter;
import br.com.aleson.nasa.apod.app.feature.apod.domain.APOD;

public interface APODPresenter extends BasePresenter {

    void loadAPOD(APOD apod);
}
