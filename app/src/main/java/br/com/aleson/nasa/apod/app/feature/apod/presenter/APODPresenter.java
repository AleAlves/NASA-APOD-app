package br.com.aleson.nasa.apod.app.feature.apod.presenter;

import br.com.aleson.nasa.apod.app.common.view.BasePresenterView;
import br.com.aleson.nasa.apod.app.feature.apod.domain.APOD;

public interface APODPresenter extends BasePresenterView {

    void loadAPOD(APOD apod);
}
