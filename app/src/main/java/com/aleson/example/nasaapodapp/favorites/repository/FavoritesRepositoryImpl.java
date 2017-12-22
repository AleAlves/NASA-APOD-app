package com.aleson.example.nasaapodapp.favorites.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.domain.ApodModel;
import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.aleson.example.nasaapodapp.favorites.domain.Rate;
import com.aleson.example.nasaapodapp.favorites.repository.task.RateRequest;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    public FavoritesRepositoryImpl(){

    }

    @Override
    public void sendRate(Apod apod, Device device, int rateValue) {
        Rate rate = new Rate();
        rate.setDeviceId(device.getImei());
        rate.setRateValue(rateValue);
        ApodModel apodModel = new ApodModel();
        apodModel.setApod(apod);
        apodModel.setDevice(device);
        apodModel.setRate(rate);
        new RateRequest(apodModel);
    }
}
