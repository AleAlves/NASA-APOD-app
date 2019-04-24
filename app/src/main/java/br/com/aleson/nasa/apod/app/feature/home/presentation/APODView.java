package br.com.aleson.nasa.apod.app.feature.home.presentation;

import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.common.view.BaseView;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.repository.request.APODRateRequest;

public interface APODView extends BaseView {

    void loadAPOD(APOD apod);

    void onError();

    void rate(APODRateRequest request, FavoriteCallback favoriteCallback);

    void exit();
}
