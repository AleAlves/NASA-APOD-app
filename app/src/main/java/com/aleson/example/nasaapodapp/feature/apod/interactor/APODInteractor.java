package com.aleson.example.nasaapodapp.feature.apod.interactor;

import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRateRequest;
import com.aleson.example.nasaapodapp.common.callback.FavoriteCallback;

public interface APODInteractor {

    void getAPOD(String date);

    void favorite(APODRateRequest request, FavoriteCallback favoriteCallback);
}
