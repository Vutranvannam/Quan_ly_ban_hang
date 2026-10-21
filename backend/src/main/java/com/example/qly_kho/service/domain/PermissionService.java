package com.example.qly_kho.service.domain;

import com.example.qly_kho.entity.Permission;

public interface PermissionService {

    //tìm permission theo id
    public Permission findById(Long id);
}
