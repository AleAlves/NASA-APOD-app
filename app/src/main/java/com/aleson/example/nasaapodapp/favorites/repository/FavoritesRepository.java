package com.aleson.example.nasaapodapp.favorites.repository;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.favorites.domain.Device;

/**
 * Created by Santander on 27/10/17.
 */

public interface FavoritesRepository {
    void sendRate(Apod apod, Device device, int rate);
}
