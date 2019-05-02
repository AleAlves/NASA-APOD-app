package br.com.aleson.nasa.apod.app.feature.apod.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.aleson.nasa.apod.app.common.response.BaseResponse;


public class APODRateResponse extends BaseResponse {

    @Expose
    @SerializedName("favorite")
    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }
}