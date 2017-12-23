package com.aleson.example.nasaapodapp.favorites.presentation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.aleson.example.nasaapodapp.favorites.presentation.adapter.RecyclerViewAdapter;
import com.aleson.example.nasaapodapp.favorites.presenter.FavoritesPresenter;
import com.aleson.example.nasaapodapp.favorites.presenter.FavoritesPresenterImpl;
import com.aleson.example.nasaapodapp.utils.HashUtils;
import com.aleson.example.nasaapodapp.utils.LocalDataBase;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements FavoritesView {

    private Activity mActivity;
    private Context context;
    private TextView textViewNofavorites;
    private ProgressBar progressBarLoading;
    private FavoritesPresenter favoritesPresenter;
    private LinearLayout relativeLayoutLoading;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        context = this;
        setContentView(R.layout.activity_favorites);
        textViewNofavorites = (TextView) findViewById(R.id.textview_no_favorites);
        progressBarLoading = (ProgressBar) findViewById(R.id.progressbar_loading_image);
        relativeLayoutLoading = (LinearLayout) findViewById(R.id.loading_image);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Favorites");
        setSupportActionBar(myToolbar);
        ArrayList<Apod> apodModelList;
        LocalDataBase apodBD = new LocalDataBase(this);
        if (!apodBD.hasDeviceInformation()) {
            String imei = null;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP && checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                imei = getDeviceImei();
            }
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Device deviceModel = new Device();
            try {
                deviceModel.setImei(HashUtils.makeSHA1Hash(imei));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            deviceModel.setManufactuer(android.os.Build.MANUFACTURER);
            deviceModel.setDeviceName(android.os.Build.MODEL);
            deviceModel.setRateValue(0);
            deviceModel.setScreenSize(size.x + "x" + size.y);
            apodBD.saveDeviceInfo(deviceModel);
        }
        apodModelList = apodBD.finAll();
        favoritesPresenter = new FavoritesPresenterImpl(mActivity);
        adapter(apodModelList);
    }

    private String getDeviceImei() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            String deviceid = mTelephonyManager.getDeviceId();
            return deviceid;
        }
        else{
            String deviceid = mTelephonyManager.getDeviceId();
            return deviceid;
        }
    }

    private void adapter(ArrayList<Apod> model) {
        recyclerView = (RecyclerView) findViewById(R.id.favorites_list);
        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(context, model, this, favoritesPresenter);
        recyclerView.setAdapter(recyclerViewAdapter);
        if (model.size() == 0) {
            textViewNofavorites.setVisibility(View.VISIBLE);
            textViewNofavorites.setText("Nothing here yet");
            progressBarLoading.setVisibility(View.GONE);
            relativeLayoutLoading.setVisibility(View.VISIBLE);
        } else {
            relativeLayoutLoading.setVisibility(View.GONE);
            textViewNofavorites.setVisibility(View.GONE);
            progressBarLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void reloadFavoritesList() {
        ArrayList<Apod> apodModelList;
        LocalDataBase apodBD = new LocalDataBase(this);
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
