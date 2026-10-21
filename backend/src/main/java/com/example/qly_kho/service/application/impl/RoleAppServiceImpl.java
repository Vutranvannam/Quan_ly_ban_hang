package com.example.qly_kho.service.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.dto.response.role.RoleResponse;
import com.example.qly_kho.entity.Role;
import com.example.qly_kho.mapper.RoleMapper;
import com.example.qly_kho.service.application.RoleAppService;
import com.example.qly_kho.service.domain.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleAppServiceImpl implements RoleAppService {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getRoleList() {

        List<Role> roles = roleService.getAllRoles();

        return roles.stream()
                        .map(role -> 
                            roleMapper.toRoleResponse(role)
                        )
                            .toList();

    }
}
