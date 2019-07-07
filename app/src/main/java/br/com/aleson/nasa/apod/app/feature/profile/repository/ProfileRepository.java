package br.com.aleson.nasa.apod.app.feature.profile.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;

public interface ProfileRepository {

    void getService(ResponseCallback callback);

    void deleteAccount(ResponseCallback callback);
}
