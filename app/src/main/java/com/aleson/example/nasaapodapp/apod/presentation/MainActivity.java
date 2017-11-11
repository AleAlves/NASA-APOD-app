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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.domain.Media;
import com.aleson.example.nasaapodapp.apod.presenter.ApodPresenter;
import com.aleson.example.nasaapodapp.apod.presenter.ApodPresenterImpl;
import com.aleson.example.nasaapodapp.favorites.presentation.FavoritesActivity;
import com.aleson.example.nasaapodapp.topRated.presentation.TopRatedActivity;
import com.aleson.example.nasaapodapp.utils.Permissions;
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
    private TextView textViewErrorMessage;
    private ProgressBar progressBarLoadingImage;
    private TextView title, copyright, date;
    private JustifiedTextView explanation;
    private LinearLayout linearLayoutLoading;
    private RelativeLayout linearLayoutImageLoading;
    private ScrollView scrollView;
    private Activity mActivity;
    private DatePickerDialog getDatePickerDialog;
    private ImageButton imageButtonCalendar;
    private ImageButton imageButtonRandom;
    private ImageButton imageButtonWallpaper;
    private ImageButton imageButtonPermission;
    private LinearLayout linearlayoutRandomAfterError;
    private LinearLayout linearLayoutPermission;
    private ImageButton imageButtonRandomAfterError;
    private Calendar calendarAgendada;
    private String dataSelecionada;
    private String dataSelecionadaTitulo;
    private static String url = "";
    private ApodPresenter apodPresenter;
    private Apod model;
    private String today;
    private boolean permissionsAllowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        mActivity = this;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        init();
        checkPermission();
    }

    private void start(){
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

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Permissions permissions = new Permissions(this);
            permissions.permissions();
        }
        else{
            start();
        }
    }

    private void init() {

        imageView = (ImageView) findViewById(R.id.page);
        textViewErrorMessage = (TextView) findViewById(R.id.textview_error_message);
        progressBarLoadingImage = (ProgressBar) findViewById(R.id.progressbar_loading_image);
        title = (TextView) findViewById(R.id.title);
        explanation = (JustifiedTextView) findViewById(R.id.explanation);
        copyright = (TextView) findViewById(R.id.copyright);
        linearLayoutLoading = (LinearLayout) findViewById(R.id.translucid_loading);
        linearlayoutRandomAfterError = (LinearLayout) findViewById(R.id.linearlayout_random_after_error);
        linearLayoutPermission = (LinearLayout) findViewById(R.id.linearlayout_permission);
        linearLayoutImageLoading = (RelativeLayout) findViewById(R.id.loading_image);
        scrollView = (ScrollView) findViewById(R.id.main);
        montarDatePickerDialog();
        date = (TextView) findViewById(R.id.date);
        imageButtonCalendar = (ImageButton) findViewById(R.id.image_button_calendar);
        imageButtonRandom = (ImageButton) findViewById(R.id.image_button_random);
        imageButtonWallpaper = (ImageButton) findViewById(R.id.image_button_wallpaper);
        imageButtonPermission = (ImageButton) findViewById(R.id.image_button_permisison_after_error);
        imageButtonRandomAfterError = (ImageButton) findViewById(R.id.image_button_random_after_error);
        calendarAgendada = Calendar.getInstance();
        dataSelecionada = new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime());
        today = new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime());
        date.setText(dataSelecionada);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_translate);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages_array, R.layout.simple_spinner_apod);
        adapter.setDropDownViewResource(R.layout.simple_spinner_apod_item);
        spinner.setAdapter(adapter);

        initListeners();
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

        imageButtonRandomAfterError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading(false);
                RandomDate randomDate = new RandomDate(new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime()));
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
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
        if(permissionsAllowed)
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

    public void openSystemWallpaperManager() {
        linearLayoutLoading.setVisibility(View.GONE);
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        startActivity(Intent.createChooser(intent, "Select Wallpaper"));
    }
}
