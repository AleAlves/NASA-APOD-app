package br.com.aleson.nasa.apod.app.feature.home.presentation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.github.android.aleson.slogger.SLogger;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.Constants;
import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractor;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.APODGestureListener;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.APODRecyclerViewAdapter;
import br.com.aleson.nasa.apod.app.feature.home.presenter.APODPresenterImpl;
import br.com.aleson.nasa.apod.app.feature.home.repository.APODRepositoryImpl;
import br.com.aleson.nasa.apod.app.feature.home.repository.request.APODRateRequest;

public class APODsActivity extends BaseActivity implements APODView, BottomNavigationView.OnNavigationItemSelectedListener {

    private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private Context context;
    private String apodDate;
    private String apodMaxDate;
    private APODInteractor interactor;
    private RecyclerView recyclerView;
    private APODRecyclerViewAdapter mAdapter;
    private GestureDetector mGestureDetector;
    private RecyclerView.LayoutManager layoutManager;
    private List<APOD> apodList = new ArrayList<>();
    private List<String> apodDatelist = new ArrayList<>();
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_apods);
        getSupportActionBar().hide();
        init();
        initRecyclerView();
        updateDate(0);
    }

    private void updateDate(int direction) {

        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            apodMaxDate = simpleDateFormat.format(c.getTime());
            if (apodDate == null) {
                c.add(Calendar.DATE, direction);
            } else {
                c.setTime(simpleDateFormat.parse(apodDate));
                c.add(Calendar.DATE, direction);
            }

            apodDate = simpleDateFormat.format(c.getTime());

            if (isValidRange()) {
                Log.d("SWIPE", apodDate + " " + apodList.size());
                searchAPOD();
            } else {
                apodDate = apodMaxDate;
            }

        } catch (Exception e) {
            SLogger.d(e);
        }
    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.act_apod_recyclerview_adapter);
        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new APODRecyclerViewAdapter(this, apodList, this);
        recyclerView.setAdapter(mAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        mGestureDetector = new GestureDetector(this, new APODGestureListener(recyclerView) {
            @Override
            public boolean onSwipeRight() {
                updateDate(-1);
                return false;
            }

            @Override
            public boolean onSwipeLeft() {
                updateDate(1);
                return false;
            }

            @Override
            public boolean onTouch() {
                return false;
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    private boolean isValidRange() {

        Calendar requestDate = Calendar.getInstance();
        Calendar todayDate = Calendar.getInstance();
        Calendar minimumDate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            requestDate.setTime(simpleDateFormat.parse(apodDate));
            todayDate.setTime(minimumDate.getTime());
            minimumDate.setTime(simpleDateFormat.parse(Constants.APOD.FIRST_APOD));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!requestDate.before(minimumDate) && !requestDate.after(todayDate)) {
            return true;
        }
        return false;
    }

    private void searchAPOD() {

        if (apodDatelist.contains(apodDate)) {
            SLogger.d(apodDate + " Already searched");
        } else {
            apodDatelist.add(apodDate);
            interactor.getAPOD(apodDate);
        }
    }

    private void init() {

        navigationView = findViewById(R.id.bottom_navigation_apod_menu);
        navigationView.setOnNavigationItemSelectedListener(this);

        this.interactor = new APODInteractorImpl(new APODPresenterImpl(this), new APODRepositoryImpl());
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    public void loadAPOD(APOD apod) {

        apodList.add(apod);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(apodList.size() - 1);
    }

    @Override
    public void onError() {

    }

    @Override
    public void rate(String date, String pic) {

    }

    @Override
    public void rate(APODRateRequest request, FavoriteCallback favoriteCallback) {
        interactor.favorite(request, favoriteCallback);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.apod_account: {
                break;
            }
            case R.id.apod_random: {
                break;
            }
            case R.id.apod_date_range: {
                break;
            }
            case R.id.apod_fav_list: {
                break;
            }
        }
        return true;
    }
}