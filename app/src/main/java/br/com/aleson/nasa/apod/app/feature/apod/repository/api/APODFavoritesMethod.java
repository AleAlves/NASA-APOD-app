package br.com.aleson.nasa.apod.app.feature.apod.repository.api;

import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRequest;
import br.com.aleson.nasa.apod.app.feature.apod.repository.response.APODRateResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APODFavoritesMethod {

    @POST("api/v1/favorite")
    Call<APODRateResponse> getAPODRateStatus(@Header("token") String token, @Body APODRequest apod);

}
