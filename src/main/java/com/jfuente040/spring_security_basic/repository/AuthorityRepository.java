package com.jfuente040.spring_security_basic.repository;

import com.jfuente040.spring_security_basic.model.Authority;
import com.jfuente040.spring_security_basic.util.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(AuthorityName name);
}

