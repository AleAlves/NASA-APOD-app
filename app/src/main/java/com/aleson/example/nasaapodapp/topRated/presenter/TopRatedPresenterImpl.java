package com.aleson.example.nasaapodapp.topRated.presenter;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.topRated.presentation.TopRatedActivity;
import com.aleson.example.nasaapodapp.topRated.presentation.TopRatedView;
import com.aleson.example.nasaapodapp.topRated.repository.TopRatedRepositoryImpl;

import java.util.List;

/**
 * Created by GAMER on 28/10/2017.
 */

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
}
