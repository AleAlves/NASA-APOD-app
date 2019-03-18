package br.com.aleson.nasa.apod.app.common.view;

import android.view.View;

public interface BaseView extends View.OnClickListener{

    void onShowLoading();

    void onHideLoading();

    void onShowDialog();

}
