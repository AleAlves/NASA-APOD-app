// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.favorites.presenter;

import android.app.Activity;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.aleson.example.nasaapodapp.favorites.presentation.FavoritesView;
import com.aleson.example.nasaapodapp.favorites.repository.FavoritesRepository;
import com.aleson.example.nasaapodapp.favorites.repository.FavoritesRepositoryImpl;

public class FavoritesPresenterImpl implements FavoritesPresenter{

    private FavoritesView favoritesView;
    private FavoritesRepository favoritesRepository;
    public FavoritesPresenterImpl(Activity mActivity){
        this.favoritesView = (FavoritesView) mActivity;
    }

    @Override
    public void sendRate(Apod apodModel, Device deviceModel, int rate) {
        favoritesRepository = new FavoritesRepositoryImpl(this);
        favoritesRepository.sendRate(apodModel, deviceModel, rate);
    }

    @Override
    public void sendRateStatus(boolean done) {
        favoritesView.showRateStatus(done);
    }
}
