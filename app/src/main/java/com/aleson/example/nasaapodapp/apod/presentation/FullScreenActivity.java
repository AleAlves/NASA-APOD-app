package com.aleson.example.nasaapodapp.apod.presentation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.aleson.example.nasaapodapp.R;

public class FullScreenActivity extends AppCompatActivity {

    private ImageView imageViewApodFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        imageViewApodFullScreen = (ImageView) findViewById(R.id.imageview_full_screen);
        Bundle extras = getIntent().getExtras();
        try {
            byte[] b = extras.getByteArray("pic");
            if (b != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                imageViewApodFullScreen.setImageBitmap(bmp);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Oops", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
