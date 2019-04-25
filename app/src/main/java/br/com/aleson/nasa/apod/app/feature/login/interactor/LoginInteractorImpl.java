package br.com.aleson.nasa.apod.app.feature.login.interactor;

import br.com.aleson.nasa.apod.app.common.callback.ResponseCallback;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.login.domain.AESData;
import br.com.aleson.nasa.apod.app.feature.login.domain.User;
import br.com.aleson.nasa.apod.app.feature.login.presenter.LoginPresenter;
import br.com.aleson.nasa.apod.app.feature.login.repository.LoginRepository;
import br.com.aleson.nasa.apod.app.feature.login.repository.response.TicketResponse;
import br.com.aleson.nasa.apod.app.feature.login.repository.response.TokenResponse;

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter presenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter, LoginRepository repository) {

        this.presenter = presenter;
        this.repository = repository;
    }

    @Override
    public void login() {

        presenter.showLoading();

        repository.getPublicKey(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                ticket((AESData) response);
            }

            @Override
            public void onFailure(Object response) {

                presenter.hideLoading();
                presenter.showDialog(null);
            }

        });
    }

    @Override
    public void ticket(AESData aesData) {

        repository.getTicket(aesData, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {
                token((TicketResponse) response);
            }

            @Override
            public void onFailure(Object response) {
                presenter.hideLoading();
                presenter.showDialog(null);
            }
        });
    }

    @Override
    public void token(TicketResponse ticketResponse) {

        final User user = new User();

        user.setUid(Session.getInstance().firebaseAuth().getCurrentUser().getUid());
        user.setEmail(Session.getInstance().firebaseAuth().getCurrentUser().getEmail());
        user.setName(Session.getInstance().firebaseAuth().getCurrentUser().getDisplayName());
        user.setPic(Session.getInstance().firebaseAuth().getCurrentUser().getPhotoUrl().toString());


        repository.registerLogin(user, ticketResponse, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                registerValidToken((TokenResponse) response);
                registerUser(user);
                presenter.startHome();
                presenter.hideLoading();

            }

            @Override
            public void onFailure(Object response) {

                presenter.showDialog(null);
                presenter.hideLoading();
            }

        });
    }


    private void registerValidToken(TokenResponse response) {

        Session.getInstance().setToken(response.getToken());
    }

    private void registerUser(User user) {

        Session.getInstance().setUser(user);
        Session.getInstance().setLogged(true);
    }
}
