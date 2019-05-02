package br.com.aleson.nasa.apod.app.feature.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.common.firebase.FirebaseCloudMessaging;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.feature.login.domain.User;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.android.aleson.slogger.SLogger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileActivity extends BaseActivity {

    private User user;

    private ImageView profilePic;
    private TextView profileName;
    private TextView profileEmail;
    private TextView textViewAppVersion;
    private ImageButton imageButtonLogout;
    private Switch switchDailyNotifications;

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

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            textViewAppVersion.setText(info.versionName);
        } catch (Exception e) {
            SLogger.e(e);
        }

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
                }).into(profilePic);
    }

    private void init() {

        profilePic = findViewById(R.id.iamgeview_profile_pic);
        profileName = findViewById(R.id.textview_profile_name);
        profileEmail = findViewById(R.id.textview_profile_email);
        imageButtonLogout = findViewById(R.id.image_button_logout);
        textViewAppVersion = findViewById(R.id.textview_app_version);
        switchDailyNotifications = findViewById(R.id.switch_daily_notification);
        imageButtonLogout.setOnClickListener(this);
        switchDailyNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseCloudMessaging.subscribeDailyNotification();
                } else {
                    FirebaseCloudMessaging.unsubscribeDailyNotification();
                }
            }
        });
    }

    private void signOut() {

        Session.getInstance().firebaseAuth().signOut();

        Session.getInstance().googleSignInClient().signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });

        Session.getInstance().setLogged(false);
        Session.getInstance().setUser(null);
    }


    private void exitDialog() {

        DialogMessage message = new DialogMessage();
        message.setMessage(getString(R.string.dialog_message_logout_warn));
        message.setPositiveButton(getString(R.string.dialog_button_default_positive));
        message.setNegativeButton(getString(R.string.dialog_button_default_negative));
        showDialog(message, false, new DialogCallback.Buttons() {
            @Override
            public void onPositiveAction() {
                signOut();
            }

            @Override
            public void onNegativeAction() {
                //do nothing
            }

            @Override
            public void onDismiss() {
                //do nothing
            }
        });
    }

    @Override
    public void onClick(View v) {

        super.onClick(v);
        switch (v.getId()) {
            case R.id.image_button_logout:
                exitDialog();
                break;
            default:
                break;
        }
    }
}
