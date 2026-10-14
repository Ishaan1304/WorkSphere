package com.worksphere.WorkSphere.security;

import com.worksphere.WorkSphere.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {

        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {

        return Boolean.FALSE.equals(user.getAccountLocked());
    }

    @Override
    public boolean isEnabled() {

        return Boolean.TRUE.equals(user.getIsActive());
    }
}