
// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.favorites.repository.task;

import android.util.Log;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;
import com.aleson.example.nasaapodapp.favorites.domain.RateStatus;
import com.aleson.example.nasaapodapp.favorites.repository.FavoritesRepository;
import com.aleson.example.nasaapodapp.utils.EndPoint;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RateRequest {

    public RateRequest(final FavoritesRepository favoritesRepository, ApodModel model) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(EndPoint.SERVER_API).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        RateClient client = retrofit.create(RateClient.class);

        Call<RateStatus> call = client.rate(model);
        call.enqueue(new Callback<RateStatus>() {
            @Override
            public void onResponse(Call<RateStatus> call, Response<RateStatus> response) {
                favoritesRepository.receiveRateStatus(response.body().getDone());
            }

            @Override
            public void onFailure(Call<RateStatus> call, Throwable t) {
                Log.e("","");
            }
        });
    }
}
