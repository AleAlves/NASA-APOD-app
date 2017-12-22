package com.aleson.example.nasaapodapp.topRated.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NasaApodTopRated {
    @GET("top")
    Call<List<Apod>> topRatedList();
}
