package com.aleson.example.nasaapodapp.topRated.presentation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.topRated.presentation.adapter.RecyclerViewAdapterTopRated;
import com.aleson.example.nasaapodapp.topRated.presenter.TopRatedPresenter;
import com.aleson.example.nasaapodapp.topRated.presenter.TopRatedPresenterImpl;

import java.util.ArrayList;

public class TopRatedActivity extends AppCompatActivity  implements TopRatedView {

    private Activity mActivity;
    private Context context;
    private TopRatedPresenter topRatedPresenter;
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
        setSupportActionBar(myToolbar);
        topRatedPresenter = new TopRatedPresenterImpl();
    }

    private void adapter(ArrayList<Apod> model) {
        recyclerView = (RecyclerView) findViewById(R.id.top_rated_list);
        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapterTopRated(context, model, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void loadTopRatedApods() {

    }
}
