package br.com.aleson.nasa.apod.app.feature.favorite.presenter;

import br.com.aleson.nasa.apod.app.common.view.BasePresenterView;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoritesResponse;

public interface FavoritePresenter extends BasePresenterView {

    void loadFavorite(FavoritesResponse favoritesResponse);
}
