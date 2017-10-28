package com.aleson.example.nasaapodapp.favorites.repository;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by GAMER on 28/10/2017.
 */

public interface NasaApodClient {
    @PUT("test")
    Call<String> test(@Body ApodModel model);

    @PUT("rate")
    Call<String> rate(@Body ApodModel model);

    @GET("top")
    Call<ApodModel> top();
}