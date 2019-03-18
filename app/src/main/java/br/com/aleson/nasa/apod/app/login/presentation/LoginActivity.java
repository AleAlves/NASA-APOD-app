package br.com.aleson.nasa.apod.app.login.presentation;

import androidx.annotation.NonNull;
import br.com.aleson.nasa.apod.app.common.view.BaseActivity;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.home.APODsActivity;
import br.com.aleson.nasa.apod.app.login.interactor.LoginInteractor;
import br.com.aleson.nasa.apod.app.login.interactor.LoginInteractorImpl;
import br.com.aleson.nasa.apod.app.login.presenter.LoginPresenterImpl;
import br.com.aleson.nasa.apod.app.login.repository.LoginRepositoryImpl;
import br.com.aleson.nasa.apod.app.login.repository.api.PublicKeyMethod;
import br.com.aleson.nasa.apod.app.login.repository.api.TicketMethod;
import br.com.aleson.nasa.apod.app.login.repository.api.LoginMethod;
import br.com.aleson.nasa.apod.app.login.repository.response.PublicKeyResponse;
import br.com.aleson.nasa.apod.app.login.domain.AESData;
import br.com.aleson.nasa.apod.app.login.repository.response.TicketResponse;
import br.com.aleson.nasa.apod.app.login.repository.response.TokenResponse;
import br.com.aleson.nasa.apod.app.login.domain.User;
import br.com.connector.aleson.android.connector.Connector;
import br.com.connector.aleson.android.connector.cryptography.domain.Safe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
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

    private void getPublicKey() {
        Connector.request().create(PublicKeyMethod.class).getPublicKey().enqueue(new Callback<PublicKeyResponse>() {
            @Override
            public void onResponse(Call<PublicKeyResponse> call, Response<PublicKeyResponse> response) {
                SLogger.d(response);

                Session.getInstance().setPublicKey(response.body().getPublicKey());

                AESData AESData = new AESData();
                AESData.setIv(Base64.encodeToString(Connector.crypto().getAes().getIv(), 0).replace("\n", ""));
                AESData.setKey(Connector.crypto().getAes().getSecret());
                AESData.setSalt(Connector.crypto().getAes().getSalt());

                getTicket(AESData);
            }

            @Override
            public void onFailure(Call<PublicKeyResponse> call, Throwable t) {
                SLogger.d(t);
            }
        });
    }

    private void getTicket(AESData AESData) {

        Safe safe = new Safe();
        safe.setContent(AESData, Session.getInstance().getPublickKey());

        Connector.request().create(TicketMethod.class).token(safe).enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                SLogger.d(response);

                resgisterLogin(response.body());
            }

            @Override
            public void onFailure(Call<TicketResponse> call, Throwable t) {
                SLogger.d(t);

            }
        });
    }

    private void resgisterLogin(TicketResponse ticketResponse) {

        final User user = new User();
        user.setUid(firebaseAuth.getCurrentUser().getUid());
        user.setEmail(firebaseAuth.getCurrentUser().getEmail());
        user.setName(firebaseAuth.getCurrentUser().getDisplayName());
        user.setPic(firebaseAuth.getCurrentUser().getPhotoUrl().toString());
        Safe safe = new Safe();
        safe.setContent(user);

        Connector.request().create(LoginMethod.class).login(ticketResponse.getTicket(), safe).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                SLogger.d(response);

                registerValidToken(response.body(), user);
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                SLogger.d(t);

            }
        });
    }

    private void registerValidToken(TokenResponse response, User user) {
        Session.getInstance().setToken(response.getToken());
        registerUser(user);
    }

    private void registerUser(User user) {
        Session.getInstance().setUser(user);
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
