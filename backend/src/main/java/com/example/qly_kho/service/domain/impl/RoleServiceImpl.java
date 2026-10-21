package com.example.qly_kho.service.domain.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.Role;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.repository.RoleRepository;
import com.example.qly_kho.service.domain.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    
    private final RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        
        return roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                String.format("Role with (id: %s) not found in RoleService", id)
            ));
    }
    
    

    @Override
    public List<Role> findByIds(List<Long> ids) {
        return roleRepository.findAllById(ids);
    }



    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new DuplicateException("Username already exists in UserService");
        }

        try {
            return roleRepository.save(role);

        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateException("Duplicate data in UserService: " + ex.getMessage());
        }
    }
}
