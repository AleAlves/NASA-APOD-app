package com.aleson.example.nasaapodapp.common.callback;

public interface DialogCallback {

    void onDismiss();

    interface Buttons extends DialogCallback {

        void onPositiveAction();

        void onNegativeAction();
    }
}
