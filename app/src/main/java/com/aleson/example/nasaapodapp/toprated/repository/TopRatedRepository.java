package com.aleson.example.nasaapodapp.toprated.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

import retrofit2.Response;

public interface TopRatedRepository {

    void requestTopratedList();

    void onServiceSuccess(Response<List<Apod>> apods);

    void onServiceError(String message);
}
