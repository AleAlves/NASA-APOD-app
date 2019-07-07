package br.com.aleson.nasa.apod.app.feature.favorite.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;

public interface FavoriteRespository {

    void getFavorites(ResponseCallback responseCallback);
}
