package com.aleson.example.nasaapodapp.feature.apod.repository.api;

import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRequest;
import com.aleson.example.nasaapodapp.feature.apod.repository.response.APODRateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APODFavoritesMethod {

    @POST("api/v1/favorite")
    Call<APODRateResponse> getAPODRateStatus(@Header("token") String token, @Body APODRequest apod);

}
