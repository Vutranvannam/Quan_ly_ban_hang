package com.example.qly_kho.service.domain;

import java.util.List;

import com.example.qly_kho.entity.Role;

public interface RoleService {
    
    // tìm kiếm role theo id
    Role findById(Long id);

    // Lấy danh sách role theo id
    List<Role> findByIds(List<Long> ids);

    // lấy danh sách roles
    List<Role> getAllRoles();

    //save role
    Role saveRole(Role role);
}
