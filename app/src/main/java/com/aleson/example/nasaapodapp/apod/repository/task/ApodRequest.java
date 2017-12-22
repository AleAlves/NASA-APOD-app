package com.aleson.example.nasaapodapp.apod.repository.task;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.repository.ApodRepository;
import com.aleson.example.nasaapodapp.utils.EndPoint;
import com.aleson.example.nasaapodapp.utils.Keys;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApodRequest {

    private ApodRepository apodRepository;

    public ApodRequest(final ApodRepository topRatedRepository, String date) {

        this.apodRepository = topRatedRepository;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(EndPoint.APOD_API).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        NasaApodApi client = retrofit.create(NasaApodApi.class);

        Call<Apod> call = client.getApod(Keys.API_KEY, date);

        call.enqueue(new Callback<Apod>() {
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                if (response == null || response.code() == 404 || response.code() == 500) {
                    apodRepository.onError(String.valueOf(response.code()));
                } else {
                    if (response.code() == 400) {
                        apodRepository.badRequest(String.valueOf(response.code()));
                    } else {
                        if (response.body() == null) {
                            apodRepository.onError(String.valueOf(response.code()));
                        } else {
                            apodRepository.onSucess(response.body());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                apodRepository.onError(t.getMessage());
            }
        });
    }
}

