package com.aleson.example.nasaapodapp.topRated.repository.task;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.topRated.domain.TopRatedList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NasaApodTopRated {
    @POST("top")
    Call<List<Apod>> topRatedList(@Body TopRatedList list);
}
