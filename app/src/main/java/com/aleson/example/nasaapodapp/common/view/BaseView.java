package com.aleson.example.nasaapodapp.common.view;

import android.view.View;

import com.aleson.example.nasaapodapp.common.callback.DialogCallback;
import com.aleson.example.nasaapodapp.common.domain.DialogMessage;

public interface BaseView extends View.OnClickListener {

    void showLoading();

    void hideLoading();

    void showDialog(boolean finish);

    void showDialog(DialogMessage dialogMessage, boolean cancelable);

    void showDialog(DialogMessage dialogMessage, boolean cancelable, DialogCallback callback);

    void showToast(String press_again_to_leave);
}
