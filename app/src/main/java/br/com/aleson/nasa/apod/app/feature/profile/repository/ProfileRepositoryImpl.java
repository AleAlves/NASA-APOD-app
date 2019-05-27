package br.com.aleson.nasa.apod.app.feature.profile.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.response.BaseResponse;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.profile.api.DeletecAccountRequest;
import br.com.aleson.nasa.apod.app.feature.profile.api.ServiceVersionRequest;
import br.com.aleson.nasa.apod.app.feature.profile.api.ServiceVersionResponse;
import br.com.connector.aleson.android.connector.Connector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepositoryImpl implements ProfileRepository {


    @Override
    public void getService(final ResponseCallback callback) {

        Connector.request().create(ServiceVersionRequest.class).getServiceVersion().enqueue(
                new Callback<ServiceVersionResponse>() {

                    @Override
                    public void onResponse(Call<ServiceVersionResponse> call, Response<ServiceVersionResponse> response) {

                        if (response == null || response.body() == null) {

                            callback.onFailure(response);

                        } else {
                            callback.onResponse(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<ServiceVersionResponse> call, Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    @Override
    public void deleteAccount(final ResponseCallback callback) {

        Connector.request().create(DeletecAccountRequest.class).deleteAccount(
                Session.getInstance().getToken()).enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                if (response == null || response.body() == null) {

                    callback.onFailure(response);

                } else {
                    callback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
