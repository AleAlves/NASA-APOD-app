package com.aleson.example.nasaapodapp.topRated.presentation;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

/**
 * Created by GAMER on 28/10/2017.
 */

public interface TopRatedView {
    void loadTopRatedApods(List<Apod> apods);
    void serviceError();
}
