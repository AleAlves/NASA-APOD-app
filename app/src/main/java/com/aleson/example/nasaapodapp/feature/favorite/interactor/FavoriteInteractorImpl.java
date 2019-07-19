package com.aleson.example.nasaapodapp.feature.favorite.interactor;

import com.aleson.example.nasaapodapp.feature.favorite.repository.FavoriteRespository;
import com.aleson.example.nasaapodapp.feature.favorite.repository.response.FavoritesResponse;
import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;
import com.aleson.example.nasaapodapp.common.constants.Constants;
import com.aleson.example.nasaapodapp.common.response.BaseResponse;
import com.aleson.example.nasaapodapp.feature.favorite.presenter.FavoritePresenter;

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
                if (response == null) {

                    presenter.onError();

                } else if (((BaseResponse) response).getHttpStatus().getCode() == Constants.HTTP_CODE.SUCCESS) {

                    FavoritesResponse favoritesResponse = (FavoritesResponse) response;

                    if (favoritesResponse == null || favoritesResponse.getFavorites().isEmpty()) {
                        presenter.emptyFavorite();
                    } else {
                        presenter.loadFavorite((FavoritesResponse) response);
                    }
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
