package com.aleson.example.nasaapodapp.presentation;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.domain.ApodModel;
import com.aleson.example.nasaapodapp.domain.ConfigModel;
import com.aleson.example.nasaapodapp.presenter.ApodPresenter;
import com.aleson.example.nasaapodapp.presenter.ApodPresenterImpl;
import com.aleson.example.nasaapodapp.utils.RandomDate;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.aleson.example.nasaapodapp.R.drawable.placeholder_image;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private ImageView imageView;
    private ImageView imageViewWallpaperSet;
    private TextView title, copyright, date;
    private JustifiedTextView explanation;
    private LinearLayout linearLayoutLoading;
    private ScrollView scrollView;
    private Activity mActivity;
    private DatePickerDialog getDatePickerDialog;
    private ImageButton imageButtonCalendar;
    private ImageButton imageButtonRandom;
    private ImageButton imageButtonWallpaper;
    private Calendar calendarAgendada;
    private String dataSelecionada;
    private String dataSelecionadaTitulo;
    private String key;
    private static String url = "";
    private boolean lockWallpaper = false;
    private ApodPresenter apodPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        init();
        initListeners();
        config();
        apodPresenter = new ApodPresenterImpl(mActivity, dataSelecionada);
        apodPresenter.getTodayApod();
    }

    private void init() {

        imageView = (ImageView) findViewById(R.id.page);
        imageViewWallpaperSet = (ImageView) findViewById(R.id.wallpaper_set);
        title = (TextView) findViewById(R.id.title);
        explanation = (JustifiedTextView) findViewById(R.id.explanation);
        copyright = (TextView) findViewById(R.id.copyright);
        linearLayoutLoading = (LinearLayout) findViewById(R.id.loading);
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
                clear();
                onLoading();
                RandomDate randomDate = new RandomDate(new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime()));
                apodPresenter.getRandomApod(randomDate.getRandomDate());
            }
        });

        imageButtonWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lockWallpaper) {
                    Picasso.with(mActivity).load(url).into(imageViewWallpaperSet, new Callback() {
                        @Override
                        public void onSuccess() {

                            imageViewWallpaperSet.setScaleType(ImageView.ScaleType.CENTER);
                            BitmapDrawable drawable = (BitmapDrawable) imageViewWallpaperSet.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            permission();
                            saveSD(bitmap);
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(mActivity, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(mActivity, "Already set as Wallpaper", Toast.LENGTH_SHORT).show();
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

    private void config() {
        Gson gson = new Gson();
        ConfigModel config = gson.fromJson(loadJSONFromAsset("config"), ConfigModel.class);
        this.key = config.getKey();
    }

    public String loadJSONFromAsset(String file) {
        String json = null;
        try {
            InputStream is = mActivity.getAssets().open("jsons/" + file + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onError(String response) {
        lockWallpaper = false;
        scrollView.setVisibility(View.VISIBLE);
        linearLayoutLoading.setVisibility(View.GONE);
        clear();
        date.setText("Try again another day now");
    }

    @Override
    public void onServiceError() {
        clear();
    }

    @Override
    public void onLoading() {
        scrollView.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishLoad() {
        scrollView.setVisibility(View.VISIBLE);
        linearLayoutLoading.setVisibility(View.GONE);
        lockWallpaper = false;
    }

    @Override
    public void loadImage(ApodModel model) {
        scrollView.setVisibility(View.VISIBLE);
        clear();
        url = model.getUrl();
        switch (apodPresenter.getMediaType()) {
            case IMAGE:
            case GIF:
                Glide.with(mActivity).load(model.getHdurl()).into(imageView);
                break;
            case VIDEO:
                Glide.with(mActivity).load(R.drawable.videopholder).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (apodPresenter.getMediaType() == ApodPresenterImpl.MEDIA.VIDEO)
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
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
            android.icu.text.SimpleDateFormat inFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
            android.icu.text.SimpleDateFormat outFormat = new android.icu.text.SimpleDateFormat("EEEE , dd MMM yyyy");
            try {
                date.setText(outFormat.format(inFormat.parse(model.getDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void onInvalidDate() {
        clear();
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
                // Tratativa para nao executar duas vezes
                if (view.isShown()) {
                    calendarAgendada = Calendar.getInstance();
                    calendarAgendada.set(year, monthOfYear, dayOfMonth);
                    dataSelecionada = new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime());
                    date.setText(dataSelecionada);
                    clear();
                    scrollView.setVisibility(View.GONE);
                    apodPresenter.getChosenApod(dataSelecionada);
                }

            }

        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy").parse(sumDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateFormat != null)
            getDatePickerDialog.getDatePicker().setMaxDate(dateFormat.getTime());

        String string_date = "1995-06-16";
        long dateLong = 0;

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = f.parse(string_date);
            dateLong = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getDatePickerDialog.getDatePicker().setMinDate(dateLong);
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

    private void saveSD(Bitmap bitMapImg) {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_PICTURES+"/NASA APOD App/");
        mediaStorageDir.mkdirs();

        try {
//            MediaStore.Images.Media.insertImage(getContentResolver(), bitMapImg, dataSelecionada , "NASA APOD");
            String fname = dataSelecionadaTitulo + "." + url.substring(url.length() - 3, url.length());
            File file = new File(mediaStorageDir, fname);
            if (file.exists()) file.delete();
            file.createNewFile();
            Date currentTime = Calendar.getInstance().getTime();
            file.setLastModified(currentTime.getTime());
            FileOutputStream out = new FileOutputStream(file);
            bitMapImg.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), mediaStorageDir +"/"+ fname, dataSelecionada , "NASA APOD");
            Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
            startActivity(Intent.createChooser(intent, "Select Wallpaper"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void permission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
