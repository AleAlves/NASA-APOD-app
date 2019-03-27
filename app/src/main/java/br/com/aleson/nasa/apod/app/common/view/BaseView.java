package br.com.aleson.nasa.apod.app.common.view;

import android.view.View;

import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;

public interface BaseView extends View.OnClickListener{

    void onShowLoading();

    void onHideLoading();

    void onShowDialog();

    void onShowDialog(DialogMessage dialogMessage, boolean cancelable);

    void onShowDialog(DialogMessage dialogMessage, boolean cancelable, DialogCallback callback);

}
