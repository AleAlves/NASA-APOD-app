package br.com.aleson.nasa.apod.app.common.session;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public interface Session {

    FirebaseAuth firebaseAuth();

    GoogleSignInClient googleSignInClient();

    FirebaseUser firebaseUser();

    void setFirebaseAuth(FirebaseAuth firebaseAuth);

    void setGoogleSignInClient(GoogleSignInClient googleSignInClient);

    void setFirebaseUser(FirebaseUser currentUser);
}
