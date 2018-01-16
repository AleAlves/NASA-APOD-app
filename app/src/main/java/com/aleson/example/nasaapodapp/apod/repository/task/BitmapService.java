// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.apod.repository.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.aleson.example.nasaapodapp.apod.repository.ApodRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapService extends AsyncTask<URL, Integer, Bitmap> {

    private String src;
    private ApodRepository apodRepository;

    public BitmapService(String url, ApodRepository apodRepository) {
        this.src = url;
        this.apodRepository = apodRepository;
    }

    @Override
    protected Bitmap doInBackground(URL... params) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            Log.e("Error", e.toString());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        apodRepository.setWallpaper(bitmap);
    }
}
