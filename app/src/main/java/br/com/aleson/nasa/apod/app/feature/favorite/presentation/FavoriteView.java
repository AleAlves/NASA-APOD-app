package br.com.aleson.nasa.apod.app.feature.favorite.presentation;

import br.com.aleson.nasa.apod.app.common.view.BaseView;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoritesResponse;

public interface FavoriteView extends BaseView {

    void loadFavorite(FavoritesResponse favoritesResponse);

    void onError();

    void onError(String message);
}
