package br.com.aleson.nasa.apod.app.login.interactor;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.login.presenter.LoginPresenter;
import br.com.aleson.nasa.apod.app.login.repository.LoginRepository;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter presenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter, LoginRepository repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    @Override
    public void login() {
        presenter.showDialog();
        repository.getPublicKey(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {
                ticket();
            }

            @Override
            public void onFailure(Object response) {

            }

            @Override
            public void onFinish() {
                presenter.hideLoading();
            }
        });
    }

    @Override
    public void ticket() {
        repository.getTicket(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {
                token();
            }

            @Override
            public void onFailure(Object response) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void token() {
        repository.registerLogin(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

            }

            @Override
            public void onFailure(Object response) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
