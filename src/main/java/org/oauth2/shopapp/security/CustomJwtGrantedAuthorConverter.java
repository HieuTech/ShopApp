package org.oauth2.shopapp.security;

import org.oauth2.shopapp.entity.Roles;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomJwtGrantedAuthorConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object rolesList = jwt.getClaims().get("roleName");
        if(rolesList instanceof List<?>){
            return (((List<?>) rolesList).stream().filter(roles -> roles instanceof String)
                    .map(roles -> new SimpleGrantedAuthority((String) roles))
                    .collect(Collectors.toList()));
        }
        return Collections.emptyList();
    }
}
