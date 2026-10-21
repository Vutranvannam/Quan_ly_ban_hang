package com.example.qly_kho.security.cache;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class UserCache {

    private Long id;
    private String username;

    private boolean enabled;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;

    private Set<String> authorities;

    private Long authVersion;
    private Long permissionVersion;

    @JsonCreator
    public UserCache(
        @JsonProperty("id") Long id,
        @JsonProperty("username") String username,
        @JsonProperty("enabled") boolean enabled,
        @JsonProperty("accountNonLocked") boolean accountNonLocked,
        @JsonProperty("accountNonExpired") boolean accountNonExpired,
        @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
        @JsonProperty("authorities") Set<String> authorities,
        @JsonProperty("authVersion") Long authVersion,
        @JsonProperty("permissionVersion") Long permissionVersion
    ) {
        this.id = id;
        this.username = username;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = authorities;
        this.authVersion = authVersion;
        this.permissionVersion = permissionVersion;
    }

    @JsonIgnore
    public Set<GrantedAuthority> getGrantedAuthorities() {
        return authorities == null
                ? Set.of()
                : authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
    }

}