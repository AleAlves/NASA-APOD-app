package com.aleson.example.nasaapodapp.topRated.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

/**
 * Created by GAMER on 28/10/2017.
 */

public interface TopRatedPresenter {

    void getTopRatedList();
    void setTopRatedList(List<Apod> apods);
    void servieError();
}
