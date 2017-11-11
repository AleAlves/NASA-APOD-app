package com.aleson.example.nasaapodapp.favorites.repository;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by GAMER on 28/10/2017.
 */

public interface RateClient {

    @POST("rate")
    Call<String> rate(@Body ApodModel model);
}