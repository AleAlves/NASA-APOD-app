package br.com.aleson.nasa.apod.app.feature.home.presentation;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
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

    private static final int APOD_DAY_1_INDEX = 0;

    private RecyclerView recyclerView;

    private APODInteractor interactor;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<APOD> apodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apods);

        init();
    }

    private void init() {
        this.interactor = new APODInteractorImpl(new APODPresenterImpl(this), new APODRepositoryImpl());
        this.interactor.getAPOD("");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void loadAPOD(APOD apod) {
        apodList.add(apod);
        apodList.add(apod);
        recyclerView = findViewById(R.id.act_apod_recyclerview_adapter);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new APODRecyclerViewAdapter(apodList);
        recyclerView.setAdapter(mAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }
}
