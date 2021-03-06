package com.aleson.example.nasaapodapp.feature.login.repository;

import android.util.Base64;

import com.aleson.example.nasaapodapp.common.constants.Constants;
import com.aleson.example.nasaapodapp.common.util.StorageHelper;
import com.aleson.example.nasaapodapp.feature.login.domain.AESData;
import com.aleson.example.nasaapodapp.feature.login.domain.User;
import com.aleson.example.nasaapodapp.feature.login.repository.api.LoginMethod;
import com.aleson.example.nasaapodapp.feature.login.repository.api.PublicKeyMethod;
import com.aleson.example.nasaapodapp.feature.login.repository.api.TicketMethod;
import com.aleson.example.nasaapodapp.feature.login.repository.response.PublicKeyResponse;
import com.aleson.example.nasaapodapp.feature.login.repository.response.TicketResponse;
import com.aleson.example.nasaapodapp.feature.login.repository.response.TokenResponse;
import com.github.android.aleson.slogger.SLogger;

import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;
import com.aleson.example.nasaapodapp.common.session.Session;
import br.com.connector.aleson.android.connector.Connector;
import br.com.connector.aleson.android.connector.cryptography.domain.Safe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImpl implements LoginRepository {

    @Override
    public void verifyToken(ResponseCallback responseCallback) {

        responseCallback.onResponse(StorageHelper.readStringData(Constants.TOKEN_KEY));
    }

    @Override
    public void saveToken(String token) {
        StorageHelper.saveData(Constants.TOKEN_KEY, token);
    }

    @Override
    public void getPublicKey(final ResponseCallback responseCallback) {

        Connector.request().create(PublicKeyMethod.class).getPublicKey().enqueue(new Callback<PublicKeyResponse>() {
            @Override
            public void onResponse(Call<PublicKeyResponse> call, Response<PublicKeyResponse> response) {

                SLogger.d(response);

                if (response == null || response.body() == null) {

                    responseCallback.onFailure(response);
                } else {

                    Session.getInstance().setPublicKey(response.body().getPublicKey());

                    AESData AESData = new AESData();
                    AESData.setIv(Base64.encodeToString(Connector.crypto().getAes().getIv(), 0).replace("\n", ""));
                    AESData.setKey(Connector.crypto().getAes().getSecret());
                    AESData.setSalt(Connector.crypto().getAes().getSalt());

                    responseCallback.onResponse(AESData);
                }
            }

            @Override
            public void onFailure(Call<PublicKeyResponse> call, Throwable t) {

                SLogger.d(t);
                responseCallback.onFailure(t);
            }
        });
    }

    @Override
    public void getTicket(AESData aesData, final ResponseCallback responseCallback) {

        Safe safe = new Safe();
        safe.setContent(aesData, Session.getInstance().getPublickKey());

        Connector.request().create(TicketMethod.class).token(safe).enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {

                SLogger.d(response);

                if (response == null || response.body() == null) {

                    responseCallback.onFailure(response);
                } else {

                    responseCallback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<TicketResponse> call, Throwable t) {

                SLogger.d(t);

                responseCallback.onFailure(t);
            }
        });
    }

    @Override
    public void registerLogin(User user, TicketResponse ticketResponse, final ResponseCallback responseCallback) {

        Safe safe = new Safe();
        safe.setContent(user);

        Connector.request().create(LoginMethod.class).login(ticketResponse.getTicket(), safe).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                SLogger.d(response);

                if (response == null || response.body() == null) {

                    responseCallback.onFailure(response);
                } else {

                    responseCallback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

                SLogger.d(t);
                responseCallback.onFailure(t);
            }
        });
    }
}
