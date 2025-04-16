package lk.ijse.heladivaproject.service;


import lk.ijse.heladivaproject.reqAndresp.response.JWTAuthResponse;
import lk.ijse.heladivaproject.reqAndresp.secure.SignIn;
import lk.ijse.heladivaproject.reqAndresp.secure.SignUp;

public interface AuthenticationService {
    JWTAuthResponse signIn(SignIn signInReq);
    JWTAuthResponse signUp(SignUp signUp);
    JWTAuthResponse refreshToken(String tokenAccess);
}
