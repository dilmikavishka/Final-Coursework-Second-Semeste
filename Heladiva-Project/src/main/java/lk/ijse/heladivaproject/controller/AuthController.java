package lk.ijse.heladivaproject.controller;

import lk.ijse.heladivaproject.reqAndresp.response.JWTAuthResponse;
import lk.ijse.heladivaproject.reqAndresp.secure.SignIn;
import lk.ijse.heladivaproject.reqAndresp.secure.SignUp;
import lk.ijse.heladivaproject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:63342")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationService authenticationService;


    @PostMapping("/signUp")
    public ResponseEntity<JWTAuthResponse> register(@RequestBody SignUp signUp) {
        log.info("Received signup request for email: {}", signUp.getEmail());
        JWTAuthResponse response = authenticationService.signUp(signUp);
        log.info("User registered successfully: {}", signUp.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody SignIn signIn) {
        log.info("Received signIn request for email: {}", signIn.getEmail());
        JWTAuthResponse response = authenticationService.signIn(signIn);
        log.info("User signed in successfully: {}", signIn.getEmail());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/test")
    public ResponseEntity<String> securedEndpoint() {
        log.info("Accessing secured endpoint");
        return ResponseEntity.ok("You have access to this secured endpoint!");
    }
}
