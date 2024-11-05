package com.jfuente040.spring_security_basic.service;

import com.jfuente040.spring_security_basic.dto.LoginUserDto;
import com.jfuente040.spring_security_basic.dto.RegisterUserDto;
import com.jfuente040.spring_security_basic.model.Authority;
import com.jfuente040.spring_security_basic.model.User;
import com.jfuente040.spring_security_basic.repository.AuthorityRepository;
import com.jfuente040.spring_security_basic.repository.UserRepository;
import com.jfuente040.spring_security_basic.util.AuthorityName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public User signup(RegisterUserDto input) {

        var user = User.builder()
                .username(input.getUsername())
                .password(passwordEncoder.encode(input.getPassword()))
                .authorities(List.of(
                        authorityRepository.findByName(AuthorityName.ADMIN).orElseThrow(() ->
                                new RuntimeException("Error: Role is not found"))))
                .build();
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        Authentication auth =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        //Return the user but the authentication is not keep yet in the context of the application
        return userRepository.findByUsername(input.getUsername()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
