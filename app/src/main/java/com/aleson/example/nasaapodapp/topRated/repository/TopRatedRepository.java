package com.aleson.example.nasaapodapp.topRated.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

import retrofit2.Response;

public interface TopRatedRepository {

    void requestTopratedList(String listSize);

    void onServiceSuccess(Response<List<Apod>> apods);

    void onServiceError(String message);
}
