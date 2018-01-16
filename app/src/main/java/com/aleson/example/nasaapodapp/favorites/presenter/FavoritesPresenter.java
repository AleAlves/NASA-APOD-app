// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.favorites.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.favorites.domain.Device;

/**
 * Created by GAMER on 27/10/2017.
 */

public interface FavoritesPresenter {

    void sendRate(Apod apodModel, Device deviceModel, int rate);
}
