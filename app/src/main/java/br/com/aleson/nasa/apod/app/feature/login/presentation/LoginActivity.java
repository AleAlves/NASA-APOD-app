package br.com.aleson.nasa.apod.app.feature.login.presentation;

import androidx.annotation.NonNull;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.home.APODsActivity;
import br.com.aleson.nasa.apod.app.feature.login.interactor.LoginInteractor;
import br.com.aleson.nasa.apod.app.feature.login.interactor.LoginInteractorImpl;
import br.com.aleson.nasa.apod.app.feature.login.presenter.LoginPresenterImpl;
import br.com.aleson.nasa.apod.app.feature.login.repository.LoginRepositoryImpl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.android.aleson.slogger.SLogger;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends BaseActivity implements LoginView {

    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    private LoginInteractor interactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.act_login_buttton_google_login).setOnClickListener(this);
        findViewById(R.id.act_login_button_skip).setOnClickListener(this);

        interactor = new LoginInteractorImpl(new LoginPresenterImpl(this), new LoginRepositoryImpl());
    }

    @Override
    public void onStart() {
        super.onStart();
        verifyCurrentUser();
    }

    private void verifyCurrentUser() {

        firebaseAuth = FirebaseAuth.getInstance();

        startGoogleSignin();

        if (firebaseAuth.getCurrentUser() != null) {
            startLogin();
        }
    }

    private void startGoogleSignin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("564935331593-788ive354t904oj80g8sqeum4a3krbcu.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void startLogin() {

        initSession();

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

    private void signOut() {

        // Firebase sign out
        firebaseAuth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getBaseContext(), "signOut sucessed.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                SLogger.w("Google sign in failed", e);
                // [START_EXCLUDE]
                Toast.makeText(getBaseContext(), "Authentication Failed.", Toast.LENGTH_LONG).show();
                // [END_EXCLUDE]
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        SLogger.d("firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            SLogger.d("signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            Toast.makeText(getBaseContext(), "Authentication sucessed." + user.getDisplayName(), Toast.LENGTH_LONG).show();

                            startLogin();
                        } else {
                            // If sign in fails, display a message to the user.
                            SLogger.w("signInWithCredential:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication Failed.", Toast.LENGTH_LONG).show();
                        }

                        // ...
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
        startActivity(new Intent(this, APODsActivity.class));
    }
}
