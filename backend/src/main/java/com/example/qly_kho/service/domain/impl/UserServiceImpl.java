package com.example.qly_kho.service.domain.impl;

import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.entity.User;
import com.example.qly_kho.exception.custom.DuplicateException;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.repository.UserRepository;
import com.example.qly_kho.service.domain.UserService;
import com.example.qly_kho.specification.UserSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void existsByUsername(String username) {
        
        if (userRepository.existsByEmail(username)) {
            throw new DuplicateException("Username already exists in UserService");
        }
    }

    @Override
    public void existsByEmail(String email) {

        if (userRepository.existsByUsername(email)) {
            throw new DuplicateException("Email already exists in UserService");
        }
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException(
                String.format("User with username: %s not found in UserService", username)
            ));
    }


    @Override
    public User findByUsernameAndDeletedAtIsNull(String username) {

        return userRepository.findByUsernameAndDeletedAtIsNull(username)
            .orElseThrow(() -> new NotFoundException(
                String.format("User with username: %s not found in UserService", username)
            ));
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                String.format("User with (id: %s) not found in UserService", id)
            ));
    }

    

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(
                String.format("User with email: %s not found in UserService", email)
            ));
    }


    @Override
    public User saveUser(User user) {

        try {
            return userRepository.save(user);

        } catch (DataIntegrityViolationException ex) {

            throw new DuplicateException("Duplicate data");
        }
    }

    @Override
    public User deletedUser(User user) {
        user.deletedUser();
        return saveUser(user);
    }

    @Override
    public Set<Long> findUserIdByRoleId(Long roleId) {
       return userRepository.findUserIdByRoleId(roleId);
    }

    @Override
    public void incrementPermissionVersionByUserIds(Set<Long> userIds) {
        userRepository.incrementPermissionVersionByUserIds(userIds);
    }

    @Override
    public Page<User> searchUser(String keyword, Pageable pageable) {

        Specification<User> specification = Specification
                                                .where(UserSpecification.hasUsername(keyword))
                                                .or(UserSpecification.hasEmail(keyword))
                                                .or(UserSpecification.hasFullName(keyword));

        return userRepository.findAll(specification, pageable);
    }
}
