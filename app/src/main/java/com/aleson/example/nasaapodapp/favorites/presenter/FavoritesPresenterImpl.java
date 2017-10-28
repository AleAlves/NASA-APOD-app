package com.aleson.example.nasaapodapp.favorites.presenter;

import android.app.Activity;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.aleson.example.nasaapodapp.favorites.repository.FavoritesRepository;
import com.aleson.example.nasaapodapp.favorites.repository.FavoritesRepositoryImpl;

/**
 * Created by GAMER on 27/10/2017.
 */

public class FavoritesPresenterImpl implements FavoritesPresenter{

    private Activity mActivity;
    private FavoritesRepository favoritesRepository;
    public FavoritesPresenterImpl(Activity mActivity){
        this.mActivity = mActivity;
    }

    @Override
    public void sendRate(Apod apodModel, Device deviceModel) {
        favoritesRepository = new FavoritesRepositoryImpl();
        favoritesRepository.sendRate(apodModel, deviceModel);
    }
}
