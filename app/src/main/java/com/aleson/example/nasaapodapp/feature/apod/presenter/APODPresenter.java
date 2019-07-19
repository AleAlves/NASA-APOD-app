package com.aleson.example.nasaapodapp.feature.apod.presenter;

import com.aleson.example.nasaapodapp.common.callback.BasePresenter;
import com.aleson.example.nasaapodapp.feature.apod.domain.APOD;

public interface APODPresenter extends BasePresenter {

    void loadAPOD(APOD apod);

    void onAPODUnavailable(String message);

}
