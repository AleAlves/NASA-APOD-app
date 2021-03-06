package com.aleson.example.nasaapodapp.feature.favorite.presenter;

import com.aleson.example.nasaapodapp.feature.favorite.presentation.FavoriteView;
import com.aleson.example.nasaapodapp.feature.favorite.repository.response.FavoritesResponse;

public class FavoritePresenterImpl implements FavoritePresenter {

    private FavoriteView view;

    public FavoritePresenterImpl(FavoriteView view) {
        this.view = view;
    }

    @Override
    public void showLoading() {
        this.view.showLoading();
    }

    @Override
    public void hideLoading() {
        this.view.hideLoading();
    }

    @Override
    public void onError() {
        this.view.showDialog(true);
    }

    @Override
    public void onError(String message) {
        this.view.showDialog(true);
    }

    @Override
    public void loadFavorite(FavoritesResponse favoritesResponse) {
        this.view.loadFavorite(favoritesResponse);
    }

    @Override
    public void emptyFavorite() {
        this.view.emptyFavorites();
    }
}
