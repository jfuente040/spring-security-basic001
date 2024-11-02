package com.jfuente040.spring_security_basic.security;

import com.jfuente040.spring_security_basic.model.Authority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class UserAuthority implements GrantedAuthority {

    private final Authority authority;


    @Override
    public String getAuthority() {
        return authority.getName().toString();
    }
}
