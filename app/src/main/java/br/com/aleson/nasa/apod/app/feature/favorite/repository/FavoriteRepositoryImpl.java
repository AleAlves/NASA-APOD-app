package br.com.aleson.nasa.apod.app.feature.favorite.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.api.FavoritesMethod;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoritesResponse;
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

                responseCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<FavoritesResponse> call, Throwable t) {

                responseCallback.onFailure(t);
            }
        });
    }
}
