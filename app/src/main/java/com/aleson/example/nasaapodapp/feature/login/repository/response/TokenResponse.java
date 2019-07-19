package com.aleson.example.nasaapodapp.feature.login.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;

public class TokenResponse extends BaseResponse {

    @Expose
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
