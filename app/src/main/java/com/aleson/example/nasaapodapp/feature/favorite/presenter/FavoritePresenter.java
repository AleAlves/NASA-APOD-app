package com.aleson.example.nasaapodapp.feature.favorite.presenter;

import com.aleson.example.nasaapodapp.common.callback.BasePresenter;
import com.aleson.example.nasaapodapp.feature.favorite.repository.response.FavoritesResponse;

public interface FavoritePresenter extends BasePresenter {

    void loadFavorite(FavoritesResponse favoritesResponse);

    void emptyFavorite();
}
