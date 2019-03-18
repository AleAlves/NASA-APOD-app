package br.com.aleson.nasa.apod.app.login.repository.api;

import br.com.aleson.nasa.apod.app.login.repository.response.TokenResponse;
import br.com.connector.aleson.android.connector.cryptography.domain.Safe;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginMethod {

    @POST("api/v1/login")
    Call<TokenResponse> login(@Header("ticket") String ticket, @Body Safe user);
}
