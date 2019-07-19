package com.aleson.example.nasaapodapp.feature.favorite.repository;

import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;
import com.aleson.example.nasaapodapp.common.session.Session;
import com.aleson.example.nasaapodapp.feature.favorite.repository.api.FavoritesMethod;
import com.aleson.example.nasaapodapp.feature.favorite.repository.response.FavoritesResponse;

import br.com.connector.aleson.android.connector.Connector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteRepositoryImpl implements FavoriteRespository {

    @Override
    public void getFavorites(final ResponseCallback responseCallback) {
        Connector.request().create(FavoritesMethod.class).getFavorites(Session.getInstance().getToken()).enqueue(new Callback<FavoritesResponse>() {
            @Override
            public void onResponse(Call<FavoritesResponse> call, Response<FavoritesResponse> response) {

                if (response == null) {

                    responseCallback.onFailure(response);
                } else {

                    responseCallback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<FavoritesResponse> call, Throwable t) {

                responseCallback.onFailure(t);
            }
        });
    }
}
