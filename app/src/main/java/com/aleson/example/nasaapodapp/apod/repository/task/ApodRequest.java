package com.aleson.example.nasaapodapp.apod.repository.task;

/**
 * Created by GAMER on 28/10/2017.
 */

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;
import com.aleson.example.nasaapodapp.apod.repository.ApodRepository;
import com.aleson.example.nasaapodapp.apod.repository.NasaApodApi;
import com.aleson.example.nasaapodapp.utils.EndPoint;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GAMER on 28/10/2017.
 */

public class ApodRequest {

    private ApodRepository apodRepository;

    public ApodRequest(final ApodRepository topRatedRepository) {

        this.apodRepository = topRatedRepository;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(EndPoint.SERVER_API).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        NasaApodApi client = retrofit.create(NasaApodApi.class);

        Call<ApodModel> call = client.getApod();

        call.enqueue(new Callback<ApodModel>() {
            @Override
            public void onResponse(Call<ApodModel> call, Response<ApodModel> response) {
                apodRepository.onSucess(response.body());
            }

            @Override
            public void onFailure(Call<ApodModel> call, Throwable t) {
                apodRepository.onError(t.getMessage());
            }
        });
    }
}

