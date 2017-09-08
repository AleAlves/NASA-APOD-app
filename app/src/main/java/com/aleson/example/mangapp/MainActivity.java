package com.aleson.example.mangapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MainActivityView{

    private ImageView imageView;
    private TextView title, copyright, date;
    private JustifiedTextView explanation;
    private LinearLayout linearLayoutLoading;
    private LinearLayout main;
    private ScrollView scrollView;
    private Activity mActivity;
    private DatePickerDialog getDatePickerDialog;
    private ImageButton imagebuttonCalendar;
    private Calendar calendarAgendada;
    private String dataSelecionada;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        init();
        Gson gson = new Gson();
        ConfigModel config = gson.fromJson(loadJSONFromAsset("config"), ConfigModel.class);
        this.key = config.getKey();
        Service service = new Service(mActivity, key, dataSelecionada);
        linearLayoutLoading.setVisibility(View.VISIBLE);
        service.execute();
    }

    private void init(){

        imageView = (ImageView) findViewById(R.id.page);
        title = (TextView) findViewById(R.id.title);
        explanation = (JustifiedTextView) findViewById(R.id.explanation);
        copyright = (TextView) findViewById(R.id.copyright);
        linearLayoutLoading = (LinearLayout) findViewById(R.id.loading);
        scrollView = (ScrollView) findViewById(R.id.main);
        montarDatePickerDialog();
        date = (TextView) findViewById(R.id.date);
        imagebuttonCalendar = (ImageButton) findViewById(R.id.imagebutton_calendar);
        calendarAgendada = Calendar.getInstance();
        dataSelecionada = new SimpleDateFormat("yyyy-MM-dd").format(calendarAgendada.getTime());
        date.setText(dataSelecionada);
        imagebuttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePickerDialog.show();
            }
        });
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
        Glide.with(mActivity).load(R.drawable.placeholder_image).into(imageView);
        title.setText("");
        explanation.setText("");
        copyright.setText("");
        date.setText("");
    }

    private void loadAPOD(APODModel model){
        Glide.with(mActivity).load(model.getHdurl()).into(imageView);
        if(model.getTitle() != null)
            title.setText(model.getTitle());
        if(model.getExplanation() != null)
            explanation.setText(model.getExplanation());
        if(model.getCopyright() != null)
            copyright.setText(model.getCopyright()+"©");
        if(model.getDate() != null) {
            android.icu.text.SimpleDateFormat inFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
            android.icu.text.SimpleDateFormat outFormat = new android.icu.text.SimpleDateFormat("EEEE , dd MMMM yyyy");
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
