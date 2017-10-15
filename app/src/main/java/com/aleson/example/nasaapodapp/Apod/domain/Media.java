package com.aleson.example.nasaapodapp.Apod.domain;

import android.support.annotation.IntDef;

import static com.aleson.example.nasaapodapp.Apod.domain.Media.*;

@IntDef({IMAGE, GIF, VIDEO})
public @interface Media {
    int IMAGE = 1;
    int GIF = 2;
    int VIDEO = 3;
}
