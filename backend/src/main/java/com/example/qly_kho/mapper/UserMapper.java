package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.qly_kho.dto.response.auth.AuthUserResponse;
import com.example.qly_kho.dto.response.user.UserResponse;
import com.example.qly_kho.entity.Role;
import com.example.qly_kho.entity.User;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleToString")
    AuthUserResponse toAuthUserResponse(User user);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRoleToString")
    @Mapping(source = "accountNonLocked", target = "locked", qualifiedByName = "invertBoolean")
    UserResponse toUserResponse(User user);

    @Named("mapRoleToString")
    default String mapRoleToString(Role role) {
        return role.getName();
    } 

    @Named("invertBoolean")
    default boolean invertBoolean(Boolean value) {
        return !value;
    }
}
