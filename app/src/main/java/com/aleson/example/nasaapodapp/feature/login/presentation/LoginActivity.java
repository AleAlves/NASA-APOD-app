package com.aleson.example.nasaapodapp.feature.login.presentation;

import androidx.annotation.NonNull;

import com.aleson.example.nasaapodapp.feature.login.interactor.LoginInteractorImpl;
import com.aleson.example.nasaapodapp.common.constants.Constants;
import com.aleson.example.nasaapodapp.common.domain.DialogMessage;
import com.aleson.example.nasaapodapp.common.util.NavigationHelper;
import com.aleson.example.nasaapodapp.common.view.BaseActivity;
import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.common.session.Session;
import com.aleson.example.nasaapodapp.feature.login.interactor.LoginInteractor;
import com.aleson.example.nasaapodapp.feature.login.presenter.LoginPresenterImpl;
import com.aleson.example.nasaapodapp.feature.login.repository.LoginRepositoryImpl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends BaseActivity implements LoginView {

    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private LoginInteractor interactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.activity_login);

        findViewById(R.id.act_login_buttton_google_login).setOnClickListener(this);
        findViewById(R.id.act_login_button_skip).setOnClickListener(this);

        interactor = new LoginInteractorImpl(new LoginPresenterImpl(this), new LoginRepositoryImpl());

        firebaseAuth = FirebaseAuth.getInstance();

        startGoogleSignin();

        if (!Session.getInstance().isLogged()) {
            verifyCurrentUser();
        }
    }

    private void verifyCurrentUser() {

        if (firebaseAuth.getCurrentUser() != null) {

            showLoading();

            startLogin();
        }
    }

    private void startGoogleSignin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Constants.FIREBASE.RQUEST_ID_TOKEN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void startLogin() {

        this.initSession();

        this.interactor.login();
    }


    private void initSession() {

        Session.getInstance().setFirebaseAuth(firebaseAuth);
        Session.getInstance().setGoogleSignInClient(googleSignInClient);
        Session.getInstance().setFirebaseUser(firebaseAuth.getCurrentUser());
    }

    private void signIn() {

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                showToast(getString(R.string.toast_message_auth_failed));
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startLogin();
                        } else {

                            showToast(getString(R.string.toast_message_auth_failed));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.act_login_buttton_google_login:
                signIn();
                break;
            case R.id.act_login_button_skip:
                startHome();
                break;
        }
    }

    @Override
    public void startHome() {

        NavigationHelper.navigateAPOD();
    }

    @Override
    public void onError() {

        showDialog(false);
    }

    @Override
    public void onError(String message) {

        DialogMessage dialogMessage = new DialogMessage();
        showDialog(dialogMessage, false);
    }
}
