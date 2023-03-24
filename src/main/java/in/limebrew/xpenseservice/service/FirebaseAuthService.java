package in.limebrew.xpenseservice.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public interface FirebaseAuthService {

    //? Verify JWT
    FirebaseToken verfifyToken(String accessToken) throws FirebaseAuthException;
}
