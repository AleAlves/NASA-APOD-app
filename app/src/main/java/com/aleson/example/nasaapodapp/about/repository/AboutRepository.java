// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.about.repository;

public interface AboutRepository {
    void requestServiceVersion();
    void onSucessesServiceVersion(String version);
}
