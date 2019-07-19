package com.aleson.example.nasaapodapp.feature.login.interactor;

import com.aleson.example.nasaapodapp.feature.login.repository.response.TicketResponse;
import com.aleson.example.nasaapodapp.feature.login.domain.AESData;

public interface LoginInteractor {

    void login();

    void getPublicKey();

    void ticket(AESData aesData);

    void token(TicketResponse ticketResponse);
}

