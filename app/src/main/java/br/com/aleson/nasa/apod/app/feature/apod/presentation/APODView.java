package br.com.aleson.nasa.apod.app.feature.apod.presentation;

import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.common.view.BaseView;
import br.com.aleson.nasa.apod.app.feature.apod.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRateRequest;

public interface APODView extends BaseView {

    void loadAPOD(APOD apod);

    void rate(APODRateRequest request, FavoriteCallback favoriteCallback);

    void exit();

    void askStoragePermission();

    void onError();

    void onError(String message);
}
