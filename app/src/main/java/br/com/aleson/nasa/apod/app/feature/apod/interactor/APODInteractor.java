package br.com.aleson.nasa.apod.app.feature.apod.interactor;

import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRateRequest;

public interface APODInteractor {

    void getAPOD(String date);

    void favorite(APODRateRequest request, FavoriteCallback favoriteCallback);
}
