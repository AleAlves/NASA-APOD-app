package br.com.aleson.nasa.apod.app.feature.profile;

import androidx.annotation.Nullable;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.login.domain.User;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class ProfileActivity extends BaseActivity {

    private User user;

    private ImageView profilePic;
    private TextView profileName;
    private TextView profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = Session.getInstance().getUser();

        init();

        bindUserData();
    }

    private void bindUserData() {

        profileName.setText(user.getName());

        profileEmail.setText(user.getEmail());

        Glide.with(this)
                .load(user.getPic())
                .apply(RequestOptions.circleCropTransform())
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
                .into(profilePic);
    }

    private void init() {
        profilePic = findViewById(R.id.iamgeview_profile_pic);
        profileName = findViewById(R.id.textview_profile_name);
        profileEmail = findViewById(R.id.textview_profile_email);
    }
}
