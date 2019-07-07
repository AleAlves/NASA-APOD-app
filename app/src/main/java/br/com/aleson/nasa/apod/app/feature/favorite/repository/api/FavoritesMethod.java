package br.com.aleson.nasa.apod.app.feature.favorite.repository.api;

import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoritesResponse;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FavoritesMethod {

    @POST("api/v1/favorites")
    Call<FavoritesResponse> getFavorites(@Header("token") String token);
}
