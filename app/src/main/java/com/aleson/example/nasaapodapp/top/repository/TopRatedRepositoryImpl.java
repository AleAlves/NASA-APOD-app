package com.aleson.example.nasaapodapp.top.repository;

import android.util.Log;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.top.domain.TopRatedList;
import com.aleson.example.nasaapodapp.top.presenter.TopRatedPresenterImpl;
import com.aleson.example.nasaapodapp.top.repository.task.TopRatedRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

public class TopRatedRepositoryImpl implements TopRatedRepository {

    private TopRatedPresenterImpl topRatedPresenter;

    public TopRatedRepositoryImpl(TopRatedPresenterImpl topRatedPresenter){
        this.topRatedPresenter = topRatedPresenter;
    }

    @Override
    public void requestTopratedList(String listSize) {
        TopRatedList topRatedList = new TopRatedList();
        topRatedList.setListSize(listSize);
        new TopRatedRequest(this, topRatedList);
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
        Log.i("S","Deu ruim");
        topRatedPresenter.servieError();
    }
}
