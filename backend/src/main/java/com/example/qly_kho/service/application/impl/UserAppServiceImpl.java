package com.example.qly_kho.service.application.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.qly_kho.constant.ActionConstants;
import com.example.qly_kho.dto.request.user.UserCreateRequest;
import com.example.qly_kho.dto.request.user.UserSearchRequest;
import com.example.qly_kho.dto.request.user.UserUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.user.UserResponse;
import com.example.qly_kho.entity.Role;
import com.example.qly_kho.entity.User;
import com.example.qly_kho.exception.custom.ForbiddenException;
import com.example.qly_kho.exception.custom.NotFoundException;
import com.example.qly_kho.mapper.UserMapper;
import com.example.qly_kho.security.rbac.RBACService;
import com.example.qly_kho.service.application.UserAppService;
import com.example.qly_kho.service.domain.ActivityLogService;
import com.example.qly_kho.service.domain.EmailService;
import com.example.qly_kho.service.domain.RefreshTokenService;
import com.example.qly_kho.service.domain.RoleService;
import com.example.qly_kho.service.domain.UserService;
import com.example.qly_kho.util.CodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAppServiceImpl implements UserAppService {

    private final UserService userService;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final ActivityLogService activityLogService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RBACService rbacService;

    @Override
    public UserResponse createUser(UserCreateRequest request, String ip, Long actorUserId) {

        userService.existsByUsername(request.username());
        userService.existsByEmail(request.email());

        String code = CodeGenerator.generateUserCode();

        User newUser = User.createUser(
            request.username(),
            passwordEncoder.encode(code),
            request.email(),
            request.fullName()
        );
        
        List<Role> roles = roleService.findByIds(request.roleIds());

        roles.forEach(role -> newUser.addRole(role));

        if(request.status()) newUser.enable();
        else newUser.disable();

        User savedUser = userService.saveUser(newUser);

        emailService.sendUserCreatedEmail(request.email(), request.username(), code);

        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.CREATE, 
            ActionConstants.ENTITY_USER, 
            savedUser.getId(), 
            "User created successfully", 
            ip
        );

        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request, String ip, Long actorUserId) {

        if(actorUserId.equals(userId)) {
            throw new ForbiddenException("You cannot update yourself");
        }

        User user = userService.findById(userId);

        if(request.fullName() != null) {
            user.changeFullName(request.fullName());
        }

        if(request.email() != null) {
            user.changeEmail(request.email());
        }

        if (request.status() != null) {
            if (request.status()) user.enable();
            else user.disable();
        }

        if (request.locked() != null) {
            if (request.locked()) user.lock();
            else user.unlock();
        }

        if(request.roleIds() != null && !request.roleIds().isEmpty()) {

            rbacService.updateRolesFromUser(userId, request.roleIds());
        }

        User savedUser = userService.saveUser(user);

        activityLogService.logActivity(
            actorUserId,
            ActionConstants.UPDATE,
            ActionConstants.ENTITY_USER,
            savedUser.getId(),
            "User updated successfully",
            ip
        );

        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public void deleteUser(Long userId, String ip, Long actorUserId) {

        if(actorUserId.equals(userId)) {
            throw new ForbiddenException("You cannot delete yourself");
        };

        User user = userService.findById(userId);

        if(user.getDeletedAt() != null) {
            throw new NotFoundException(
                String.format("Category with id %d has already been deleted", userId)
            );
        }

        userService.deletedUser(user);

        refreshTokenService.revokeRefreshTokenFromUserId(userId);

        
        activityLogService.logActivity(
            actorUserId, 
            ActionConstants.DELETE, 
            ActionConstants.ENTITY_USER, 
            userId, 
            "User deleted successfully", 
            ip
        );
    }

    @Override
    public PageResponse<UserResponse> getUserList(UserSearchRequest request) {

        int page = request.page();
        int size = request.size();
        String keyword = request.keyword();

        PageRequest pageable = PageRequest.of(
            page, 
            size,
            Sort.by("deletedAt").descending()
        );

        Page<User> userPage = userService.searchUser(keyword, pageable);


        List<UserResponse> data = userPage.getContent()
                                    .stream()
                                    .map(u -> userMapper.toUserResponse(u))
                                    .toList();

        return new PageResponse<>(
            data,
            userPage.getNumber(),
            userPage.getSize(),
            userPage.getTotalElements(),
            userPage.getTotalPages()
        );
    }
    
    @Override
    public UserResponse getUser(Long userId) {

        return userMapper.toUserResponse(userService.findById(userId));
    }
}
