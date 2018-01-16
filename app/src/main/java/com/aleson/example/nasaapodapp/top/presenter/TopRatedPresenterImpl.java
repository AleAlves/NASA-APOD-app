// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.top.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.top.presentation.TopRatedActivity;
import com.aleson.example.nasaapodapp.top.presentation.TopRatedView;
import com.aleson.example.nasaapodapp.top.repository.TopRatedRepositoryImpl;

import java.util.List;

public class TopRatedPresenterImpl implements TopRatedPresenter {

    TopRatedRepositoryImpl topRatedRepository;
    TopRatedView topRatedView;

    public TopRatedPresenterImpl(TopRatedActivity topRatedActivity, String listSize){
        topRatedRepository = new TopRatedRepositoryImpl(this);
        topRatedView = (TopRatedView) topRatedActivity;
        getTopRatedList(listSize);
    }

    @Override
    public void getTopRatedList(String listSize) {
        topRatedRepository.requestTopratedList(listSize);
    }

    @Override
    public void setTopRatedList(List<Apod> apods) {
        topRatedView.loadTopRatedApods(apods);
    }

    @Override
    public void servieError() {
        topRatedView.serviceError();
    }
}
