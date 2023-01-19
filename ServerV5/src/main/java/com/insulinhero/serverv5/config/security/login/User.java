package com.insulinhero.serverv5.config.security.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean activated;
    private Set<Authority> authorities = new HashSet<>();

    public User() { }

    public User(Long id, String username, String password, String authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        if(authorities != null) this.setAuthorities(authorities);
        this.activated = true;
    }

    public void setAuthorities(String authorities) {
        String[] roles = authorities.split(",");
        for(String role : roles) {
            String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
            this.authorities.add(new Authority(authority));
        }
    }
}
