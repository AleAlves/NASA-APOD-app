package com.aleson.example.nasaapodapp.feature.favorite.repository;

import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;

public interface FavoriteRespository {

    void getFavorites(ResponseCallback responseCallback);
}
