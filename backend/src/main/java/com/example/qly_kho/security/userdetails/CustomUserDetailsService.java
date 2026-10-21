package com.example.qly_kho.security.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.qly_kho.entity.User;
import com.example.qly_kho.service.domain.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userService.findByUsernameAndDeletedAtIsNull(username);
        return new CustomUserDetails(user);         
    }


}
