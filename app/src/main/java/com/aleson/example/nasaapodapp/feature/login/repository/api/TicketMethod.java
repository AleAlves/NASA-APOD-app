package com.aleson.example.nasaapodapp.feature.login.repository.api;

import com.aleson.example.nasaapodapp.feature.login.repository.response.TicketResponse;

import br.com.connector.aleson.android.connector.cryptography.domain.Safe;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TicketMethod {

    @POST("api/v1/ticket")
    Call<TicketResponse> token(@Body Safe safe);

}
