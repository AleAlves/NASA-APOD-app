package br.com.aleson.nasa.apod.app.login.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;

public interface LoginRepository {

    void getPublicKey(ResponseCallback responseCallback);

    void getTicket(ResponseCallback responseCallback);

    void registerLogin(ResponseCallback responseCallback);

}
