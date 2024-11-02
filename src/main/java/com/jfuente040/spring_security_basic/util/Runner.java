package com.jfuente040.spring_security_basic.util;


import com.jfuente040.spring_security_basic.model.Authority;
import com.jfuente040.spring_security_basic.model.User;
import com.jfuente040.spring_security_basic.repository.AuthorityRepository;
import com.jfuente040.spring_security_basic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoders;

    @Override
    public void run(String... args) throws Exception {

        if (this.authorityRepository.count() == 0) {
            this.authorityRepository.saveAll(List.of(
                    new Authority(AuthorityName.ADMIN),
                    new Authority(AuthorityName.READ),
                    new Authority(AuthorityName.WRITE)
            ));
        }

        if (this.userRepository.count() == 0) {
            this.userRepository.saveAll(List.of(
                            new User("user", encoders.encode("password"), List.of(this.authorityRepository.findByName(AuthorityName.ADMIN).get())),
                            new User("user01", encoders.encode("password01"), List.of(this.authorityRepository.findByName(AuthorityName.READ).get())),
                            new User("user02", encoders.encode("password02"), List.of(this.authorityRepository.findByName(AuthorityName.WRITE).get()))
                    )
            );
        }
    }
}
