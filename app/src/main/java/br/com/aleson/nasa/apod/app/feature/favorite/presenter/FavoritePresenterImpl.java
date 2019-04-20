package br.com.aleson.nasa.apod.app.feature.favorite.presenter;

import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.feature.favorite.presentation.FavoriteView;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoritesResponse;

public class FavoritePresenterImpl implements FavoritePresenter {

    private FavoriteView view;

    public FavoritePresenterImpl(FavoriteView view) {
        this.view = view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDialog(DialogMessage message) {

    }

    @Override
    public void loadFavorite(FavoritesResponse favoritesResponse) {
        this.view.loadFavorite(favoritesResponse);
    }
}
