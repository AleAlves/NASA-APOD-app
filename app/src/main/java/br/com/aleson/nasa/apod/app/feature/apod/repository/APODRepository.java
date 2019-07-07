package br.com.aleson.nasa.apod.app.feature.apod.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRateRequest;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRequest;

public interface APODRepository {

    void getAPOD(final APODRequest request, ResponseCallback responseCallback);

    void getAPODRate(final APODRequest request, ResponseCallback responseCallback);

    void setAPODRate(final APODRateRequest request, ResponseCallback responseCallback);
}
