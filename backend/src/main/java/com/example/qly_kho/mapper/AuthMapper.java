package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.qly_kho.dto.response.auth.AuthResponse;
import com.example.qly_kho.entity.User;

@Mapper(
    componentModel = "spring", 
    uses = UserMapper.class
)
public interface AuthMapper {

    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    @Mapping(target = "tokenType", source = "tokenType")
    @Mapping(target = "user", source = "user")
    AuthResponse toAuthResponse(String accessToken, String refreshToken, String tokenType, User user);
}
