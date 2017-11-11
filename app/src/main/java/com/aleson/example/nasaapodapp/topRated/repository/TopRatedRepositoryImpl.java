package com.aleson.example.nasaapodapp.topRated.repository;

import android.util.Log;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.topRated.presenter.TopRatedPresenterImpl;
import com.aleson.example.nasaapodapp.topRated.repository.task.TopRatedRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class TopRatedRepositoryImpl implements TopRatedRepository {

    private TopRatedPresenterImpl topRatedPresenter;

    public TopRatedRepositoryImpl(TopRatedPresenterImpl topRatedPresenter){
        this.topRatedPresenter = topRatedPresenter;
    }

    @Override
    public void requestTopratedList() {
        new TopRatedRequest(this);
    }

    @Override
    public void onServiceSuccess(Response<List<Apod>> apods) {
        List<Apod> apodList = new ArrayList<>();
        for (Apod apod : apods.body()){
            apodList.add(apod);
        }
        topRatedPresenter.setTopRatedList(apodList);
    }

    @Override
    public void onServiceError(String message) {
        Log.i("S","Deu boruim");
        topRatedPresenter.servieError();
    }
}
