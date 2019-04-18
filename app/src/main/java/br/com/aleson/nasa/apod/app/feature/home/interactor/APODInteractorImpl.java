package br.com.aleson.nasa.apod.app.feature.home.interactor;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.presenter.APODPresenter;
import br.com.aleson.nasa.apod.app.feature.home.repository.APODRepository;
import br.com.aleson.nasa.apod.app.feature.home.repository.request.APODRequest;
import br.com.aleson.nasa.apod.app.feature.home.repository.response.APODResponse;
import br.com.connector.aleson.android.connector.cryptography.domain.Safe;

public class APODInteractorImpl implements APODInteractor {

    private APODPresenter presenter;
    private APODRepository repository;

    public APODInteractorImpl(APODPresenter presenter, APODRepository repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    @Override
    public void getAPOD(String date) {
        this.presenter.showLoading();

        APODRequest request = new APODRequest();
        request.setDate(date);

        this.repository.getAPOD(request, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {
                presenter.loadAPOD(((APODResponse) response).getApod());
                presenter.hideLoading();
            }

            @Override
            public void onFailure(Object response) {
                presenter.onError();
                presenter.hideLoading();
            }
        });
    }
}
