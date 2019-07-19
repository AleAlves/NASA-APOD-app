package com.aleson.example.nasaapodapp.feature.apod.repository.response;

import com.aleson.example.nasaapodapp.feature.apod.domain.APOD;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;

public class APODResponse  extends BaseResponse {

    @Expose
    @SerializedName("apod")
    private APOD apod;

    public APOD getApod() {
        return apod;
    }

    public void setApod(APOD apod) {
        this.apod = apod;
    }
}
