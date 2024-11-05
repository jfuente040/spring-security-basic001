package com.jfuente040.spring_security_basic.controller;

import com.jfuente040.spring_security_basic.dto.LoginResponse;
import com.jfuente040.spring_security_basic.dto.LoginUserDto;
import com.jfuente040.spring_security_basic.dto.RegisterUserDto;
import com.jfuente040.spring_security_basic.model.User;
import com.jfuente040.spring_security_basic.security.UserSecurity;
import com.jfuente040.spring_security_basic.security.service.JwtService;
import com.jfuente040.spring_security_basic.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    // This method is used to authenticate a user and generate a JWT token
    // The token is then returned to the client
    // The client can then use this token to authenticate future requests
    // The token is valid for a certain amount of time
    // The client must request a new token after the token expires
    // The token is generated using the user's username and roles
    // The token is signed using a secret key
    // The token is sent to the client in the response body
    // The client must include the token in the Authorization header of future requests
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(new UserSecurity(authenticatedUser));
        // Generate dto loginResponse with the token and expiration time for the response
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        logger.info("Usuario autentificado: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok(loginResponse);
    }

    // This method is used to register a new user
    // The user's details are sent in the request body
    // The user's password is hashed before being stored in the database
    // The user is then returned to the client
    // The client can then use the user's details to authenticate
    // The user's password is not returned to the client
    // The user's password is not stored in the database
    // The user's password is hashed using a secure hashing algorithm
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(new UserSecurity(registeredUser));
        // Generate dto loginResponse with the token and expiration time for the response
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        logger.info("Usuario registrado: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok(loginResponse);
    }

}
