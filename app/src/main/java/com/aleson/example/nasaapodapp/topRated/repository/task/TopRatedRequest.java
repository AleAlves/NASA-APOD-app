package com.aleson.example.nasaapodapp.topRated.repository.task;

/**
 * Created by GAMER on 28/10/2017.
 */

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.topRated.repository.NasaApodTopRated;
import com.aleson.example.nasaapodapp.topRated.repository.TopRatedRepository;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GAMER on 28/10/2017.
 */

public class TopRatedRequest {

    private String API_BASE_URL = "http://192.168.0.11:3000/api/";
    private TopRatedRepository topRatedRepository;

    public TopRatedRequest(final TopRatedRepository topRatedRepository) {

        this.topRatedRepository = topRatedRepository;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        NasaApodTopRated client = retrofit.create(NasaApodTopRated.class);

        Call<List<Apod>> call = client.topRatedList();

        call.enqueue(new Callback<List<Apod>>() {
            @Override
            public void onResponse(Call<List<Apod>> call, Response<List<Apod>> response) {
                topRatedRepository.onServiceSuccess(response);
            }

            @Override
            public void onFailure(Call<List<Apod>> call, Throwable t) {
                topRatedRepository.onServiceError("Error");
            }
        });
    }
}

