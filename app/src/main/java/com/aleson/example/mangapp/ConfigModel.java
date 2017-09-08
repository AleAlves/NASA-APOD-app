package com.aleson.example.mangapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Santander on 05/09/17.
 */

public class ConfigModel {
    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
