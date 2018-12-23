// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.top.presentation;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.util.List;

public interface TopRatedView {
    void loadTopRatedApods(List<Apod> apods);
    void serviceError();
}
