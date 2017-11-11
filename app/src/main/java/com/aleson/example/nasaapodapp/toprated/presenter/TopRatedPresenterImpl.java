package com.aleson.example.nasaapodapp.toprated.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.toprated.presentation.TopRatedActivity;
import com.aleson.example.nasaapodapp.toprated.presentation.TopRatedView;
import com.aleson.example.nasaapodapp.toprated.repository.TopRatedRepositoryImpl;

import java.util.List;

public class TopRatedPresenterImpl implements TopRatedPresenter {

    TopRatedRepositoryImpl topRatedRepository;
    TopRatedView topRatedView;

    public TopRatedPresenterImpl(TopRatedActivity topRatedActivity){
        topRatedRepository = new TopRatedRepositoryImpl(this);
        topRatedView = (TopRatedView) topRatedActivity;
        getTopRatedList();
    }

    @Override
    public void getTopRatedList() {
        topRatedRepository.requestTopratedList();
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
