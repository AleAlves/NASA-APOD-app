package br.com.aleson.nasa.apod.app.feature.apod.interactor;

import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.apod.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.apod.presenter.APODPresenter;
import br.com.aleson.nasa.apod.app.feature.apod.repository.APODRepository;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRateRequest;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRequest;
import br.com.aleson.nasa.apod.app.feature.apod.repository.response.APODRateResponse;
import br.com.aleson.nasa.apod.app.feature.apod.repository.response.APODResponse;

import static br.com.aleson.nasa.apod.app.common.constants.Constants.HTTP_CODE.SUCCESS;
import static br.com.aleson.nasa.apod.app.common.constants.Constants.HTTP_CODE.UNAVAILABLE_APOD;

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

                APODResponse apod = ((APODResponse) response);

                if (SUCCESS == apod.getHttpStatus().getCode()) {

                    if (Session.getInstance().isLogged()) {

                        getAPODRate(request, apod.getApod());
                    } else {

                        presenter.loadAPOD(apod.getApod());
                        presenter.hideLoading();
                    }
                } else {

                    if (UNAVAILABLE_APOD == apod.getHttpStatus().getCode()) {

                        presenter.onAPODUnavailable(apod.getHttpStatus().getStatus());
                    } else {

                        presenter.onError();
                    }
                    presenter.hideLoading();
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

    private void getAPODRate(APODRequest request, final APOD apod) {

        this.repository.getAPODRate(request, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

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
