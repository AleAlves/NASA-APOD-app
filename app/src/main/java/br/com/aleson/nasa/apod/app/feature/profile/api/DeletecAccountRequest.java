package br.com.aleson.nasa.apod.app.feature.profile.api;

import br.com.aleson.nasa.apod.app.common.response.BaseResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;

public interface DeletecAccountRequest {

    @DELETE("api/v1/delete")
    Call<BaseResponse> deleteAccount(@Header("token") String token);
}
