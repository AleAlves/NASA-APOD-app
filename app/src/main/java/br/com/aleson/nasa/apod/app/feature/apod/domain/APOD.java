package br.com.aleson.nasa.apod.app.feature.apod.domain;

import com.google.gson.annotations.SerializedName;

public class APOD {


    @SerializedName("copyright")
    private String copyright;

    @SerializedName("date")
    private String date;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("hdurl")
    private String hdurl;

    @SerializedName("media_type")
    private String media_type;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    private boolean like;

    public boolean isFavorite() {
        return like;
    }

    public void setFavorite(boolean empty) {
        this.like = empty;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}