// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.apod.domain;

import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.aleson.example.nasaapodapp.favorites.domain.Rate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApodModel {

    @SerializedName("apod")
    @Expose
    private Apod apod;
    @SerializedName("device")
    @Expose
    private Device device;

    @SerializedName("rate")
    @Expose
    private Rate rate;

    public Apod getApod() {
        return apod;
    }

    public void setApod(Apod apod) {
        this.apod = apod;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

}
