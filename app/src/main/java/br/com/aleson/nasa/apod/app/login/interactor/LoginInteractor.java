package br.com.aleson.nasa.apod.app.login.interactor;

import br.com.aleson.nasa.apod.app.login.domain.AESData;
import br.com.aleson.nasa.apod.app.login.repository.response.TicketResponse;

public interface LoginInteractor {

    void login();

    void ticket(AESData aesData);

    void token(TicketResponse ticketResponse);
}

