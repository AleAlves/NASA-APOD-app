package com.aleson.example.nasaapodapp.feature.apod.repository.api;

import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRequest;
import com.aleson.example.nasaapodapp.feature.apod.repository.response.APODResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APODMethod {


    @POST("api/v1/apod")
    Call<APODResponse> getAPOD(@Header("token") String token, @Body APODRequest apod);

}
