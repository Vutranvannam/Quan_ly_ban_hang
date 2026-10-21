package com.example.qly_kho.service.domain;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.qly_kho.entity.User;

public interface UserService {

    void existsByUsername(String username);

    void existsByEmail(String email);

    //tìm kiếm user theo username
    User findByUsername(String username);

    //tìm kiếm user theo username và kiểm tra tài khoản bị xóa chưa
    User findByUsernameAndDeletedAtIsNull(String username);

    //tìm kiếm user theo id
    User findById(Long id);

    //tìm kiếm user theo email
    User findByEmail(String email);

    //tạo user
    User saveUser(User user);

    //xóa user
    User deletedUser(User user);

    //lấy user id theo vai trò 
    Set<Long> findUserIdByRoleId(Long roleId);

    //
    void incrementPermissionVersionByUserIds(Set<Long> userIds);

    //tìm kiếm user theo keyword
    Page<User> searchUser(String keyword, Pageable pageable);
}
