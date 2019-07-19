package com.aleson.example.nasaapodapp.feature.apod.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;


public class APODRateResponse extends BaseResponse {

    @Expose
    @SerializedName("favorite")
    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }
}
