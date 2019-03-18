package br.com.aleson.nasa.apod.app.common.session;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.aleson.nasa.apod.app.login.domain.User;


public class Session implements SessionDescription {

    private static SessionDescription sessionDescription;

    FirebaseAuth firebaseAuth;

    GoogleSignInClient googleSignInClient;

    FirebaseUser firebaseUser;

    private User user;

    private String token;

    private String publicKey;


    public static SessionDescription getInstance() {
        if (sessionDescription == null) {
            sessionDescription = new Session();
        }
        return sessionDescription;
    }

    @Override
    public FirebaseAuth firebaseAuth() {
        return firebaseAuth;
    }

    @Override
    public GoogleSignInClient googleSignInClient() {
        return googleSignInClient;
    }

    @Override
    public FirebaseUser firebaseUser() {
        return firebaseUser;
    }

    @Override
    public String getPublickKey() {
        return publicKey;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public User getUser() {
        return user;
    }


    @Override
    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        this.googleSignInClient = googleSignInClient;
    }

    @Override
    public void setFirebaseUser(FirebaseUser currentUser) {
        this.firebaseUser = firebaseUser;
    }

    @Override
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

}
