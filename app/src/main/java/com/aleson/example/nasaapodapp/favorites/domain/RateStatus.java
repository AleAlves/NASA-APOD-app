package com.aleson.example.nasaapodapp.favorites.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateStatus {

    @SerializedName("done")
    @Expose
    private String done;

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }
}
