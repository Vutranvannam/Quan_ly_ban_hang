package com.example.qly_kho.service.application;

import java.util.List;

import com.example.qly_kho.dto.response.role.RoleResponse;

public interface RoleAppService {
    
    public List<RoleResponse> getRoleList();
}
