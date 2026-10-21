package com.example.qly_kho.service.domain.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.Permission;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.repository.PermissionRepository;
import com.example.qly_kho.service.domain.PermissionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    @Override
    public Permission findById(Long id) {

        return permissionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                String.format("Permission with (id: %s) not found in PermissionService", id)
            ));
    }
}
