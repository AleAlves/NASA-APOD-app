package br.com.aleson.nasa.apod.app.feature.favorite.presentation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.favorite.interactor.FavoriteInteractor;
import br.com.aleson.nasa.apod.app.feature.favorite.interactor.FavoriteInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.favorite.presentation.adapter.FavoritesRecyclerView;
import br.com.aleson.nasa.apod.app.feature.favorite.presenter.FavoritePresenterImpl;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.FavoriteRepositoryImpl;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoriteResponse;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoritesResponse;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends BaseActivity implements FavoriteView {

    private FavoriteInteractor interactor;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FavoritesRecyclerView adapter;
    private List<FavoriteResponse> favoritesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        init();
    }

    private void init() {

        recyclerView = findViewById(R.id.favorites_recyclerview);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FavoritesRecyclerView(this, favoritesList);
        recyclerView.setAdapter(adapter);

        interactor = new FavoriteInteractorImpl(new FavoritePresenterImpl(this), new FavoriteRepositoryImpl());
        interactor.getFavorites();
    }

    @Override
    public void loadFavorite(FavoritesResponse favoritesResponse) {
        favoritesList.addAll(favoritesResponse.getFavorites());
        adapter.notifyDataSetChanged();

    }
}
