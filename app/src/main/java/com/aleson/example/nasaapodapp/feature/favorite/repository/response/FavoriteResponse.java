package com.aleson.example.nasaapodapp.feature.favorite.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteResponse {

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("pic")
    private String pic;

    @Expose
    @SerializedName("title")
    private String title;

    public String getDate() {
        return date;
    }

    public String getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }
}
