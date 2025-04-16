package lk.ijse.heladivaproject.service.impl;


import lk.ijse.heladivaproject.dto.UserDTO;
import lk.ijse.heladivaproject.entity.Article;
import lk.ijse.heladivaproject.entity.User;
import lk.ijse.heladivaproject.repo.UserDao;
import lk.ijse.heladivaproject.reqAndresp.response.JWTAuthResponse;
import lk.ijse.heladivaproject.reqAndresp.secure.SignIn;
import lk.ijse.heladivaproject.reqAndresp.secure.SignUp;
import lk.ijse.heladivaproject.service.AuthenticationService;
import lk.ijse.heladivaproject.service.JWTService;
import lk.ijse.heladivaproject.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Auth_Service implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private  final UserDao userDAO;
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;




    @Override
    public JWTAuthResponse signIn(SignIn signInReq) {
        System.out.println(signInReq.getEmail());
        // TODO: 2021-05-12
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInReq.getEmail(), signInReq.getPassword()));
        var userByEmail = userDAO.findByEmail(signInReq.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        System.out.println("User found: " + userByEmail.getName() + userByEmail.getUserId());
        String token = jwtService.generateToken(userByEmail);
        UserDTO userDTO = mapping.toUserDTO(userByEmail);
        return JWTAuthResponse.builder()
                .token(token)
                .user(userDTO)
                .build();
    }


    @Override
    public JWTAuthResponse signUp(SignUp signUp) {
        UserDTO build = UserDTO.builder()
                .userId(generateNextUserId())
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .role(signUp.getRole())
                .build();

        User user = userDAO.save(mapping.toUser(build));
        String generateToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generateToken).build();
    }


    @Override
    public JWTAuthResponse refreshToken(String tokenAccess) {
        var userEntity = userDAO
                .findByEmail(jwtService.extractUsername(tokenAccess))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return JWTAuthResponse.builder().
                token(jwtService.generateToken(userEntity)).build();
    }

    private String generateNextUserId() {
        User lastArticle = userDAO.findFirstByOrderByUserIdDesc();
        if (lastArticle == null) {
            return "USR-001";
        }
        String lastUserId = lastArticle.getUserId();
        int lastId = Integer.parseInt(lastUserId.split("-")[1]);
        int nextId = lastId + 1;
        return "USR-" + String.format("%03d", nextId);
    }
}

