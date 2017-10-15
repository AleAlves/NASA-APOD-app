package com.aleson.example.nasaapodapp.apod.domain;

import com.google.gson.annotations.SerializedName;

public class ConfigModel {

    @SerializedName("url")
    private String url;

    @SerializedName("key")
    private String key;


    @SerializedName("logcat")
    private String logcat;

    public String getLogcat() {
        return logcat;
    }

    public void setLogcat(String logcat) {
        this.logcat = logcat;
    }

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
