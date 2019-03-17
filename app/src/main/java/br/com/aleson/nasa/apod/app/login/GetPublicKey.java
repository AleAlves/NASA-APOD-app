package br.com.aleson.nasa.apod.app.login;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetPublicKey {

    @GET("api/v1/init")
    Call<PublicKeyResponse> getPublicKey();
}
