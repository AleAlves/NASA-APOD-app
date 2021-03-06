package com.aleson.example.nasaapodapp.feature.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.aleson.example.nasaapodapp.R;

import com.aleson.example.nasaapodapp.common.callback.DialogCallback;
import com.aleson.example.nasaapodapp.common.constants.Constants;
import com.aleson.example.nasaapodapp.common.domain.DialogMessage;
import com.aleson.example.nasaapodapp.common.firebase.FirebaseCloudMessaging;
import com.aleson.example.nasaapodapp.common.session.Session;
import com.aleson.example.nasaapodapp.common.util.StorageHelper;
import com.aleson.example.nasaapodapp.common.view.BaseActivity;
import com.aleson.example.nasaapodapp.feature.profile.viewmodel.ProfileViewModel;
import com.aleson.example.nasaapodapp.feature.login.domain.User;
import com.aleson.example.nasaapodapp.feature.profile.model.ServiceVersionModel;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;

import com.aleson.example.nasaapodapp.databinding.ActivityProfileBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.android.aleson.slogger.SLogger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


//Small aproach to MVVM, just a POC.

public class ProfileActivity extends BaseActivity {

    private User user;
    private ImageView profilePic;
    private TextView profileName;
    private TextView profileEmail;
    private TextView textViewAppVersion;
    private Button buttonDeleteAccount;
    private ImageButton imageButtonLogout;
    private Switch switchDailyNotifications;
    private ActivityProfileBinding binding;
    private ProfileViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        binding.setViewModel(viewModel);

        viewModel.getVersion().observe(this, new Observer<ServiceVersionModel>() {
            @Override
            public void onChanged(ServiceVersionModel serviceVersionModel) {

                binding.setOnServiceVersionLoaded(true);
                binding.textviewApiVersion.setVisibility(View.VISIBLE);
                binding.textviewApiVersion.setText(serviceVersionModel.getVersion());
            }
        });

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
        buttonDeleteAccount = findViewById(R.id.button_delete_account);
        imageButtonLogout.setOnClickListener(this);
        buttonDeleteAccount.setOnClickListener(this);


        switchDailyNotifications = findViewById(R.id.switch_daily_notification);
        switchDailyNotifications.setChecked(StorageHelper.readData(Constants.NOTIFICATIONS.DAILY_NOTIFICATION, true));
        switchDailyNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    StorageHelper.saveData(Constants.NOTIFICATIONS.DAILY_NOTIFICATION, true);
                    FirebaseCloudMessaging.subscribeDailyNotification();
                } else {

                    StorageHelper.saveData(Constants.NOTIFICATIONS.DAILY_NOTIFICATION, false);
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
        Session.getInstance().getToken();
        StorageHelper.saveData(Constants.TOKEN_KEY, "");
    }


    private void deleteAccount() {

        viewModel.delete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    signOut();
                }
            }
        });
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

    private void deleteAccountDialog() {

        DialogMessage message = new DialogMessage();
        message.setMessage(getString(R.string.dialog_message_delete_account));
        message.setPositiveButton(getString(R.string.dialog_button_default_positive));
        message.setNegativeButton(getString(R.string.dialog_button_default_negative));
        showDialog(message, false, new DialogCallback.Buttons() {
            @Override
            public void onPositiveAction() {
                deleteAccount();
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
            case R.id.button_delete_account:

                deleteAccountDialog();
                break;
            default:
                break;
        }
    }
}
