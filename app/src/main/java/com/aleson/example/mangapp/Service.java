package com.aleson.example.mangapp;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

import livroandroid.lib.utils.HttpHelper;

/**
 * Created by Santander on 05/09/17.
 */

public class Service extends AsyncTask<URL, Integer, String>{

    String url = "https://api.nasa.gov/planetary/apod?api_key=";
    HttpHelper http;
    MainActivityView mainActivityView;
    String key;
    String date;

    public Service(Activity mainActivityView, String key, String date){
        this.mainActivityView = (MainActivityView) mainActivityView;
        this.key = key;
        this.date = date;
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            http = new HttpHelper();
            String json = http.doGet(url+key+"&date="+date);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        mainActivityView.onSucess(s);
    }
}
