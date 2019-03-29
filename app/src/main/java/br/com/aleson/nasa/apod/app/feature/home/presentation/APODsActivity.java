package br.com.aleson.nasa.apod.app.feature.home.presentation;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractor;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.APODRecyclerViewAdapter;
import br.com.aleson.nasa.apod.app.feature.home.presenter.APODPresenterImpl;
import br.com.aleson.nasa.apod.app.feature.home.repository.APODRepositoryImpl;

public class APODsActivity extends BaseActivity implements APODView {

    private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private RecyclerView recyclerView;

    private Context context;
    private APODInteractor interactor;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<APOD> apodList = new ArrayList<>();
    private String apodDate;
    private int mLoadedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_apods);
        getSupportActionBar().hide();
        updateDate();
        init();
        initRecyclerView();
    }

    private void updateDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -mLoadedItems);
        apodDate = new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(c.getTime());
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.act_apod_recyclerview_adapter);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new APODRecyclerViewAdapter(this, apodList);
        recyclerView.setAdapter(mAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                updateDate();
                interactor.getAPOD(apodDate);
            }
        });
    }

    private void init() {
        this.interactor = new APODInteractorImpl(new APODPresenterImpl(this), new APODRepositoryImpl());
        this.interactor.getAPOD(apodDate);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void loadAPOD(APOD apod) {
        apodList.add(apod);
        mLoadedItems++;
        mAdapter.notifyDataSetChanged();
    }
}
