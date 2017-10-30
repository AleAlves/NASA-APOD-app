package com.aleson.example.nasaapodapp.apod.repository;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by GAMER on 28/10/2017.
 */

public interface NasaApodApi {
    @GET("api")
    Call<ApodModel> getApod();
}