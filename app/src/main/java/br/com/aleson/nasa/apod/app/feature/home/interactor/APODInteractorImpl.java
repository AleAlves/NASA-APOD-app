package br.com.aleson.nasa.apod.app.feature.home.interactor;

import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.presenter.APODPresenter;
import br.com.aleson.nasa.apod.app.feature.home.repository.APODRepository;
import br.com.aleson.nasa.apod.app.feature.home.repository.request.APODRateRequest;
import br.com.aleson.nasa.apod.app.feature.home.repository.request.APODRequest;
import br.com.aleson.nasa.apod.app.feature.home.repository.response.APODRateResponse;
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

        final APODRequest request = new APODRequest();
        request.setDate(date);

        this.repository.getAPOD(request, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                if (Session.getInstance().getToken() == null) {

                    presenter.loadAPOD(((APODResponse) response).getApod());
                    presenter.hideLoading();
                } else {

                    getAPODRate(request, response);
                }
            }

            @Override
            public void onFailure(Object response) {

                presenter.onError();
                presenter.hideLoading();
            }
        });
    }

    @Override
    public void favorite(APODRateRequest request, final FavoriteCallback favoriteCallback) {

        this.repository.setAPODRate(request, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                APODRateResponse apodRateResponse = (APODRateResponse) response;

                favoriteCallback.status(apodRateResponse.isFavorite());
            }

            @Override
            public void onFailure(Object response) {

                favoriteCallback.status(false);
            }
        });
    }

    private void getAPODRate(APODRequest request, final Object APODResponse) {

        this.repository.getAPODRate(request, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                APOD apod = ((APODResponse) APODResponse).getApod();
                apod.setFavorite(((APODRateResponse) response).isFavorite());

                presenter.loadAPOD(apod);
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
