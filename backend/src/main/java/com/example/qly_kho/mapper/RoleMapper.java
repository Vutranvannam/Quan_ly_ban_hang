package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.qly_kho.dto.response.role.RoleResponse;
import com.example.qly_kho.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    
    @Mapping(source = "id", target = "roleId")
    RoleResponse toRoleResponse(Role role);
}
