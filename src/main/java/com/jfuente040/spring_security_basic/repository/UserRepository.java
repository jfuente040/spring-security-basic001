package com.jfuente040.spring_security_basic.repository;

import com.jfuente040.spring_security_basic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
