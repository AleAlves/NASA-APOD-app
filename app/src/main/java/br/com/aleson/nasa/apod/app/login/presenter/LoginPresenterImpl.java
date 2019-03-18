package br.com.aleson.nasa.apod.app.login.presenter;

import br.com.aleson.nasa.apod.app.login.presentation.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;

    public LoginPresenterImpl(LoginView view){
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
    public void showDialog() {

    }

    @Override
    public void startHome() {
//        this.view.onShowLoading();
        this.view.startHome();
    }
}
