package com.aleson.example.nasaapodapp.feature.login.repository;

import com.aleson.example.nasaapodapp.feature.login.repository.response.TicketResponse;
import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;
import com.aleson.example.nasaapodapp.feature.login.domain.AESData;
import com.aleson.example.nasaapodapp.feature.login.domain.User;

public interface LoginRepository {

    void getPublicKey(ResponseCallback responseCallback);

    void getTicket(AESData aesData, ResponseCallback responseCallback);

    void registerLogin(User user, TicketResponse ticketResponse, ResponseCallback responseCallback);

    void saveToken(String token);

    void verifyToken(ResponseCallback responseCallback);
}
