package br.com.aleson.nasa.apod.app.feature.login.interactor;

import br.com.aleson.nasa.apod.app.feature.login.domain.AESData;
import br.com.aleson.nasa.apod.app.feature.login.repository.response.TicketResponse;

public interface LoginInteractor {

    void login();

    void ticket(AESData aesData);

    void token(TicketResponse ticketResponse);
}

