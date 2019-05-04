package br.com.aleson.nasa.apod.app.feature.apod.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.apod.repository.api.APODMethod;
import br.com.aleson.nasa.apod.app.feature.apod.repository.api.APODFavoritesMethod;
import br.com.aleson.nasa.apod.app.feature.apod.repository.api.APODRateMethod;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRateRequest;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRequest;
import br.com.aleson.nasa.apod.app.feature.apod.repository.response.APODRateResponse;
import br.com.aleson.nasa.apod.app.feature.apod.repository.response.APODResponse;
import br.com.connector.aleson.android.connector.Connector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APODRepositoryImpl implements APODRepository {

    public APODRepositoryImpl() {

    }

    @Override
    public void getAPOD(APODRequest request, final ResponseCallback responseCallback) {
        Connector.request().create(APODMethod.class).getAPOD(Session.getInstance().getToken(), request).enqueue(new Callback<APODResponse>() {
            @Override
            public void onResponse(Call<APODResponse> call, Response<APODResponse> response) {

                if (response == null || response.body() == null) {

                    responseCallback.onFailure(response);
                } else {

                    responseCallback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<APODResponse> call, Throwable t) {
                responseCallback.onFailure(t);
            }
        });
    }

    @Override
    public void getAPODRate(APODRequest request, final ResponseCallback responseCallback) {
        Connector.request().create(APODFavoritesMethod.class).getAPODRateStatus(Session.getInstance().getToken(), request).enqueue(new Callback<APODRateResponse>() {
            @Override
            public void onResponse(Call<APODRateResponse> call, Response<APODRateResponse> response) {

                if (response == null || response.body() == null) {

                    responseCallback.onFailure(response);
                } else {

                    responseCallback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<APODRateResponse> call, Throwable t) {

                responseCallback.onFailure(t);
            }
        });
    }

    @Override
    public void setAPODRate(APODRateRequest request, final ResponseCallback responseCallback) {
        Connector.request().create(APODRateMethod.class).setAPODRate(Session.getInstance().getToken(), request).enqueue(new Callback<APODRateResponse>() {
            @Override
            public void onResponse(Call<APODRateResponse> call, Response<APODRateResponse> response) {

                if (response == null || response.body() == null) {

                    responseCallback.onFailure(response);
                } else {

                    responseCallback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<APODRateResponse> call, Throwable t) {

                responseCallback.onFailure(t);
            }
        });
    }
}
