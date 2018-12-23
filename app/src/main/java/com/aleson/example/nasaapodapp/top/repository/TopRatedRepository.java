// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.top.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

import retrofit2.Response;

public interface TopRatedRepository {

    void requestTopratedList(String listSize);

    void onServiceSuccess(Response<List<Apod>> apods);

    void onServiceError(String message);
}
