package br.com.aleson.nasa.apod.app.feature.favorite.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.aleson.nasa.apod.app.common.response.BaseResponse;

public class FavoritesResponse extends BaseResponse {

    @Expose
    @SerializedName("favorites")
    private List<FavoriteResponse> favorites;

    public List<FavoriteResponse> getFavorites() {
        return favorites;
    }
}
