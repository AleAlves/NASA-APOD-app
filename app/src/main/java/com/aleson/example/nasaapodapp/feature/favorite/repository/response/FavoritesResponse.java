package com.aleson.example.nasaapodapp.feature.favorite.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;

public class FavoritesResponse extends BaseResponse {

    @Expose
    @SerializedName("favorites")
    private List<FavoriteResponse> favorites;

    public List<FavoriteResponse> getFavorites() {
        return favorites;
    }
}
