package com.jfuente040.spring_security_basic.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserDto {

    private String username;
    private String password;
}
