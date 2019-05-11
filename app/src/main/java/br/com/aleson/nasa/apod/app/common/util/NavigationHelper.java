package br.com.aleson.nasa.apod.app.common.util;

import android.content.Intent;

import br.com.aleson.nasa.apod.app.feature.apod.presentation.APODsActivity;
import br.com.aleson.nasa.apod.app.feature.favorite.presentation.FavoriteActivity;
import br.com.aleson.nasa.apod.app.feature.login.presentation.LoginActivity;
import br.com.aleson.nasa.apod.app.feature.about.AboutActivity;


public class NavigationHelper extends AndroidHelper {

    public static void navigateAPOD() {
        Intent i = new Intent(currentContext, APODsActivity.class);
        currentContext.startActivity(i);
    }

    public static void navigateProfile() {
        Intent i = new Intent(currentContext, AboutActivity.class);
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
