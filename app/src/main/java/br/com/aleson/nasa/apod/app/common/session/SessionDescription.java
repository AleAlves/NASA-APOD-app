package br.com.aleson.nasa.apod.app.common.session;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.aleson.nasa.apod.app.feature.login.domain.User;

public interface SessionDescription {

    FirebaseAuth firebaseAuth();

    GoogleSignInClient googleSignInClient();

    FirebaseUser firebaseUser();

    String getPublickKey();

    String getToken();

    User getUser();

    boolean isLogged();

    void setFirebaseAuth(FirebaseAuth firebaseAuth);

    void setGoogleSignInClient(GoogleSignInClient googleSignInClient);

    void setFirebaseUser(FirebaseUser currentUser);

    void setPublicKey(String publicKey);

    void setToken(String  token);

    void setUser(User user);

    void setLogged(boolean logged);

}
