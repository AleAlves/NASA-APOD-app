package br.com.aleson.nasa.apod.app.feature.home.presentation;

import br.com.aleson.nasa.apod.app.common.view.BaseView;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;

public interface APODView extends BaseView {

    void loadAPOD(APOD apod);

}
