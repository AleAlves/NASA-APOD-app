package br.com.aleson.nasa.apod.app.login.repository;

import android.util.Base64;

import com.github.android.aleson.slogger.SLogger;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.login.domain.AESData;
import br.com.aleson.nasa.apod.app.login.repository.api.PublicKeyMethod;
import br.com.aleson.nasa.apod.app.login.repository.response.PublicKeyResponse;
import br.com.connector.aleson.android.connector.Connector;
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
                responseCallback.onFinish();
            }
        });
    }

    @Override
    public void getTicket(final ResponseCallback responseCallback) {

    }

    @Override
    public void registerLogin(final ResponseCallback responseCallback) {

    }
}
