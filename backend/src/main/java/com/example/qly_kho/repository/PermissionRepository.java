package com.example.qly_kho.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qly_kho.entity.Permission;

@Repository
public interface PermissionRepository  extends JpaRepository<Permission, Long> {
    
}
