package com.jfuente040.spring_security_basic.dto;

import com.jfuente040.spring_security_basic.util.AuthorityName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDto {

    private String username;
    private String password;
    private AuthorityName authority;


}
