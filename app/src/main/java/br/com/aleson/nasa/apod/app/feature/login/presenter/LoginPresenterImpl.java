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
        this.view.onShowLoading();
    }

    @Override
    public void hideLoading() {
        this.view.onHideLoading();
    }

    @Override
    public void showDialog(DialogMessage dialogMessage) {
        if (dialogMessage == null) {
            this.view.onShowDialog();
        } else {
            this.view.onShowDialog(dialogMessage, false);
        }
    }

    @Override
    public void startHome() {
        this.view.startHome();
    }
}
