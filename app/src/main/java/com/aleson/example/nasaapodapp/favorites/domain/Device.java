package com.aleson.example.nasaapodapp.favorites.domain;

import java.io.Serializable;

/**
 * Created by GAMER on 27/10/2017.
 */

public class Device implements Serializable{

    private int id;
    private String imei;
    private String modelName;
    private String screenSize;
    private String manufactuer;
    private int rate_value;

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

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getManufactuer() {
        return manufactuer;
    }

    public void setManufactuer(String manufactuer) {
        this.manufactuer = manufactuer;
    }

    public int getRate_value() {
        return rate_value;
    }

    public void setRate_value(int rate_value) {
        this.rate_value = rate_value;
    }
}
