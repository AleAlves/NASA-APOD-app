package com.aleson.example.nasaapodapp.presenter;

import android.app.Activity;

import com.aleson.example.nasaapodapp.repository.ApodRepository;
import com.aleson.example.nasaapodapp.repository.ApodRepositoryImpl;

/**
 * Created by Santander on 11/09/17.
 */

public class ApodPresenterImpl implements ApodPresenter {

    private String date;
    private Activity mActivity;
    private ApodRepository apodRepository;

    public ApodPresenterImpl(Activity mActivity, String date){
        this.mActivity = mActivity;
        this.apodRepository = new ApodRepositoryImpl(mActivity);
        this.date = date;
    }

    @Override
    public void getTodayApod() {
        apodRepository.requestData(mActivity, date);
    }

    @Override
    public void getRandomApod(String date) {
        apodRepository.requestData(mActivity, date);
    }

    @Override
    public void getChosenApod(String date) {
        apodRepository.requestData(mActivity, date);
    }
}
