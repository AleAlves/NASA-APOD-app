package br.com.aleson.nasa.apod.app.feature.home.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.home.repository.api.APODMethod;
import br.com.aleson.nasa.apod.app.feature.home.repository.response.APODResponse;
import br.com.aleson.nasa.apod.app.feature.login.repository.response.TokenResponse;
import br.com.connector.aleson.android.connector.Connector;
import br.com.connector.aleson.android.connector.cryptography.domain.Safe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APODRepositoryImpl implements APODRepository {

    public APODRepositoryImpl() {

    }

    @Override
    public void getAPOD(final Safe safeRequest, final ResponseCallback responseCallback) {

        Connector.request().create(APODMethod.class).getAPOD(Session.getInstance().getToken(), safeRequest).enqueue(new Callback<APODResponse>() {
            @Override
            public void onResponse(Call<APODResponse> call, Response<APODResponse> response) {
                responseCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<APODResponse> call, Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }
}
