package com.aleson.example.nasaapodapp.apod.domain;

import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApodModel {

    @SerializedName("apod")
    @Expose
    private Apod apod;
    @SerializedName("device")
    @Expose
    private Device device;

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

}