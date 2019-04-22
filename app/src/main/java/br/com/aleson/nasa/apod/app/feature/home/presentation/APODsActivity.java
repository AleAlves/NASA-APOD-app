package br.com.aleson.nasa.apod.app.feature.home.presentation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;

import com.github.android.aleson.slogger.SLogger;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import br.com.aleson.nasa.apod.app.common.util.DateUtil;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.favorite.presentation.FavoriteActivity;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractor;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.APODGestureListener;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.APODRecyclerViewAdapter;
import br.com.aleson.nasa.apod.app.feature.home.presenter.APODPresenterImpl;
import br.com.aleson.nasa.apod.app.feature.home.repository.APODRepositoryImpl;
import br.com.aleson.nasa.apod.app.feature.home.repository.request.APODRateRequest;
import br.com.aleson.nasa.apod.app.feature.profile.ProfileActivity;

public class APODsActivity extends BaseActivity implements APODView, BottomNavigationView.OnNavigationItemSelectedListener {

    private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static int LEFT = 1;
    private static int RIGHT = -1;
    private static int MIDDLE = 0;
    private static int currentAction;

    private Context context;
    private String apodDate;
    private String apodMaxDate;
    private DateUtil dateUtil;
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
        handleExtras(getIntent());
        init();
        initRecyclerView();
        updateDate(MIDDLE);
        dateUtil = new DateUtil(apodDate);
    }

    private void handleExtras(Intent intent) {
        if (intent != null) {
            apodDate = intent.getStringExtra("date");
        }
    }

    private void updateDate(int direction) {

        currentAction = direction;

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
                updateDate(RIGHT);
                return false;
            }

            @Override
            public boolean onSwipeLeft() {
                updateDate(LEFT);
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
        Collections.sort(apodList, new Comparator<APOD>() {
            @Override
            public int compare(APOD apod1, APOD apod2) {
                return apod2.getDate().compareTo(apod1.getDate());
            }
        });
        mAdapter.notifyDataSetChanged();
        if (currentAction > 0)
            recyclerView.scrollToPosition(apodList.size());
        else if (currentAction < 0)
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
                startActivity(new Intent(context, ProfileActivity.class));
                break;
            }
            case R.id.apod_random: {
                clearDataLists();
                apodDate = dateUtil.getRandomDate();
                updateDate(MIDDLE);
                break;
            }
            case R.id.apod_date_range: {
                datePicker();
                break;
            }
            case R.id.apod_fav_list: {
                startActivity(new Intent(context, FavoriteActivity.class));
                break;
            }
        }
        return true;
    }

    private void datePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                context,
                dateListener(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        Date dateFormatInitial = null;
        Date dateFormatFinal = null;
        try {
            dateFormatInitial = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(apodMaxDate);
            dateFormatFinal = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse("16/06/1995");
        } catch (ParseException e) {
            Log.e("Error", e.toString());
        }
        if (dateFormatInitial != null)
            dialog.getDatePicker().setMaxDate(dateFormatInitial.getTime());
        if (dateFormatFinal != null)
            dialog.getDatePicker().setMinDate(dateFormatFinal.getTime());
        dialog.setTitle("data");
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                apodDate = dateUtil.getRequestFormatedDate(year, month, dayOfMonth);
                clearDataLists();
                updateDate(MIDDLE);
            }
        };
    }

    private void clearDataLists() {
        apodList.clear();
        apodDatelist.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}