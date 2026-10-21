package com.example.qly_kho.security.cache;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.qly_kho.entity.User;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCacheService {

    private final UserRepository userRepository;


    @Cacheable(value = "user_version", key = "#username")
    public UserVersionCache getUserVersionCache(String username) {

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found"));

        return new UserVersionCache(
            username,
            user.getAuthVersion(),
            user.getPermissionVersion()
        );
    }

    @Cacheable(
        value = "user_security",
        key = "#username + ':v:' + #authVersion + ':p:' + #permissionVersion"
    )
    public UserCache getUserCacheByUsername(
            String username,
            Long authVersion,
            Long permissionVersion
    ) {

        User user = userRepository.findByUsernameWithRolesAndPermissions(username)
            .orElseThrow(() ->
                new NotFoundException("User not found: " + username)
            );

        Set<String> authorities =
            user.getRoles() == null
                    ? Set.of()
                    : user.getRoles().stream()
                            .filter(r -> r != null)
                            .flatMap(role -> {

                                Stream<String> roleAuth =
                                        Stream.of("ROLE_" + role.getName());

                                Stream<String> permAuth =
                                        role.getPermissions() == null
                                                ? Stream.empty()
                                                : role.getPermissions()
                                                        .stream()
                                                        .map(p -> p.getName());

                                return Stream.concat(roleAuth, permAuth);
                            })
                            .collect(Collectors.toSet());

        return new UserCache(
                user.getId(),
                user.getUsername(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                authorities,
                user.getAuthVersion(),
                user.getPermissionVersion()
        );
    }
}