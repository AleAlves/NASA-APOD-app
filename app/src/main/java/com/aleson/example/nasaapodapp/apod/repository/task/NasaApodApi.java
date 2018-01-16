// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.apod.repository.task;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApodApi {
    @GET("apod")
    Call<Apod> getApod(@Query("api_key") String name, @Query("date") String classs);
}
