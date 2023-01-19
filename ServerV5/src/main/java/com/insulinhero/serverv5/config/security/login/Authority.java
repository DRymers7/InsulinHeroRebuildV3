package com.insulinhero.serverv5.config.security.login;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Authority {

    private String name;

    public Authority(String name) {
        this.name = name;
    }

}
