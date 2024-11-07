package com.jfuente040.spring_security_basic.controller;

import com.jfuente040.spring_security_basic.dto.AuthResponseDto;
import com.jfuente040.spring_security_basic.dto.LoginUserDto;
import com.jfuente040.spring_security_basic.dto.RegisterUserDto;
import com.jfuente040.spring_security_basic.model.User;
import com.jfuente040.spring_security_basic.security.UserSecurity;
import com.jfuente040.spring_security_basic.security.service.JwtService;
import com.jfuente040.spring_security_basic.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationService authenticationService;

    // This method is used to authenticate (login) a user and return a JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
    }

    // This method is used to register a user and return a JWT token
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(authenticationService.signup(registerUserDto));
    }



}
