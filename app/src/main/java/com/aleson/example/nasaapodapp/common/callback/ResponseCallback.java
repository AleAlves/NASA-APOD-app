package com.aleson.example.nasaapodapp.common.callback;

public interface ResponseCallback<T> {

    void onResponse(T response);

    void onFailure(T response);
}
