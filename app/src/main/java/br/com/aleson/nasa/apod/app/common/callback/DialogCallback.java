package br.com.aleson.nasa.apod.app.common.callback;

public interface DialogCallback {

    void onDismiss();

    interface Buttons extends DialogCallback {

        void onPositiveAction();

        void onNegativeAction();
    }
}
