package com.aleson.example.nasaapodapp.apod.presentation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.aleson.example.nasaapodapp.R;

public class FullScreenActivity extends AppCompatActivity {

    private ImageView imageViewApodFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        imageViewApodFullScreen = (ImageView) findViewById(R.id.imageview_full_screen);
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("pic");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        imageViewApodFullScreen.setImageBitmap(bmp);
    }
}
