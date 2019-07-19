package com.aleson.example.nasaapodapp.feature.profile.repository;

import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;

public interface ProfileRepository {

    void getService(ResponseCallback callback);

    void deleteAccount(ResponseCallback callback);
}
