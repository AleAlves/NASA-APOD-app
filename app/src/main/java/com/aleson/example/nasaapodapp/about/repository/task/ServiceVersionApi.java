package com.aleson.example.nasaapodapp.about.repository.task;

import com.aleson.example.nasaapodapp.about.domain.ServiceVersion;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Aleson on 23-Dec-17.
 */

public interface ServiceVersionApi {
    @GET("version")
    Call<ServiceVersion> version();
}
