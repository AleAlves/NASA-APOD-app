package br.com.aleson.nasa.apod.app.home;

import br.com.aleson.nasa.apod.app.BaseActivity;
import br.com.aleson.nasa.apod.app.R;

import android.os.Bundle;

public class APODsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apods);
    }
}
