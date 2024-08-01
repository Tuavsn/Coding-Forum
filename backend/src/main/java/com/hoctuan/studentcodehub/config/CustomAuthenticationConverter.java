package com.hoctuan.studentcodehub.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

public class CustomAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        String roles = "ROLE_" + jwt.getClaimAsString("roles");
        return List.of(new SimpleGrantedAuthority(roles));
    }
}
