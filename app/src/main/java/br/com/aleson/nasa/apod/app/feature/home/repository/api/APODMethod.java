package br.com.aleson.nasa.apod.app.feature.home.repository.api;

import br.com.aleson.nasa.apod.app.feature.home.repository.request.APODRequest;
import br.com.aleson.nasa.apod.app.feature.home.repository.response.APODResponse;
import br.com.connector.aleson.android.connector.cryptography.domain.Safe;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APODMethod {


    @POST("api/v1/apod")
    Call<APODResponse> getAPOD(@Header("token") String token, @Body APODRequest apod);

}
