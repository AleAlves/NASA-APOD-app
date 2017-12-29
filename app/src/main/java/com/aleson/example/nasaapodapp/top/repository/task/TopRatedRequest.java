package com.aleson.example.nasaapodapp.top.repository.task;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.top.domain.TopRatedList;
import com.aleson.example.nasaapodapp.top.repository.TopRatedRepository;
import com.aleson.example.nasaapodapp.utils.EndPoint;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopRatedRequest {

    private TopRatedRepository topRatedRepository;

    public TopRatedRequest(final TopRatedRepository topRatedRepository, final TopRatedList listSize) {

        this.topRatedRepository = topRatedRepository;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(EndPoint.SERVER_API).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        NasaApodTopRated client = retrofit.create(NasaApodTopRated.class);

        Call<List<Apod>> call = client.topRatedList(listSize);

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

