package br.com.aleson.nasa.apod.app.common.callback;

public interface DialogCallback {

    void onDismiss();

    interface Checkbox extends DialogCallback {
        void onCheckedAction();
    }

    interface Buttons extends DialogCallback {

        void onPositiveAction();

        void onNegativeAction();
    }
}
