package br.com.aleson.nasa.apod.app.common.view;

import androidx.appcompat.app.AppCompatActivity;
import br.com.aleson.nasa.apod.app.R;

import android.os.Bundle;
import android.view.View;

import com.github.android.aleson.slogger.SLogger;

public class BaseActivity extends AppCompatActivity implements BaseView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        SLogger.d(v.getId());
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowDialog() {

    }
}
