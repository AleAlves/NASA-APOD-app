package br.com.aleson.nasa.apod.app.common.view;

import android.view.View;

import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;

public interface BaseView extends View.OnClickListener{

    void showLoading();

    void hideLoading();

    void showDialog();

    void showDialog(DialogMessage dialogMessage, boolean cancelable);

    void showDialog(DialogMessage dialogMessage, boolean cancelable, DialogCallback callback);

    void showToast(String press_again_to_leave);
}
