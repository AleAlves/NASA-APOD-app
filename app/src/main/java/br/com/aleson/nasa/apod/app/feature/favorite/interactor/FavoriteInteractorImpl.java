package br.com.aleson.nasa.apod.app.feature.favorite.interactor;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
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

                presenter.loadFavorite((FavoritesResponse) response);
                presenter.hideLoading();
            }

            @Override
            public void onFailure(Object response) {

                presenter.showLoading();
                presenter.hideLoading();
            }
        });
    }
}
