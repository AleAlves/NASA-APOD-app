package com.aleson.example.nasaapodapp.topRated.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

import retrofit2.Response;

/**
 * Created by GAMER on 28/10/2017.
 */

public interface TopRatedRepository {

    void requestTopratedList();

    void onServiceSuccess(Response<List<Apod>> apods);

    void onServiceError(String message);
}
