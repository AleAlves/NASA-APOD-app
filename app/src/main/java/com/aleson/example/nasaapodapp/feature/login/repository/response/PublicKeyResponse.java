package com.aleson.example.nasaapodapp.feature.login.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;

public class PublicKeyResponse extends BaseResponse {

    @Expose
    @SerializedName("publicKey")
    private String publicKey;


    public String getPublicKey() {
        return publicKey;
    }
}
