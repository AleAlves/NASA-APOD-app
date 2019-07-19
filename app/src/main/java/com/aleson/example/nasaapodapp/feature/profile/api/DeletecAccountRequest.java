package com.aleson.example.nasaapodapp.feature.profile.api;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;

public interface DeletecAccountRequest {

    @DELETE("api/v1/delete")
    Call<BaseResponse> deleteAccount(@Header("token") String token);
}
