package com.aleson.example.nasaapodapp.favorites.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleson on 1/17/2018.
 */

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
