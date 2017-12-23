package com.aleson.example.nasaapodapp.about.repository;

/**
 * Created by Aleson on 23-Dec-17.
 */

public interface AboutRepository {
    void requestServiceVersion();
    void onSucessesServiceVersion(String version);
}
