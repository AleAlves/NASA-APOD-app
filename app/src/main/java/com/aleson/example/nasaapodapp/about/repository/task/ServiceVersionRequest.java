// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.about.repository.task;

import android.util.Log;

import com.aleson.example.nasaapodapp.about.domain.ServiceVersion;
import com.aleson.example.nasaapodapp.about.repository.AboutRepository;
import com.aleson.example.nasaapodapp.utils.EndPoint;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceVersionRequest {

    private AboutRepository aboutRepository;

    public ServiceVersionRequest(final AboutRepository aboutRepository) {

        this.aboutRepository = aboutRepository;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(EndPoint.SERVER_API).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        ServiceVersionApi client = retrofit.create(ServiceVersionApi.class);

        Call<ServiceVersion> call = client.version();
        call.enqueue(new Callback<ServiceVersion>() {
            @Override
            public void onResponse(Call<ServiceVersion> call, Response<ServiceVersion> response) {
                Log.i("E",call.request().url().toString());
                aboutRepository.onSucessesServiceVersion(response.body().getVersion());
            }

            @Override
            public void onFailure(Call<ServiceVersion> call, Throwable t) {
                Log.i("E","error");
            }
        });
    }

}
