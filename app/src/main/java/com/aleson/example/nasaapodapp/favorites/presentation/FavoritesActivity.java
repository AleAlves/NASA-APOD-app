package com.aleson.example.nasaapodapp.favorites.presentation;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.presentation.MainActivity;
import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.aleson.example.nasaapodapp.favorites.presentation.adapter.RecyclerViewAdapter;
import com.aleson.example.nasaapodapp.favorites.presenter.FavoritesPresenter;
import com.aleson.example.nasaapodapp.favorites.presenter.FavoritesPresenterImpl;
import com.aleson.example.nasaapodapp.utils.ApodBD;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements FavoritesView {

    private Activity mActivity;
    private Context context;
    private FavoritesPresenter favoritesPresenter;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        context = this;
        setContentView(R.layout.activity_favorites);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ArrayList<Apod> apodModelList;
        ApodBD apodBD = new ApodBD(this);
        apodModelList = apodBD.finAll();
        if (!apodBD.hasDeviceInformation()) {
            Display display = getWindowManager().getDefaultDisplay();
            Device deviceModel = new Device();
            deviceModel.setImei("321421e23d23d223e");
            deviceModel.setManufactuer(android.os.Build.MANUFACTURER);
            deviceModel.setModelName(android.os.Build.MODEL);
            deviceModel.setRate_value(0);
            deviceModel.setScreenSize("100x100");
            apodBD.saveDeviceInfo(deviceModel);
        }
        favoritesPresenter = new FavoritesPresenterImpl(mActivity);
        adapter(apodModelList);
    }

    public String getIMEI(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                break;
            case R.id.action_exit:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                break;
            case R.id.action_rate:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent openPlayStore = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(openPlayStore);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(mActivity, " unable to find market app", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_lenguage:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void adapter(ArrayList<Apod> model) {
        recyclerView = (RecyclerView) findViewById(R.id.favorites_list);
        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(context, model, this, favoritesPresenter);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void reloadFavoritesList() {
        ArrayList<Apod> apodModelList;
        ApodBD apodBD = new ApodBD(this);
        apodModelList = apodBD.finAll();
        adapter(apodModelList);
        recyclerViewAdapter = new RecyclerViewAdapter(context, apodModelList, this, favoritesPresenter);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void openWallpaperManager() {
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        startActivity(Intent.createChooser(intent, "Select Wallpaper"));
    }
}
