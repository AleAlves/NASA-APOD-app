package br.com.aleson.nasa.apod.app.login.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.login.domain.AESData;
import br.com.aleson.nasa.apod.app.login.domain.User;
import br.com.aleson.nasa.apod.app.login.repository.response.TicketResponse;

public interface LoginRepository {

    void getPublicKey(ResponseCallback responseCallback);

    void getTicket(AESData aesData, ResponseCallback responseCallback);

    void registerLogin(User user, TicketResponse ticketResponse, ResponseCallback responseCallback);

}
