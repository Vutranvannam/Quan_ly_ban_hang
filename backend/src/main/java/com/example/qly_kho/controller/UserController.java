package com.example.qly_kho.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.qly_kho.dto.request.user.UserCreateRequest;
import com.example.qly_kho.dto.request.user.UserSearchRequest;
import com.example.qly_kho.dto.request.user.UserUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.user.UserResponse;
import com.example.qly_kho.security.cache.UserCache;
import com.example.qly_kho.service.application.UserAppService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserAppService userApplicationService;

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    public ResponseEntity<Void> createUser(
        @Valid @RequestBody UserCreateRequest request, 
        HttpServletRequest httpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {

        userApplicationService.createUser(
            request, 
            httpServletRequest.getRemoteAddr(),
            user.getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
        @PathVariable Long userId, 
        @Valid @RequestBody UserUpdateRequest request, 
        HttpServletRequest httpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {
        
        return ResponseEntity.ok(
            userApplicationService.updateUser(
                userId, 
                request, 
                httpServletRequest.getRemoteAddr(),
                user.getId()
            )
        );
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
        @PathVariable Long userId, 
        HttpServletRequest httpServletRequest,
        @AuthenticationPrincipal UserCache user
    ) {

        userApplicationService.deleteUser(
            userId, 
            httpServletRequest.getRemoteAddr(),
            user.getId()
        );
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping
    public PageResponse<UserResponse> getUserList(@Valid @ModelAttribute UserSearchRequest request) {

        return userApplicationService.getUserList(request);
    }

    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {

        return ResponseEntity.ok(userApplicationService.getUser(userId));
    }
}
