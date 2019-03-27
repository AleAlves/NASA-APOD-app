package br.com.aleson.nasa.apod.app.feature.home.presentation;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractor;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.home.presentation.adapter.APODSwippeAdapter;
import br.com.aleson.nasa.apod.app.feature.home.presenter.APODPresenterImpl;
import br.com.aleson.nasa.apod.app.feature.home.repository.APODRepositoryImpl;

public class APODsActivity extends BaseActivity implements APODView, ViewPager.OnPageChangeListener {

    private static final int APOD_DAY_1_INDEX = 0;
    private static final int APOD_DAY_2_INDEX = 1;

    private APODInteractor interactor;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apods);

        bind();

        init();


    }

    private void bind() {
        this.viewPager = findViewById(R.id.viewpager);
        this.viewPager.setAdapter(new APODSwippeAdapter(getSupportFragmentManager()));
        this.viewPager.addOnPageChangeListener(this);
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

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.viewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
