package br.com.aleson.nasa.apod.app.feature.home.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class APODRateResponse {

    @Expose
    @SerializedName("favorite")
    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }
}
