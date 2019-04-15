package br.com.aleson.nasa.apod.app.feature.home.presentation;

import android.content.Context;
import android.os.Bundle;

import com.github.android.aleson.slogger.SLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.Constants;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractor;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.APODCarousel;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.APODRecyclerViewAdapter;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.EndlessRecyclerOnSwipeListener;
import br.com.aleson.nasa.apod.app.feature.home.presenter.APODPresenterImpl;
import br.com.aleson.nasa.apod.app.feature.home.repository.APODRepositoryImpl;

public class APODsActivity extends BaseActivity implements APODView {

    private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private RecyclerView recyclerView;

    private Context context;
    private APODInteractor interactor;
    private RecyclerView.LayoutManager layoutManager;
    private List<APOD> apodList = new ArrayList<>();
    private String apodDate;
    private String apodMaxDate;
    private List<String> apodDatelist = new ArrayList<>();
    private APODCarousel apodCarousel;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_apods);
        getSupportActionBar().hide();
        updateDate(0);
        init();
        initRecyclerView();
    }

    private void updateDate(int axisX) {
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            apodMaxDate = simpleDateFormat.format(c.getTime());
            int direction = axisX;
            if (apodDate == null) {
                c.add(Calendar.DATE, direction);
            } else {
                c.setTime(simpleDateFormat.parse(apodDate));
                c.add(Calendar.DATE, direction);
            }
            apodDate = simpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            SLogger.d(e);
        }
    }

    private void initRecyclerView() {
//        recyclerView = findViewById(R.id.act_apod_recyclerview_adapter);
//        layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new APODRecyclerViewAdapter(this, apodList);
//        recyclerView.setAdapter(mAdapter);
//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
//        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
//        recyclerView.setOnScrollListener(new EndlessRecyclerOnSwipeListener() {
//
//            @Override
//            public void onGetApod(int XAxis) {
//                updateDate(XAxis);
//                searchAPOD();
//            }
//        });
    }

    private boolean verifyValidRanges() {
        if (!apodDate.equalsIgnoreCase(apodMaxDate) && !Constants.APOD.FIRST_APOD.equalsIgnoreCase(apodDate)) {
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
        this.interactor = new APODInteractorImpl(new APODPresenterImpl(this), new APODRepositoryImpl());
        this.interactor.getAPOD(apodDate);

        APOD tomorrow = new APOD();
        tomorrow.setEmpty(true);

        this.apodList.add(tomorrow);

        viewPager = findViewById(R.id.pager);
        apodCarousel = new APODCarousel(context, apodList);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        viewPager.setAdapter(apodCarousel);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void loadAPOD(APOD apod) {
        apodList.add(apod);
        apodCarousel.notifyDataSetChanged();
    }
}
