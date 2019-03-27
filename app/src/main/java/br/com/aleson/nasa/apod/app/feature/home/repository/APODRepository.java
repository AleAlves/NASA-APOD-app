package br.com.aleson.nasa.apod.app.feature.home.repository;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.connector.aleson.android.connector.cryptography.domain.Safe;

public interface APODRepository {

    void getAPOD(final Safe safeRequest, ResponseCallback responseCallback);

}
