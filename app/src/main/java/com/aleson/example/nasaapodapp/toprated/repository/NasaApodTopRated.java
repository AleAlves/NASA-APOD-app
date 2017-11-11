package com.aleson.example.nasaapodapp.toprated.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NasaApodTopRated {
    @GET("topRated")
    Call<List<Apod>> topRatedList();
}
