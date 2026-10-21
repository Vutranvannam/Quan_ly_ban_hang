package com.example.qly_kho.security.userdetails;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.qly_kho.entity.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails{
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).toList();
    }

    @Override
    public @Nullable String getPassword() {
        
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {

        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {

        return user.isEnabled();
    }

    
}