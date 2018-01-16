// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.apod.domain;

import android.support.annotation.IntDef;

import static com.aleson.example.nasaapodapp.apod.domain.Media.*;

@IntDef({IMAGE, GIF, VIDEO})
public @interface Media {
    int IMAGE = 1;
    int GIF = 2;
    int VIDEO = 3;
}
