package com.aleson.example.nasaapodapp.apod.domain;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Aleson on 2/4/2018.
 */

public class ApodResource  implements Serializable {

    private Bitmap resourceApod;

    public Bitmap getResourceApod() {
        return resourceApod;
    }

    public void setResourceApod(Bitmap resourceApod) {
        this.resourceApod = resourceApod;
    }
}
