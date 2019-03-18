package br.com.aleson.nasa.apod.app.login.repository;

import android.util.Base64;

import com.github.android.aleson.slogger.SLogger;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.login.domain.AESData;
import br.com.aleson.nasa.apod.app.login.domain.User;
import br.com.aleson.nasa.apod.app.login.repository.api.LoginMethod;
import br.com.aleson.nasa.apod.app.login.repository.api.PublicKeyMethod;
import br.com.aleson.nasa.apod.app.login.repository.api.TicketMethod;
import br.com.aleson.nasa.apod.app.login.repository.response.PublicKeyResponse;
import br.com.aleson.nasa.apod.app.login.repository.response.TicketResponse;
import br.com.aleson.nasa.apod.app.login.repository.response.TokenResponse;
import br.com.connector.aleson.android.connector.Connector;
import br.com.connector.aleson.android.connector.cryptography.domain.Safe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepositoryImpl implements LoginRepository {

    @Override
    public void getPublicKey(final ResponseCallback responseCallback) {

        Connector.request().create(PublicKeyMethod.class).getPublicKey().enqueue(new Callback<PublicKeyResponse>() {
            @Override
            public void onResponse(Call<PublicKeyResponse> call, Response<PublicKeyResponse> response) {
                SLogger.d(response);

                Session.getInstance().setPublicKey(response.body().getPublicKey());

                AESData AESData = new AESData();
                AESData.setIv(Base64.encodeToString(Connector.crypto().getAes().getIv(), 0).replace("\n", ""));
                AESData.setKey(Connector.crypto().getAes().getSecret());
                AESData.setSalt(Connector.crypto().getAes().getSalt());

                responseCallback.onResponse(AESData);
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
                responseCallback.onResponse(response.body());
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

                responseCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                SLogger.d(t);
                responseCallback.onFailure(t);
            }
        });
    }
}
