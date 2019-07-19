package com.aleson.example.nasaapodapp.feature.login.repository.api;

import com.aleson.example.nasaapodapp.feature.login.repository.response.PublicKeyResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PublicKeyMethod {

    @GET("api/v1/init")
    Call<PublicKeyResponse> getPublicKey();
}
