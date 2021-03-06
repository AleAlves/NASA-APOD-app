package com.aleson.example.nasaapodapp.feature.login.interactor;

import com.aleson.example.nasaapodapp.common.callback.ResponseCallback;
import com.aleson.example.nasaapodapp.common.constants.Constants;
import com.aleson.example.nasaapodapp.common.response.BaseResponse;
import com.aleson.example.nasaapodapp.common.session.Session;
import com.aleson.example.nasaapodapp.feature.login.domain.AESData;
import com.aleson.example.nasaapodapp.feature.login.domain.User;
import com.aleson.example.nasaapodapp.feature.login.presenter.LoginPresenter;
import com.aleson.example.nasaapodapp.feature.login.repository.LoginRepository;
import com.aleson.example.nasaapodapp.feature.login.repository.response.TicketResponse;
import com.aleson.example.nasaapodapp.feature.login.repository.response.TokenResponse;

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

        repository.verifyToken(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                String token = (String) response;

                if (token == null || token.isEmpty()) {
                    
                    getPublicKey();
                } else {

                    registerUser(getUser());
                    registerValidToken(token);
                    presenter.startHome();
                }
            }

            @Override
            public void onFailure(Object response) {
                getPublicKey();
            }
        });
    }

    @Override
    public void getPublicKey() {
        repository.getPublicKey(new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                ticket((AESData) response);
            }

            @Override
            public void onFailure(Object response) {

                presenter.onError();
                presenter.hideLoading();
            }

        });
    }

    @Override
    public void ticket(AESData aesData) {

        repository.getTicket(aesData, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                if (((BaseResponse) response).getHttpStatus().getCode() == Constants.HTTP_CODE.SUCCESS) {

                    token((TicketResponse) response);
                } else {

                    presenter.onError(((BaseResponse) response).getHttpStatus().getStatus());
                    presenter.hideLoading();
                }

            }

            @Override
            public void onFailure(Object response) {

                presenter.onError();
                presenter.hideLoading();
            }
        });
    }

    @Override
    public void token(TicketResponse ticketResponse) {

        final User user = getUser();

        repository.registerLogin(user, ticketResponse, new ResponseCallback() {
            @Override
            public void onResponse(Object response) {

                if (((BaseResponse) response).getHttpStatus().getCode() == Constants.HTTP_CODE.CREATED ||
                        ((BaseResponse) response).getHttpStatus().getCode() == Constants.HTTP_CODE.ACCEPTED) {

                    registerValidToken(((TokenResponse) response).getToken());
                    saveToken(((TokenResponse) response).getToken());
                    registerUser(user);
                    presenter.startHome();
                } else {

                    presenter.onError(((BaseResponse) response).getHttpStatus().getStatus());
                    presenter.hideLoading();
                }
            }

            @Override
            public void onFailure(Object response) {

                presenter.onError();
                presenter.hideLoading();
            }

        });
    }


    private void registerValidToken(String token) {

        Session.getInstance().setToken(token);
    }


    private void saveToken(String token) {
        repository.saveToken(token);
    }

    private User getUser() {

        User user = new User();
        user.setUid(Session.getInstance().firebaseAuth().getCurrentUser().getUid());
        user.setEmail(Session.getInstance().firebaseAuth().getCurrentUser().getEmail());
        user.setName(Session.getInstance().firebaseAuth().getCurrentUser().getDisplayName());
        user.setPic(Session.getInstance().firebaseAuth().getCurrentUser().getPhotoUrl().toString());
        return user;
    }

    private void registerUser(User user) {

        Session.getInstance().setUser(user);
        Session.getInstance().setLogged(true);
    }
}
