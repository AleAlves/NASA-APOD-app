package br.com.aleson.nasa.apod.app.common.util;

import android.content.Intent;

import br.com.aleson.nasa.apod.app.feature.apod.presentation.APODsActivity;
import br.com.aleson.nasa.apod.app.feature.favorite.presentation.FavoriteActivity;
import br.com.aleson.nasa.apod.app.feature.profile.ProfileActivity;


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
}