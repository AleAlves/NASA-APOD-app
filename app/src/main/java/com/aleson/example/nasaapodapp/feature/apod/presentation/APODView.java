package com.aleson.example.nasaapodapp.feature.apod.presentation;

import com.aleson.example.nasaapodapp.common.callback.FavoriteCallback;
import com.aleson.example.nasaapodapp.common.view.BaseView;
import com.aleson.example.nasaapodapp.feature.apod.domain.APOD;
import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRateRequest;

public interface APODView extends BaseView {

    void loadAPOD(APOD apod);

    void rate(APODRateRequest request, FavoriteCallback favoriteCallback);

    void exit();

    void askStoragePermission();

    void onError();

    void onError(String message);

    void onAPODUnavailable(String message);
}
