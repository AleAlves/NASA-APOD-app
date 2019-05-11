package br.com.aleson.nasa.apod.app.feature.about.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceVersionRequest {

    @GET("api/v1/version")
    Call<ServiceVersionResponse> getServiceVersion();
}
