package com.aleson.example.nasaapodapp.feature.favorite.presentation;

import com.aleson.example.nasaapodapp.common.view.BaseView;
import com.aleson.example.nasaapodapp.feature.favorite.repository.response.FavoritesResponse;

public interface FavoriteView extends BaseView {

    void loadFavorite(FavoritesResponse favoritesResponse);

    void onError();

    void onError(String message);

    void emptyFavorites();
}
