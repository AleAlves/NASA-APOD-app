package com.aleson.example.nasaapodapp.presentation;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.domain.ApodModel;
import com.aleson.example.nasaapodapp.domain.ConfigModel;
import com.aleson.example.nasaapodapp.domain.Media;
import com.aleson.example.nasaapodapp.presenter.ApodPresenter;
import com.aleson.example.nasaapodapp.presenter.ApodPresenterImpl;
import com.aleson.example.nasaapodapp.utils.RandomDate;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private String key;
    private static String url = "";
    private ConfigModel config;
    private ApodPresenter apodPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        mActivity = this;
        init();
        initListeners();
        config();
        apodPresenter = new ApodPresenterImpl(mActivity, dataSelecionada);
        apodPresenter.getTodayApod();
        try {
            permission();
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "LOG");
            if (!mediaStorageDir.exists())
                mediaStorageDir.mkdirs();
            File file = new File(mediaStorageDir, "log.txt");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Date currentTime = Calendar.getInstance().getTime();
            file.setLastModified(currentTime.getTime());
            FileOutputStream out = new FileOutputStream(file);
            out.flush();
            out.close();
            Runtime.getRuntime().exec("logcat -c");
            Runtime.getRuntime().exec(new String[]{"logcat","-f",""+file,"*:W","CHOOSEN_DATE:D *:S" });

        } catch (Exception e) {
            Log.e("CUSTOMERROR", e.getMessage());
        }
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
                onLoading(true);
                RandomDate randomDate = new RandomDate(new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime()));
                apodPresenter.getRandomApod(randomDate.getRandomDate());
            }
        });

        imageButtonWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url != null && !"".equals(url)) {
                    permission();
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

    private void config() {
        Gson gson = new Gson();
        config = gson.fromJson(loadJSONFromAsset("config"), ConfigModel.class);
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
        scrollView.setVisibility(View.VISIBLE);
        linearLayoutLoading.setVisibility(View.GONE);
        imageButtonWallpaper.setEnabled(false);
        clear();
        date.setText("Try again another day now");
    }

    @Override
    public void onServiceError() {
        imageButtonWallpaper.setEnabled(false);
        clear();
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
    public void loadImage(ApodModel model) {
        scrollView.setVisibility(View.VISIBLE);
        url = model.getUrl();
        if (url != null) {
            clear();
            imageButtonWallpaper.setEnabled(true);
            switch (apodPresenter.getMediaType()) {
                case Media.IMAGE:
                case Media.GIF:
                    linearLayoutImageLoading.setVisibility(View.VISIBLE);
                    Glide.with(this)
                            .load(model.getHdurl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    linearLayoutImageLoading.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    linearLayoutImageLoading.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(imageView);
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
                    android.icu.text.SimpleDateFormat inFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
                    android.icu.text.SimpleDateFormat outFormat = new android.icu.text.SimpleDateFormat("EEEE , dd MMM yyyy");
                    date.setText(outFormat.format(inFormat.parse(model.getDate())));
                }catch (Exception e){
                    SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat outFormat = new SimpleDateFormat("EEEE , dd MMM yyyy");
                    try {
                        date.setText(outFormat.format(inFormat.parse(model.getDate())));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } else {
            Log.e("Data: ", dataSelecionada);
        }
    }

    @Override
    public void setWallpaper(Bitmap bitMapImg) {
        scrollView.setVisibility(View.VISIBLE);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, bitMapImg.getWidth(), bitMapImg.getHeight()), new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
        bitMapImg = Bitmap.createBitmap(bitMapImg, 0, 0, bitMapImg.getWidth(), bitMapImg.getHeight(), m, true);
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "APOD");
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            String format = url.substring(url.length() - 3, url.length());
            Bitmap.CompressFormat bcf = null;
            switch (format) {
                case "jpg":
                case "jpeg":
                    bcf = Bitmap.CompressFormat.JPEG;
                    break;
                case "png":
                    bcf = Bitmap.CompressFormat.PNG;
                    break;
            }
            String fname = dataSelecionadaTitulo + "." + format;
            File file = new File(mediaStorageDir, fname);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Date currentTime = Calendar.getInstance().getTime();
            file.setLastModified(currentTime.getTime());
            FileOutputStream out = new FileOutputStream(file);
            bitMapImg.compress(bcf, 100, out);
            out.flush();
            out.close();
            FileObserver observer = new FileObserver(file.getPath()) { // set up a file observer to watch this directory on sd card
                @Override
                public void onEvent(int event, String file) {
                    Toast.makeText(getBaseContext(), file + " was saved!", Toast.LENGTH_LONG);
                }
            };
            addImageToGallery(file.toString(), mActivity);
            Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
            startActivity(Intent.createChooser(intent, "Select Wallpaper"));
        } catch (Exception e) {
            e.printStackTrace();
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
                // Tratativa para nao executar duas vezes
                if (view.isShown()) {
                    calendarAgendada = Calendar.getInstance();
                    calendarAgendada.set(year, monthOfYear, dayOfMonth);
                    dataSelecionada = new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime());
                    date.setText(dataSelecionada);
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

    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void permission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

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
            }
        }
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.INTERNET}, 1);
            }
        }
    }

}
