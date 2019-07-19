package com.aleson.example.nasaapodapp.feature.favorite.presentation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aleson.example.nasaapodapp.feature.favorite.presentation.adapter.FavoritesRecyclerView;
import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.common.domain.DialogMessage;
import com.aleson.example.nasaapodapp.common.view.BaseActivity;
import com.aleson.example.nasaapodapp.feature.favorite.interactor.FavoriteInteractor;
import com.aleson.example.nasaapodapp.feature.favorite.interactor.FavoriteInteractorImpl;
import com.aleson.example.nasaapodapp.feature.favorite.presenter.FavoritePresenterImpl;
import com.aleson.example.nasaapodapp.feature.favorite.repository.FavoriteRepositoryImpl;
import com.aleson.example.nasaapodapp.feature.favorite.repository.response.FavoriteResponse;
import com.aleson.example.nasaapodapp.feature.favorite.repository.response.FavoritesResponse;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends BaseActivity implements FavoriteView {

    private FavoriteInteractor interactor;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FavoritesRecyclerView adapter;
    private ScrollView favoritesScrollView;
    private TextView textViewemptyfavorites;
    private List<FavoriteResponse> favoritesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        init();
    }

    private void init() {

        favoritesScrollView = findViewById(R.id.favorites_scrollview);
        textViewemptyfavorites = findViewById(R.id.textview_favorites_empty);
        recyclerView = findViewById(R.id.favorites_recyclerview);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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

    @Override
    public void onError() {
        showDialog(true);
    }

    @Override
    public void onError(String message) {

        DialogMessage dialogMessage = new DialogMessage();
        showDialog(dialogMessage, false);
    }

    @Override
    public void emptyFavorites() {
        favoritesScrollView.setVisibility(View.GONE);
        textViewemptyfavorites.setVisibility(View.VISIBLE);
    }
}
