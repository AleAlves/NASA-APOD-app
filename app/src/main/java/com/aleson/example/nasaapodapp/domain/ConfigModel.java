package com.aleson.example.nasaapodapp.domain;

import com.google.gson.annotations.SerializedName;

public class ConfigModel {


    @SerializedName("url")
    private String url;

    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
