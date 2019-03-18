package br.com.aleson.nasa.apod.app.feature.login.repository.api;

import br.com.aleson.nasa.apod.app.feature.login.repository.response.PublicKeyResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PublicKeyMethod {

    @GET("api/v1/init")
    Call<PublicKeyResponse> getPublicKey();
}
