package com.aleson.example.nasaapodapp.top.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleson on 23-Dec-17.
 */

public class TopRatedList {

    @SerializedName("listSize")
    @Expose
    private String listSize;

    public String getListSize() {
        return listSize;
    }

    public void setListSize(String listSize) {
        this.listSize = listSize;
    }
}
