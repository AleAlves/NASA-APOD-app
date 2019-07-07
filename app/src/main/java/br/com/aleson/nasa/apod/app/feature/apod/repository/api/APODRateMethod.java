package br.com.aleson.nasa.apod.app.feature.apod.repository.api;

import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRateRequest;
import br.com.aleson.nasa.apod.app.feature.apod.repository.response.APODRateResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APODRateMethod {

    @POST("api/v1/rate")
    Call<APODRateResponse> setAPODRate(@Header("token") String token, @Body APODRateRequest apod);

}
