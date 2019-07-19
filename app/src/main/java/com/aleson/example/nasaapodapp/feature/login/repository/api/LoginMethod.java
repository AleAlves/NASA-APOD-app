package com.aleson.example.nasaapodapp.feature.login.repository.api;

import com.aleson.example.nasaapodapp.feature.login.repository.response.TokenResponse;

import br.com.connector.aleson.android.connector.cryptography.domain.Safe;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginMethod {

    @POST("api/v1/login")
    Call<TokenResponse> login(@Header("ticket") String ticket, @Body Safe user);
}
