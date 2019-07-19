package com.aleson.example.nasaapodapp.feature.favorite.domain;

import com.google.gson.annotations.SerializedName;

public class FavoriteModel {

    @SerializedName("date")
    private String date;


    @SerializedName("pic")
    private String pic;

    public String getDate() {
        return date;
    }

    public String getPic() {
        return pic;
    }
}
