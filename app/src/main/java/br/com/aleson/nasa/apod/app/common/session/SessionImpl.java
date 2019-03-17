package br.com.aleson.nasa.apod.app.common.session;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SessionImpl implements Session {

    private static Session session;

    FirebaseAuth firebaseAuth;

    GoogleSignInClient googleSignInClient;

    FirebaseUser firebaseUser;


    public static Session getInstance() {
        if (session == null) {
            session = new SessionImpl();
        }
        return session;
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
}
