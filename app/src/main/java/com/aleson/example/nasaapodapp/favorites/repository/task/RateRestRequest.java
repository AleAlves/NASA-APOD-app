package com.aleson.example.nasaapodapp.favorites.repository.task;

import android.util.Log;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;
import com.aleson.example.nasaapodapp.favorites.repository.NasaApodClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GAMER on 28/10/2017.
 */

public class RateRestRequest {

    private String API_BASE_URL = "http://192.168.0.11:3000/";

    public RateRestRequest(ApodModel model) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        NasaApodClient client = retrofit.create(NasaApodClient.class);

        Call<ApodModel> call = client.test(model);
        call.enqueue(new Callback<ApodModel>() {
            @Override
            public void onResponse(Call<ApodModel> call, Response<ApodModel> response) {
                Log.i("E",call.request().url().toString());
            }

            @Override
            public void onFailure(Call<ApodModel> call, Throwable t) {
                Log.i("E","error");
            }
        });
    }
}
