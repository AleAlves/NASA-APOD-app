package br.com.aleson.nasa.apod.app.feature.apod.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.aleson.nasa.apod.app.R;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class APODFullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apodfullscreen);

        Bundle data = getIntent().getExtras();
        String url = data.getString("url");

        ImageView fullScreenAPOD = findViewById(R.id.apod_fullscreen_imageview);

        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(fullScreenAPOD);
    }
}
