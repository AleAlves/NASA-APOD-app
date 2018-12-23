// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.favorites.domain;

import java.io.Serializable;

public class Device implements Serializable{

    private int id;
    private String imei;
    private String deviceName;
    private String screenSize;
    private String manufacturer;
    private int rateValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String modelName) {
        this.deviceName = modelName;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getManufactuer() {
        return manufacturer;
    }

    public void setManufactuer(String manufactuer) {
        this.manufacturer = manufactuer;
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rate_value) {
        this.rateValue = rate_value;
    }
}
