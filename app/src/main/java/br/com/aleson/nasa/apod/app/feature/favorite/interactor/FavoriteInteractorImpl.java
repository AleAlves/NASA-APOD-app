package br.com.aleson.nasa.apod.app.feature.favorite.interactor;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.constants.Constants;
import br.com.aleson.nasa.apod.app.common.response.BaseResponse;
import br.com.aleson.nasa.apod.app.feature.favorite.presenter.FavoritePresenter;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.FavoriteRespository;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoritesResponse;

public class FavoriteInteractorImpl implements FavoriteInteractor {

    private FavoritePresenter presenter;
    private FavoriteRespository respository;

    public FavoriteInteractorImpl(FavoritePresenter presenter, FavoriteRespository respository) {

        this.presenter = presenter;
        this.respository = respository;
    }

    @Override
    public void getFavorites() {

        this.presenter.showLoading();

        this.respository.getFavorites(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                if (((BaseResponse) response).getHttpStatus().getCode() == Constants.HTTP_CODE.SUCCESS) {

                    presenter.loadFavorite((FavoritesResponse) response);

                } else {

                    presenter.onError(((BaseResponse) response).getHttpStatus().getStatus());
                }
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
