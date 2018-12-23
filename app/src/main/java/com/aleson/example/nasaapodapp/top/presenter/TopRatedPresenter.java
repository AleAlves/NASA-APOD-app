// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.top.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

public interface TopRatedPresenter {

    void getTopRatedList(String listSize);
    void setTopRatedList(List<Apod> apods);
    void servieError();
}
