package br.com.aleson.nasa.apod.app.feature.login.presenter;

import com.github.android.aleson.slogger.SLogger;

import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.feature.login.presentation.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
    }

    @Override
    public void showLoading() {
        this.view.showDialog();
    }

    @Override
    public void hideLoading() {
        this.view.hideLoading();
    }

    @Override
    public void showDialog(DialogMessage dialogMessage) {
        if (dialogMessage == null) {
            this.view.showDialog();
        } else {
            this.view.showDialog(dialogMessage, false);
        }
    }

    @Override
    public void startHome() {
        this.view.startHome();
    }
}
