package com.aleson.example.nasaapodapp.common.util;

import android.content.Intent;

import com.aleson.example.nasaapodapp.feature.apod.presentation.APODsActivity;
import com.aleson.example.nasaapodapp.feature.favorite.presentation.FavoriteActivity;
import com.aleson.example.nasaapodapp.feature.login.presentation.LoginActivity;
import com.aleson.example.nasaapodapp.feature.profile.ProfileActivity;


public class NavigationHelper extends AndroidHelper {

    public static void navigateAPOD() {
        Intent i = new Intent(currentContext, APODsActivity.class);
        currentContext.startActivity(i);
    }

    public static void navigateProfile() {
        Intent i = new Intent(currentContext, ProfileActivity.class);
        currentContext.startActivity(i);
    }

    public static void navigateFavorites() {
        Intent i = new Intent(currentContext, FavoriteActivity.class);
        currentContext.startActivity(i);
    }

    public static void navigateLogin() {
        Intent i = new Intent(currentContext, LoginActivity.class);
        currentContext.startActivity(i);
    }
}
