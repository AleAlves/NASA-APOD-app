package com.aleson.example.nasaapodapp.repository.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.aleson.example.nasaapodapp.repository.ApodRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Santander on 28/09/17.
 */

public class BitmapService  extends AsyncTask<URL, Integer, Bitmap> {

    private String src;
    private ApodRepository apodRepository;

    public BitmapService(String url, ApodRepository apodRepository){
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
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        apodRepository.setWallpaper(bitmap);
    }
}
