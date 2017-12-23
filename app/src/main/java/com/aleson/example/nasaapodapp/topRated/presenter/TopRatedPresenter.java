package com.aleson.example.nasaapodapp.topRated.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

public interface TopRatedPresenter {

    void getTopRatedList(String listSize);
    void setTopRatedList(List<Apod> apods);
    void servieError();
}
