package com.aleson.example.nasaapodapp.feature.apod.interactor;

import com.aleson.example.nasaapodapp.feature.apod.repository.response.APODRateResponse;
import com.aleson.example.nasaapodapp.common.callback.FavoriteCallback;
import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;
import com.aleson.example.nasaapodapp.common.session.Session;
import com.aleson.example.nasaapodapp.feature.apod.domain.APOD;
import com.aleson.example.nasaapodapp.feature.apod.presenter.APODPresenter;
import com.aleson.example.nasaapodapp.feature.apod.repository.APODRepository;
import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRateRequest;
import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRequest;
import com.aleson.example.nasaapodapp.feature.apod.repository.response.APODResponse;

import static com.aleson.example.nasaapodapp.common.constants.Constants.HTTP_CODE.SUCCESS;
import static com.aleson.example.nasaapodapp.common.constants.Constants.HTTP_CODE.UNAVAILABLE_APOD;

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
