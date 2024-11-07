package com.jfuente040.spring_security_basic.service;

import com.jfuente040.spring_security_basic.dto.AuthResponseDto;
import com.jfuente040.spring_security_basic.dto.LoginUserDto;
import com.jfuente040.spring_security_basic.dto.RegisterUserDto;
import com.jfuente040.spring_security_basic.model.User;
import com.jfuente040.spring_security_basic.repository.AuthorityRepository;
import com.jfuente040.spring_security_basic.repository.UserRepository;
import com.jfuente040.spring_security_basic.security.UserSecurity;
import com.jfuente040.spring_security_basic.security.service.JwtService;
import com.jfuente040.spring_security_basic.util.AuthorityName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// what is the purpose of this class?
// 1-   This class is used to authenticate users and sign them up.
//
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthorityRepository authorityRepository;
    private final JwtService jwtService;

    public AuthResponseDto signup(RegisterUserDto input) {
        var user = User.builder()
                .username(input.getUsername())
                .password(passwordEncoder.encode(input.getPassword()))
                .authorities(List.of(authorityRepository.findByName(AuthorityName.READ).get()))
                .build();
        userRepository.save(user);
        return null;
    }

    public AuthResponseDto authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        var user = userRepository.findByUsername(input.getUsername()).orElseThrow();
        // Generate a JWT token for the authenticated user
        String jwtToken = jwtService.generateToken(new UserSecurity(user));
        // Generate dto AuthResponse with the token and expiration time for the response

        return AuthResponseDto.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
