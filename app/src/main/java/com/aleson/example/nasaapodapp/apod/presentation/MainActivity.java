// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.apod.presentation;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.about.presentation.AboutActivity;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.domain.ApodWallpaper;
import com.aleson.example.nasaapodapp.apod.domain.Media;
import com.aleson.example.nasaapodapp.apod.presenter.ApodPresenter;
import com.aleson.example.nasaapodapp.apod.presenter.ApodPresenterImpl;
import com.aleson.example.nasaapodapp.favorites.presentation.FavoritesActivity;
import com.aleson.example.nasaapodapp.settings.SettingsActivity;
import com.aleson.example.nasaapodapp.top.presentation.TopRatedActivity;
import com.aleson.example.nasaapodapp.utils.Permissions;
import com.aleson.example.nasaapodapp.utils.RandomDate;
import com.aleson.example.nasaapodapp.utils.SettingsUtil;
import com.aleson.example.nasaapodapp.utils.Wallpaper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity implements MainActivityView, View.OnClickListener {

    private Activity mActivity;

    private TextView title;
    private TextView copyright;
    private TextView date;
    private TextView textViewErrorMessage;

    private Apod model;
    private String today;
    private static String url = "";
    private static long id;
    private static boolean options = true;
    private static String defaultTDateFormat = "yyyy-MM-dd";
    private static String defaultTDateFormatPresentation = "dd/MM/yyyy";
    private static String sharedPrefsShowOptions = "showOptions";
    private static String getSharedPrefsSaveImages = "saveImages";

    private ImageView imageView;
    private ScrollView scrollView;
    private Calendar calendarAgendada;
    private Wallpaper wallpaper;
    private ApodPresenter apodPresenter;
    private String dataSelecionada;
    private String dataSelecionadaTitulo;
    private ImageButton buttonOptions;
    private JustifiedTextView explanation;

    private ImageButton imageButtonCalendar;
    private ImageButton imageButtonRandom;
    private ImageButton imageButtonWallpaper;
    private ImageButton imageButtonPermission;
    private ImageButton imageButtonRandomAfterError;

    private LinearLayout linearLayoutLoading;
    private LinearLayout linearlayoutRandomAfterError;
    private LinearLayout linearLayoutPermission;
    private LinearLayout linearLayoutOptions;
    private LinearLayout linearLayoutOptionsContent;
    private LinearLayout linearLayoutOptionsHolder;
    private boolean permissionsAllowed = false;
    private DatePickerDialog getDatePickerDialog;
    private RelativeLayout linearLayoutImageLoading;
    private ProgressBar progressBarLoadingImage;
    private SettingsUtil settingsUtil;
    private Thread.UncaughtExceptionHandler onRuntimeErrorDefault;
    private ApodWallpaper apodWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        initDefaultUncaughtExceptionHandler();
        mActivity = this;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        settingsUtil = new SettingsUtil(this, "settings");
        settingsUtil.updateSettings();
        init();
        checkPermission();
    }

    private void start() {
        permissionsAllowed = true;
        linearLayoutPermission.setVisibility(View.GONE);
        apodPresenter = new ApodPresenterImpl(mActivity, dataSelecionada);
        onLoading(false);
        super.onResume();
        Apod apod = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            apod = (Apod) extras.getSerializable("Apod");
        }
        if (apod == null) {
            apodPresenter.getTodayApod();
        } else {
            apodPresenter.responseSucess(apod);
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Permissions permissions = new Permissions(this);
            permissions.permissions();
        } else {
            start();
        }
    }

    private void init() {

        date = (TextView) findViewById(R.id.date);
        title = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.page);
        scrollView = (ScrollView) findViewById(R.id.main);
        copyright = (TextView) findViewById(R.id.copyright);
        buttonOptions = (ImageButton) findViewById(R.id.button_options);
        explanation = (JustifiedTextView) findViewById(R.id.explanation);
        imageButtonRandom = (ImageButton) findViewById(R.id.image_button_random);
        linearLayoutLoading = (LinearLayout) findViewById(R.id.translucid_loading);
        textViewErrorMessage = (TextView) findViewById(R.id.textview_error_message);
        imageButtonCalendar = (ImageButton) findViewById(R.id.image_button_calendar);
        linearLayoutImageLoading = (RelativeLayout) findViewById(R.id.loading_image);
        imageButtonWallpaper = (ImageButton) findViewById(R.id.image_button_wallpaper);
        linearLayoutPermission = (LinearLayout) findViewById(R.id.linearlayout_permission);
        progressBarLoadingImage = (ProgressBar) findViewById(R.id.progressbar_loading_image);
        imageButtonPermission = (ImageButton) findViewById(R.id.image_button_permisison_after_error);
        imageButtonRandomAfterError = (ImageButton) findViewById(R.id.image_button_random_after_error);
        linearlayoutRandomAfterError = (LinearLayout) findViewById(R.id.linearlayout_random_after_error);
        linearLayoutOptions = (LinearLayout) findViewById(R.id.linear_layout_expand_collapse_options);
        linearLayoutOptionsHolder = (LinearLayout) findViewById(R.id.linearlayout_options_holder);
        calendarAgendada = Calendar.getInstance();
        dataSelecionada = new SimpleDateFormat(defaultTDateFormat).format(calendarAgendada.getTime());
        today = new SimpleDateFormat(defaultTDateFormat).format(calendarAgendada.getTime());
        date.setText(dataSelecionada);
        initListeners();
        buttonOptions.setOnClickListener(this);
        buttonOptions.setImageResource(R.drawable.ic_remove_24dp);
    }

    private void initListeners() {
        imageButtonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading(false);
                RandomDate randomDate = new RandomDate(new SimpleDateFormat(defaultTDateFormat).format(calendarAgendada.getTime()));
                apodPresenter.getRandomApod(randomDate.getRandomDate());
            }
        });

        imageButtonWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url != null && !"".equals(url)) {
                    if (apodPresenter.getMediaType() == Media.IMAGE) {
                        if (apodWallpaper != null && apodWallpaper.getId() == model.getId()) {
                            setWallpaper(apodWallpaper.getBitmap());
                        } else {
                            onLoading(true);
                            apodPresenter.chooseWallpaper(url);
                        }
                    } else {
                        Toast.makeText(mActivity, "Media type not allowed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getDatePickerDialog.show();
                newDatePicker();
            }
        });

        imageButtonRandomAfterError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading(false);
                RandomDate randomDate = new RandomDate(new SimpleDateFormat(defaultTDateFormat).format(calendarAgendada.getTime()));
                apodPresenter.getRandomApod(randomDate.getRandomDate());
            }
        });

        imageButtonPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permissions permissions = new Permissions(mActivity);
                permissions.permissions();
            }
        });

        linearLayoutOptions.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Permissions.COMMON:
                if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    start();
                } else {
                    permissionsAllowed = false;
                    progressBarLoadingImage.setVisibility(View.GONE);
                    textViewErrorMessage.setText("Permissions needed");
                    textViewErrorMessage.setVisibility(View.VISIBLE);
                    linearLayoutPermission.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (permissionsAllowed)
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
                    Intent intentAbout = new Intent(this, AboutActivity.class);
                    startActivity(intentAbout);
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
                case R.id.action_top_rated:
                    Intent intentTopRated = new Intent(this, TopRatedActivity.class);
                    startActivity(intentTopRated);
                    break;
                case R.id.action_config:
                    Intent intentSettings = new Intent(this, SettingsActivity.class);
                    startActivity(intentSettings);
                    break;
                default:
                    break;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onError(String code) {
        progressBarLoadingImage.setVisibility(View.GONE);
        textViewErrorMessage.setText("Houston we have a problem...\n\n code (" + code + ")");
        textViewErrorMessage.setVisibility(View.VISIBLE);
        linearlayoutRandomAfterError.setVisibility(View.VISIBLE);
    }

    @Override
    public void badRequest(String code) {
        progressBarLoadingImage.setVisibility(View.GONE);
        if (dataSelecionada.contains(today)) {
            textViewErrorMessage.setText("We dont have an APOD in this day");
            linearlayoutRandomAfterError.setVisibility(View.VISIBLE);
        } else {
            textViewErrorMessage.setText("Houston we have a problem....\n\n code (" + code + ")");
        }
        textViewErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void nasaApiUnavailable() {
        progressBarLoadingImage.setVisibility(View.GONE);
        textViewErrorMessage.setText("Looks like the APOD service is offline, try again later.");
        textViewErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionError() {
        progressBarLoadingImage.setVisibility(View.GONE);
        textViewErrorMessage.setText("Internet connection required");
        textViewErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoading(boolean content) {
        if (content) {
            scrollView.setVisibility(View.VISIBLE);
            linearLayoutLoading.setVisibility(View.VISIBLE);
        } else {
            scrollView.setVisibility(View.GONE);
            linearlayoutRandomAfterError.setVisibility(View.GONE);
            textViewErrorMessage.setVisibility(View.GONE);
            progressBarLoadingImage.setVisibility(View.VISIBLE);
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
        id = model.getId();
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
                    imageButtonWallpaper.setEnabled(false);
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
                default:
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
                    SimpleDateFormat inFormat = new SimpleDateFormat(defaultTDateFormat);
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
        onLoading(true);
        apodWallpaper = new ApodWallpaper();
        apodWallpaper.setId(model.getId());
        apodWallpaper.setBitmap(bitMapImg);
        wallpaper = new Wallpaper(mActivity);
        Display display = getWindowManager().getDefaultDisplay();
        if (wallpaper.setWallpaper(model, bitMapImg, url, dataSelecionadaTitulo, display)) {
            openSystemWallpaperManager();
        } else {
            Toast.makeText(mActivity, "Error", Toast.LENGTH_LONG);
        }
    }

    private void clear() {
        title.setText("");
        explanation.setText("");
        copyright.setText("");
        date.setText("");
    }

    private void newDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                mActivity,
                dateListener(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        Date dateFormatInitial = null;
        Date dateFormatFinal = null;
        try {
            dateFormatInitial = new SimpleDateFormat(defaultTDateFormatPresentation).parse(sumDate());
            dateFormatFinal = new SimpleDateFormat(defaultTDateFormatPresentation).parse("16/06/1995");
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
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarAgendada.set(i, i1, i2);
                dataSelecionada = new SimpleDateFormat(defaultTDateFormat).format(calendarAgendada.getTime());
                date.setText(dataSelecionada);
                scrollView.setVisibility(View.GONE);
                onLoading(false);
                apodPresenter.getChosenApod(dataSelecionada);
            }
        };
    }

    public static String sumDate() {
        Calendar calendarData = Calendar.getInstance();
        calendarData.setTime(new Date());
        calendarData.add(Calendar.DATE, 0);
        Date dataInicial = calendarData.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dataInicial);
    }

    public void openSystemWallpaperManager() {
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        startActivityForResult(Intent.createChooser(intent, "Select Wallpaper"), 0);
        onFinishLoad();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (!settingsUtil.getSharedPreferences().getBoolean(getSharedPrefsSaveImages, false)) {
                    if (wallpaper != null) {
                        if (wallpaper.deleteLastFile()) {
                            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(model.getFileLocation()))));
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_button_calendar:
            case R.id.linear_layout_expand_collapse_options:
                newDatePicker();
                break;
            case R.id.button_options:
                if (settingsUtil.getSharedPreferences().getBoolean(sharedPrefsShowOptions, true)) {
                    settingsUtil.getEditor().putBoolean(sharedPrefsShowOptions, false);
                } else {
                    settingsUtil.getEditor().putBoolean(sharedPrefsShowOptions, true);
                }
                settingsUtil.getEditor().commit();
                if (options) {
                    buttonOptions.setImageResource(R.drawable.ic_chevron_left_24dp);
                    buttonOptions.animate().alpha(0.4f).setDuration(500);
                    linearLayoutOptionsHolder.animate()
                            .alpha(0.0f)
                            .setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    linearLayoutOptionsHolder.setVisibility(View.GONE);
                                    buttonOptions.setAlpha(0.4f);
                                }
                            });
                    linearLayoutOptionsHolder.clearAnimation();
                    options = false;
                } else {
                    options = true;
                    buttonOptions.animate().alpha(0.4f).setDuration(100);
                    linearLayoutOptionsHolder.animate()
                            .alpha(1.0f)
                            .setDuration(100)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    linearLayoutOptionsHolder.setVisibility(View.VISIBLE);
                                    buttonOptions.setImageResource(R.drawable.ic_remove_24dp);
                                    buttonOptions.setAlpha(1.0f);
                                }
                            });
                    linearLayoutOptionsHolder.clearAnimation();
                }
                break;
            default:
                break;
        }
    }

    private void initDefaultUncaughtExceptionHandler() {
        onRuntimeErrorDefault = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(onRuntimeError);
    }

    private Thread.UncaughtExceptionHandler onRuntimeError = new Thread.UncaughtExceptionHandler() {
        @SuppressLint("LongLogTag")
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e("SessionControlledActivity", "UncaughtException", ex);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
