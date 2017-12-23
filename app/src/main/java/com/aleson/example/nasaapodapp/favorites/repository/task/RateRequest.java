package com.aleson.example.nasaapodapp.favorites.repository.task;

import android.util.Log;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;
import com.aleson.example.nasaapodapp.utils.EndPoint;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RateRequest {

    public RateRequest(ApodModel model) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(EndPoint.SERVER_API).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        RateClient client = retrofit.create(RateClient.class);

        Call<String> call = client.rate(model);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("E",call.request().url().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("E","error");
            }
        });
    }
}
