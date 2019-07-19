package com.aleson.example.nasaapodapp.feature.apod.repository.api;

import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRateRequest;
import com.aleson.example.nasaapodapp.feature.apod.repository.response.APODRateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APODRateMethod {

    @POST("api/v1/rate")
    Call<APODRateResponse> setAPODRate(@Header("token") String token, @Body APODRateRequest apod);

}
