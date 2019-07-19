package com.aleson.example.nasaapodapp.feature.apod.repository;

import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;
import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRateRequest;
import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRequest;

public interface APODRepository {

    void getAPOD(final APODRequest request, ResponseCallback responseCallback);

    void getAPODRate(final APODRequest request, ResponseCallback responseCallback);

    void setAPODRate(final APODRateRequest request, ResponseCallback responseCallback);
}
