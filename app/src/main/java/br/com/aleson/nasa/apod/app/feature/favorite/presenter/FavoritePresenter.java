package br.com.aleson.nasa.apod.app.feature.favorite.presenter;

import br.com.aleson.nasa.apod.app.common.callback.BasePresenter;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoritesResponse;

public interface FavoritePresenter extends BasePresenter {

    void loadFavorite(FavoritesResponse favoritesResponse);
}
