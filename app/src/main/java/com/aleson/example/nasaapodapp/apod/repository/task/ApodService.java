package com.aleson.example.nasaapodapp.apod.repository.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.aleson.example.nasaapodapp.apod.repository.ApodRepository;
import com.aleson.example.nasaapodapp.utils.Config;

import java.io.IOException;
import java.net.URL;

import livroandroid.lib.utils.HttpHelper;

public class ApodService extends AsyncTask<URL, Integer, String> {

    private Config config;
    private String url;
    private HttpHelper http;
    private String key;
    private String date;
    private ApodRepository apodRepository;


    public ApodService(Activity mainActivityView, String date, ApodRepository apodRepository) {
        config = new Config(mainActivityView);
        this.apodRepository = apodRepository;
        this.date = date;
        this.key = config.getKey();
        this.url = config.getUrl();
        Log.w("CHOOSEN_DATE", date);
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            http = new HttpHelper();
            String json = http.doGet(url + key + "&date=" + date);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s == null) {
            apodRepository.serviceError(date);
        } else {
            if (s.contains("Internal ApodService Error") || s.contains("502 Bad Gateway") || s.contains("<html>")) {
                apodRepository.onError(s);
            } else {
//                apodRepository.onSucess(s);
            }
        }
    }
}
