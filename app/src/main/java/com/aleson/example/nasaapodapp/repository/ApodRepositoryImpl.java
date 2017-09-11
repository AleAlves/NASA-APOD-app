package com.aleson.example.nasaapodapp.repository;

import android.app.Activity;

import com.aleson.example.nasaapodapp.domain.ApodModel;
import com.aleson.example.nasaapodapp.presentation.MainActivityView;
import com.aleson.example.nasaapodapp.repository.task.Service;
import com.google.gson.Gson;

public class ApodRepositoryImpl implements ApodRepository {

    private Activity mActivity;
    private MainActivityView mainActivityView;

    public ApodRepositoryImpl(Activity mActivity){
        this.mActivity = mActivity;
        mainActivityView = (MainActivityView) mActivity;
    }

    @Override
    public void requestData(String date) {
        Service service = new Service(mActivity, date, this);
        service.execute();
    }

    @Override
    public void onSucess(String response) {
        Gson gson = new Gson();
        ApodModel model = gson.fromJson(response, ApodModel.class);
        mainActivityView.onFinishLoad();
        if(model.getUrl().contains(".gif")){
            mainActivityView.loadGif(model);
        }
        else {
            if(model.getUrl().contains(".jpg") || model.getUrl().contains(".jpeg") || model.getUrl().contains(".png")){
                mainActivityView.loadImage(model);
            }
            else{
                mainActivityView.loadVideo(model);
            }
        }
    }

    @Override
    public void onError(String response) {
        mainActivityView.onFinishLoad();
        mainActivityView.onError("");
    }

    @Override
    public void serviceError() {
        mainActivityView.onFinishLoad();
        mainActivityView.onServiceError();
    }
}
