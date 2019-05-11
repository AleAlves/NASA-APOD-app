package br.com.aleson.nasa.apod.app.feature.apod.presentation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.constants.Constants;
import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.common.permission.PermissionManager;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.common.util.DateHelper;
import br.com.aleson.nasa.apod.app.common.util.NavigationHelper;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.apod.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.apod.interactor.APODInteractor;
import br.com.aleson.nasa.apod.app.feature.apod.interactor.APODInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.apod.presentation.adapter.APODGestureListener;
import br.com.aleson.nasa.apod.app.feature.apod.presentation.adapter.APODRecyclerViewAdapter;
import br.com.aleson.nasa.apod.app.feature.apod.presenter.APODPresenterImpl;
import br.com.aleson.nasa.apod.app.feature.apod.repository.APODRepositoryImpl;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRateRequest;

public class APODsActivity extends BaseActivity implements APODView, BottomNavigationView.OnNavigationItemSelectedListener {

    private static int currentAction;
    private static int onBackPressedCount;

    private Context context;
    private String apodDate;
    private String apodMaxDate;
    private String lasSuccededDate;
    private DateHelper dateHelper;
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

        updateDate(Constants.SWIPE.IDLE);

        dateHelper = new DateHelper(apodDate);
    }

    private void handleExtras(Intent intent) {

        if (intent != null) {
            apodDate = intent.getStringExtra("date");
        }
    }

    private void updateDate(int direction) {

        currentAction = direction;

        try {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_DATE_FORMAT);
            apodMaxDate = simpleDateFormat.format(calendar.getTime());

            if (apodDate == null) {
                calendar.add(Calendar.DATE, direction);
            } else {
                calendar.setTime(simpleDateFormat.parse(apodDate));
                calendar.add(Calendar.DATE, direction);
            }

            apodDate = simpleDateFormat.format(calendar.getTime());

            if (isValidRange()) {

                searchAPOD();
            } else {

                apodDate = apodMaxDate;
            }

        } catch (Exception e) {
            SLogger.d(e);
        }
    }

    private void initRecyclerView() {

        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        mAdapter = new APODRecyclerViewAdapter(this, apodList, this);
        recyclerView = findViewById(R.id.act_apod_recyclerview_adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));

        mGestureDetector = new GestureDetector(this, new APODGestureListener(recyclerView) {
            @Override
            public boolean onSwipeRight() {

                updateDate(Constants.SWIPE.RIGHT);
                return false;
            }

            @Override
            public boolean onSwipeLeft() {

                updateDate(Constants.SWIPE.LEFT);
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

        try {
            Calendar requestDate = Calendar.getInstance();
            Calendar todayDate = Calendar.getInstance();
            Calendar minimumDate = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE.DEFAULT_DATE_FORMAT);

            requestDate.setTime(simpleDateFormat.parse(apodDate));
            todayDate.setTime(minimumDate.getTime());
            minimumDate.setTime(simpleDateFormat.parse(Constants.BUSINESS.FIRST_APOD));


            if (!requestDate.before(minimumDate) && !requestDate.after(todayDate)) {

                return true;
            }

        } catch (Exception e) {
            SLogger.e(e);
        }
        return false;
    }

    private void searchAPOD() {

        if (!apodDatelist.contains(apodDate)) {

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

        this.lasSuccededDate = apodDate;

        this.apodList.add(apod);

        Collections.sort(apodList, new Comparator<APOD>() {
            @Override
            public int compare(APOD apod1, APOD apodProxy) {
                return apodProxy.getDate().compareTo(apod1.getDate());
            }
        });

        this.mAdapter.notifyDataSetChanged();

        if (currentAction > 0) {

            recyclerView.scrollToPosition(apodList.size());
        } else if (currentAction < 0) {

            recyclerView.scrollToPosition(apodList.size() - 1);
        }
    }

    @Override
    public void onError() {

        showDialog();
        resetDateToPreviusSucessfulRequest();
        updateDate(Constants.SWIPE.IDLE);
    }

    @Override
    public void onError(String message) {

        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.setMessage(message);
        showDialog(dialogMessage, true, new DialogCallback() {
            @Override
            public void onDismiss() {

                resetDateToPreviusSucessfulRequest();
                updateDate(Constants.SWIPE.IDLE);
            }
        });
    }

    @Override
    public void onAPODUnavailable(String message) {

        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.setMessage(message);
        dialogMessage.setNegativeButton(getString(R.string.dialog_random_option));
        showDialog(dialogMessage, true, new DialogCallback.Buttons() {
            @Override
            public void onPositiveAction() {
                //DO nothing
            }

            @Override
            public void onNegativeAction() {

                getRandomAPOD();
            }

            @Override
            public void onDismiss() {

            }
        });
    }

    private void resetDateToPreviusSucessfulRequest() {

        apodDate = lasSuccededDate;
    }

    @Override
    public void rate(APODRateRequest request, FavoriteCallback favoriteCallback) {

        interactor.favorite(request, favoriteCallback);
    }

    @Override
    public void exit() {

        this.finish();
    }

    @Override
    public void askStoragePermission() {

        PermissionManager.askPermissionToStorage(APODsActivity.this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.apod_profile:

                NavigationHelper.navigateProfile();
                break;
            case R.id.apod_random:

                getRandomAPOD();
                break;
            case R.id.apod_date_range:

                datePicker();
                break;
            case R.id.apod_fav_list:

                if (verifyLogged()) {

                    NavigationHelper.navigateFavorites();
                } else {
                    loggInWarn();
                }
                break;
        }
        return true;
    }

    private void datePicker() {

        try {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(
                    context,
                    dateListener(),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            Date dateFormatInitial = new SimpleDateFormat(Constants.DATE.DEFAULT_DATE_FORMAT).parse(apodMaxDate);
            Date dateFormatFinal = new SimpleDateFormat(Constants.DATE.DEFAULT_DATE_FORMAT).parse(Constants.BUSINESS.FIRST_APOD);


            if (dateFormatInitial != null)
                dialog.getDatePicker().setMaxDate(dateFormatInitial.getTime());
            if (dateFormatFinal != null)
                dialog.getDatePicker().setMinDate(dateFormatFinal.getTime());
            dialog.setTitle(getString(R.string.calendar_title));
            dialog.show();

        } catch (ParseException e) {
            SLogger.e(e);
        }
    }

    private DatePickerDialog.OnDateSetListener dateListener() {

        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                apodDate = dateHelper.getRequestFormatedDate(year, month, dayOfMonth);
                clearDataLists();
                updateDate(Constants.SWIPE.IDLE);
            }
        };
    }

    private void getRandomAPOD() {

        clearDataLists();
        apodDate = dateHelper.getRandomDate();
        updateDate(Constants.SWIPE.IDLE);
    }

    private void clearDataLists() {

        apodList.clear();
        apodDatelist.clear();
    }

    private void loggInWarn() {

        DialogMessage message = new DialogMessage();
        message.setMessage(getString(R.string.dialog_message_login_warn));
        message.setPositiveButton(getString(R.string.dialog_button_login_positive));
        message.setNegativeButton(getString(R.string.dialog_button_login_negative));
        showDialog(message, false, new DialogCallback.Buttons() {
            @Override
            public void onPositiveAction() {
                finish();
            }

            @Override
            public void onNegativeAction() {
                //TODO analytics tag
            }

            @Override
            public void onDismiss() {
                //TODO analytics tag
            }
        });
    }

    @Override
    public void onBackPressed() {

        onBackPressedCount++;

        if (Session.getInstance().isLogged()) {

            handleAppExit();
        } else {

            NavigationHelper.navigateLogin();
        }
    }

    private void handleAppExit() {

        if (onBackPressedCount == 1) {

            showToast(getString(R.string.toast_message_leave_action));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onBackPressedCount = 0;
                }
            }, Constants.TIME_LAPSE.DEFAULT);

        } else if (onBackPressedCount == 2) {

            onBackPressedCount = 0;

            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }


    private boolean verifyLogged() {

        return Session.getInstance().isLogged();
    }
}