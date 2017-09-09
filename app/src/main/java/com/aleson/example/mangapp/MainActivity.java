package com.aleson.example.mangapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.aleson.example.mangapp.R.drawable.placeholder_image;
import static com.aleson.example.mangapp.R.id.page;

public class MainActivity extends AppCompatActivity implements MainActivityView{

    private ImageView imageView;
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
    private String key;
    private static String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        init();
        initListeners();
        Gson gson = new Gson();
        ConfigModel config = gson.fromJson(loadJSONFromAsset("config"), ConfigModel.class);
        this.key = config.getKey();
        Service service = new Service(mActivity, key, dataSelecionada);
        linearLayoutLoading.setVisibility(View.VISIBLE);
        service.execute();
    }

    private void init(){

        imageView = (ImageView) findViewById(page);
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

    private void initListeners(){
        imageButtonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageButtonWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation shake = AnimationUtils.loadAnimation(mActivity, R.anim.fab_in);
                findViewById(R.id.page).startAnimation(shake);

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        setBackground(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Toast.makeText(mActivity, "Failed",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };

                Picasso.with(mActivity).load(url).into(target);
            }
        });

        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePickerDialog.show();
            }
        });

    }

    private void setBackground(Bitmap bitmap){
        try {
            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            myWallpaperManager.setBitmap(bitmap);
            Toast.makeText(mActivity, "Set as background",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mActivity, "Failed",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(String file) {
        String json = null;
        try {
            InputStream is = mActivity.getAssets().open("jsons/"+file+".json");
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
    public void onSucess(String response) {
        scrollView.setVisibility(View.VISIBLE);
        linearLayoutLoading.setVisibility(View.GONE);
        Gson gson = new Gson();
        APODModel model = gson.fromJson(response, APODModel.class);
        if(model != null) {
            if(model.getCode() != null && model.getCode().contains("400")){
                onInvalidDate();
            }
            else{
                loadAPOD(model);
            }

        }
    }

    private void onInvalidDate(){
        clear();
    }

    private void clear(){
        Glide.with(mActivity).load(placeholder_image).into(imageView);
        title.setText("");
        explanation.setText("");
        copyright.setText("");
        date.setText("");
    }

    private void loadAPOD(APODModel model){
        url = model.getUrl();
        if(!model.getMedia_type().contains("video")){
            Glide.with(mActivity).load(model.getHdurl()).into(imageView);
        }
        if (model.getTitle() != null)
            title.setText(model.getTitle());
        if (model.getExplanation() != null)
            explanation.setText(model.getExplanation());
        if (model.getCopyright() != null)
            copyright.setText(model.getCopyright() + "©");
        if (model.getDate() != null) {
            android.icu.text.SimpleDateFormat inFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
            android.icu.text.SimpleDateFormat outFormat = new android.icu.text.SimpleDateFormat("EEEE , dd M yyyy");
            try {
                date.setText(outFormat.format(inFormat.parse(model.getDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void montarDatePickerDialog() {
        getDatePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Tratativa para nao executar duas vezes
                if (view.isShown()) {
                    calendarAgendada = Calendar.getInstance();
                    calendarAgendada.set(year, monthOfYear, dayOfMonth);
                    dataSelecionada = new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime());
                    date.setText(dataSelecionada);
                    clear();
                    Service service = new Service(mActivity, key, dataSelecionada);
                    linearLayoutLoading.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    service.execute();
                }

            }

        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy").parse(sumDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dateFormat != null)
        getDatePickerDialog.getDatePicker().setMaxDate(dateFormat.getTime());
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, 1);
        getDatePickerDialog.setTitle("data");
    }

    public static String sumDate(){
        Calendar calendarData = Calendar.getInstance();
        calendarData.setTime(new Date());
        calendarData.add(Calendar.DATE,0);
        Date dataInicial = calendarData.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dataInicial);
    }
}
