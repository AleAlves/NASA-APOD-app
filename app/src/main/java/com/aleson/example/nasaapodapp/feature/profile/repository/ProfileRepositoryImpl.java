package com.aleson.example.nasaapodapp.feature.profile.repository;

import com.aleson.example.nasaapodapp.feature.profile.api.DeletecAccountRequest;
import com.aleson.example.nasaapodapp.feature.profile.api.ServiceVersionRequest;
import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;
import com.aleson.example.nasaapodapp.common.response.BaseResponse;
import com.aleson.example.nasaapodapp.common.session.Session;
import com.aleson.example.nasaapodapp.feature.profile.api.ServiceVersionResponse;
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
