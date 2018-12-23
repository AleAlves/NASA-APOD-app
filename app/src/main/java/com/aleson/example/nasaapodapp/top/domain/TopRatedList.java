// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.top.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
