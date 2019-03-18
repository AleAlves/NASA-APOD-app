package br.com.aleson.nasa.apod.app.common.callback;

public interface ResponseCallback<T> {

    void onResponse(T response);

    void onFailure(T response);
}
