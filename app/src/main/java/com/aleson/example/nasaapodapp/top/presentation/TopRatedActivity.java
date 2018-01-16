// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.top.presentation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.top.presentation.adapter.TopRatedRecyclerViewAdapter;
import com.aleson.example.nasaapodapp.top.presenter.TopRatedPresenter;
import com.aleson.example.nasaapodapp.top.presenter.TopRatedPresenterImpl;
import com.aleson.example.nasaapodapp.utils.SettingsUtil;

import java.util.List;

public class TopRatedActivity extends AppCompatActivity  implements TopRatedView {

    private Activity mActivity;
    private Context context;
    private TopRatedPresenter topRatedPresenter;
    private RelativeLayout relativeLayoutLaoding;
    private ProgressBar progressBarLoading;
    private TextView textViewServiceError;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated);
        mActivity = this;
        context = this;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Top rated");
        setSupportActionBar(myToolbar);
        SettingsUtil settingsUtil = new SettingsUtil(this, "settings");
        relativeLayoutLaoding = (RelativeLayout) findViewById(R.id.loading_image);
        progressBarLoading = ( ProgressBar) findViewById(R.id.progressbar_loading_image);
        textViewServiceError = (TextView) findViewById(R.id.textview_no_service);
        topRatedPresenter = new TopRatedPresenterImpl(this,
                settingsUtil.getSharedPreferences().getString("topRatedListSize","10"));
    }

    private void adapter(List<Apod> model) {
        recyclerView = (RecyclerView) findViewById(R.id.top_rated_list);
        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new TopRatedRecyclerViewAdapter(context, model, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void loadTopRatedApods(List<Apod> apods) {
        relativeLayoutLaoding.setVisibility(View.GONE);
        adapter(apods);
    }

    @Override
    public void serviceError() {
        textViewServiceError.setVisibility(View.VISIBLE);
        progressBarLoading.setVisibility(View.GONE);
    }
}
