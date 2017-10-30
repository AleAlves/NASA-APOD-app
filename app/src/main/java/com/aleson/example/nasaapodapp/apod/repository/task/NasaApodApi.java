package com.aleson.example.nasaapodapp.apod.repository.task;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by GAMER on 28/10/2017.
 */

public interface NasaApodApi {
    @GET("apod")
    Call<Apod> getApod(@Query("api_key") String name, @Query("date") String classs);
}