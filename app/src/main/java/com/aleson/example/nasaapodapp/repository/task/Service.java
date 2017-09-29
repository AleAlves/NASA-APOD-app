package com.aleson.example.nasaapodapp.repository.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.aleson.example.nasaapodapp.repository.ApodRepository;
import com.aleson.example.nasaapodapp.utils.Config;

import java.io.IOException;
import java.net.URL;

import livroandroid.lib.utils.HttpHelper;

public class Service extends AsyncTask<URL, Integer, String> {

    private Config config;
    private String url;
    private HttpHelper http;
    private String key;
    private String date;
    private ApodRepository apodRepository;


    public Service(Activity mainActivityView, String date, ApodRepository apodRepository) {
        config = new Config(mainActivityView);
        this.apodRepository = apodRepository;
        this.date = date;
        this.key = config.getKey();
        this.url = config.getUrl();
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
            apodRepository.serviceError();
        } else {
            if (s.contains("Internal Service Error") || s.contains("502 Bad Gateway")) {
                apodRepository.onError(s);
            } else {
                apodRepository.onSucess(s);
            }
        }
    }
}
