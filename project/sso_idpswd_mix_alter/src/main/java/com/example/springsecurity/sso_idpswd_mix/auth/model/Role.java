package com.example.springsecurity.sso_idpswd_mix.auth.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;

@Embeddable
public class Role implements GrantedAuthority {

    private String authority;

    public Role(){}
    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
