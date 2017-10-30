package com.aleson.example.nasaapodapp.apod.presentation;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.domain.Media;
import com.aleson.example.nasaapodapp.apod.presenter.ApodPresenter;
import com.aleson.example.nasaapodapp.apod.presenter.ApodPresenterImpl;
import com.aleson.example.nasaapodapp.favorites.presentation.FavoritesActivity;
import com.aleson.example.nasaapodapp.topRated.presentation.TopRatedActivity;
import com.aleson.example.nasaapodapp.utils.RandomDate;
import com.aleson.example.nasaapodapp.utils.Wallpaper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.fabric.sdk.android.Fabric;

import static com.aleson.example.nasaapodapp.R.drawable.placeholder_image;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private ImageView imageView;
    private TextView title, copyright, date;
    private JustifiedTextView explanation;
    private RelativeLayout linearLayoutLoading;
    private RelativeLayout linearLayoutImageLoading;
    private ScrollView scrollView;
    private Activity mActivity;
    private DatePickerDialog getDatePickerDialog;
    private ImageButton imageButtonCalendar;
    private ImageButton imageButtonRandom;
    private ImageButton imageButtonWallpaper;
    private Calendar calendarAgendada;
    private String dataSelecionada;
    private String dataSelecionadaTitulo;
    private static String url = "";
    private ApodPresenter apodPresenter;
    private Apod model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        mActivity = this;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        init();
        initListeners();
        apodPresenter = new ApodPresenterImpl(mActivity, dataSelecionada);
        onLoading(false);
        super.onResume();
        apodPresenter.getTodayApod();
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
            case R.id.action_favorites:
                Intent intentFavorites = new Intent(this, FavoritesActivity.class);
                Bundle bundle = new Bundle();
                Apod apodModel = model;
                bundle.putSerializable("apod", apodModel);
                intentFavorites.putExtras(bundle);
                startActivity(intentFavorites);
                break;
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
            case R.id.action_top_rated:
                Intent intentTopRated = new Intent(this, TopRatedActivity.class);
                startActivity(intentTopRated);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void init() {

        imageView = (ImageView) findViewById(R.id.page);
        title = (TextView) findViewById(R.id.title);
        explanation = (JustifiedTextView) findViewById(R.id.explanation);
        copyright = (TextView) findViewById(R.id.copyright);
        linearLayoutLoading = (RelativeLayout) findViewById(R.id.translucid_loading);
        linearLayoutImageLoading = (RelativeLayout) findViewById(R.id.loading_image);
        scrollView = (ScrollView) findViewById(R.id.main);
        montarDatePickerDialog();
        date = (TextView) findViewById(R.id.date);
        imageButtonCalendar = (ImageButton) findViewById(R.id.image_button_calendar);
        imageButtonRandom = (ImageButton) findViewById(R.id.image_button_random);
        imageButtonWallpaper = (ImageButton) findViewById(R.id.image_button_wallpaper);
        calendarAgendada = Calendar.getInstance();
        dataSelecionada = new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime());
        date.setText(dataSelecionada);
    }

    private void initListeners() {
        imageButtonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading(false);
                RandomDate randomDate = new RandomDate(new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime()));
                apodPresenter.getRandomApod(randomDate.getRandomDate());
            }
        });

        imageButtonWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url != null && !"".equals(url)) {
                    permission();
                    onLoading(true);
                    apodPresenter.chooseWallpaper(url);
                }
            }
        });

        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePickerDialog.show();
            }
        });

    }

    @Override
    public void onError(String response) {
        scrollView.setVisibility(View.VISIBLE);
        linearLayoutLoading.setVisibility(View.GONE);
        imageButtonWallpaper.setEnabled(false);
        clear();
        date.setText("Try again another day now");
    }

    @Override
    public void onServiceError() {
        clear();
        Toast.makeText(mActivity, "Houston we have a problem...try again later.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionError() {
        clear();
        Toast.makeText(mActivity, "Internet connection required", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoading(boolean content) {
        if (content) {
            scrollView.setVisibility(View.VISIBLE);
            linearLayoutLoading.setVisibility(View.VISIBLE);
        } else {
            scrollView.setVisibility(View.GONE);
            linearLayoutLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFinishLoad() {
        scrollView.setVisibility(View.VISIBLE);
        linearLayoutLoading.setVisibility(View.GONE);
        linearLayoutImageLoading.setVisibility(View.GONE);
    }

    @Override
    public void setContent(Apod model) {
        scrollView.setVisibility(View.VISIBLE);
        url = model.getUrl();
        if (url != null) {
            clear();
            imageButtonWallpaper.setEnabled(true);
            switch (apodPresenter.getMediaType()) {
                case Media.IMAGE:
                case Media.GIF:
                    linearLayoutImageLoading.setVisibility(View.VISIBLE);
                    loadImage(model);
                    break;
                case Media.VIDEO:
                    Glide.with(mActivity).load(R.drawable.videoplaceholder).into(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (apodPresenter.getMediaType() == Media.VIDEO)
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                } catch (Exception e) {
                                    Log.e("Error", e.getMessage());
                                }
                        }
                    });
                    break;
            }
            if (model.getTitle() != null)
                title.setText(model.getTitle());
            if (model.getExplanation() != null)
                explanation.setText(model.getExplanation());
            if (model.getCopyright() != null)
                copyright.setText(model.getCopyright() + "©");
            if (model.getDate() != null) {
                dataSelecionadaTitulo = model.getDate();
                try {
                    SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat outFormat = new SimpleDateFormat("EEEE , dd MMM yyyy");
                    date.setText(outFormat.format(inFormat.parse(model.getDate())));
                } catch (Exception e) {
                    Log.e("", "");
                }
            }
        } else {
            Log.e("Data: ", dataSelecionada);
        }
    }

    private void loadImage(Apod model) {
        this.model = model;
        final Apod loadModel = model;
        Glide.with(this)
                .load(model.getHdurl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        loadImage(loadModel);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        linearLayoutImageLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void setWallpaper(Bitmap bitMapImg) {
        scrollView.setVisibility(View.VISIBLE);
        Wallpaper wallpaper = new Wallpaper(mActivity);
        Display display = getWindowManager().getDefaultDisplay();
        if (wallpaper.setWallpaper(model, bitMapImg, url, dataSelecionadaTitulo, display)) {
            openSystemWallpaperManager();
        } else {
            Toast.makeText(mActivity, "Error", Toast.LENGTH_LONG);
        }
    }

    private void clear() {
        Glide.with(mActivity).load(placeholder_image).into(imageView);
        title.setText("");
        explanation.setText("");
        copyright.setText("");
        date.setText("");
    }

    public void montarDatePickerDialog() {
        getDatePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (view.isShown()) {
                    calendarAgendada = Calendar.getInstance();
                    calendarAgendada.set(year, monthOfYear, dayOfMonth);
                    dataSelecionada = new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime());
                    date.setText(dataSelecionada);
                    scrollView.setVisibility(View.GONE);
                    onLoading(false);
                    apodPresenter.getChosenApod(dataSelecionada);
                }

            }

        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        Date dateFormatInitial = null;
        Date dateFormatFinal = null;
        try {
            dateFormatInitial = new SimpleDateFormat("dd/MM/yyyy").parse(sumDate());
            dateFormatFinal = new SimpleDateFormat("dd/MM/yyyy").parse("16-06-1995");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateFormatInitial != null)
            getDatePickerDialog.getDatePicker().setMaxDate(dateFormatInitial.getTime());

        if (dateFormatFinal != null)
            getDatePickerDialog.getDatePicker().setMinDate(dateFormatFinal.getTime());

        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, 1);
        getDatePickerDialog.setTitle("data");
    }

    public static String sumDate() {
        Calendar calendarData = Calendar.getInstance();
        calendarData.setTime(new Date());
        calendarData.add(Calendar.DATE, 0);
        Date dataInicial = calendarData.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dataInicial);
    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.INTERNET)) {

            } else {

                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.INTERNET}, 1);
            }
        }
        if(ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.READ_PHONE_STATE)) {

            } else {

                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }
    }

    public void openSystemWallpaperManager() {
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        startActivity(Intent.createChooser(intent, "Select Wallpaper"));
    }
}
