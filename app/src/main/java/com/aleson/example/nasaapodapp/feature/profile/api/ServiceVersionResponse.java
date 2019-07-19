package com.aleson.example.nasaapodapp.feature.profile.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;

public class ServiceVersionResponse extends BaseResponse {

    @Expose
    @SerializedName("version")
    private String version;

    public String getVersion() {
        return version;
    }
}
