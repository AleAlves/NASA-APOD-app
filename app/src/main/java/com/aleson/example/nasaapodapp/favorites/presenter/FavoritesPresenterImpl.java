// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.favorites.presenter;

import android.app.Activity;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.aleson.example.nasaapodapp.favorites.repository.FavoritesRepository;
import com.aleson.example.nasaapodapp.favorites.repository.FavoritesRepositoryImpl;

public class FavoritesPresenterImpl implements FavoritesPresenter{

    private Activity mActivity;
    private FavoritesRepository favoritesRepository;
    public FavoritesPresenterImpl(Activity mActivity){
        this.mActivity = mActivity;
    }

    @Override
    public void sendRate(Apod apodModel, Device deviceModel, int rate) {
        favoritesRepository = new FavoritesRepositoryImpl();
        favoritesRepository.sendRate(apodModel, deviceModel, rate);
    }
}
