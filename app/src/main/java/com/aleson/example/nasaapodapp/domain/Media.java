package com.aleson.example.nasaapodapp.domain;

import android.support.annotation.IntDef;
import static com.aleson.example.nasaapodapp.domain.Media.*;

/**
 * Created by Santander on 29/09/17.
 */

@IntDef({IMAGE, GIF, VIDEO})
public @interface Media {
    int IMAGE = 1;
    int GIF = 2;
    int VIDEO = 3;
}
