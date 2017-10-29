package com.aleson.example.nasaapodapp.topRated.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by GAMER on 28/10/2017.
 */

public interface NasaApodTopRated {
    @GET("topRated")
    Call<List<Apod>> topRatedList();
}
