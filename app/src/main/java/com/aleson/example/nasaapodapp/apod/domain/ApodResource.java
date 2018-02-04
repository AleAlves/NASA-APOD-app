package com.aleson.example.nasaapodapp.apod.domain;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Aleson on 2/4/2018.
 */

public class ApodResource  implements Serializable {

    private Drawable resourceApod;

    public Drawable getResourceApod() {
        return resourceApod;
    }

    public void setResourceApod(Drawable resourceApod) {
        this.resourceApod = resourceApod;
    }
}
