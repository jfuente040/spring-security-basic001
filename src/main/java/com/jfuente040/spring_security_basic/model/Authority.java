package com.jfuente040.spring_security_basic.model;

import com.jfuente040.spring_security_basic.util.AuthorityName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Entity(name = "authorities")
public class Authority {

    public Authority(AuthorityName name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthorityName name;

}
