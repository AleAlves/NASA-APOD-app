package br.com.aleson.nasa.apod.app.feature.home.presentation;

import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractor;
import br.com.aleson.nasa.apod.app.feature.home.interactor.APODInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.home.presenter.APODPresenterImpl;
import br.com.aleson.nasa.apod.app.feature.home.repository.APODRepositoryImpl;

import android.os.Bundle;

public class APODsActivity extends BaseActivity implements APODView {

    private APODInteractor interactor;

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

    }
}
