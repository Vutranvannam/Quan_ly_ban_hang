package com.example.qly_kho.service.application;

import com.example.qly_kho.dto.request.user.UserCreateRequest;
import com.example.qly_kho.dto.request.user.UserSearchRequest;
import com.example.qly_kho.dto.request.user.UserUpdateRequest;
import com.example.qly_kho.dto.response.PageResponse;
import com.example.qly_kho.dto.response.user.UserResponse;

public interface UserAppService {

    UserResponse createUser(UserCreateRequest request, String ip, Long actorUserId);

    UserResponse updateUser(Long userId, UserUpdateRequest request, String ip, Long actorUserId);

    void deleteUser(Long userId, String ip, Long actorUserId);

    PageResponse<UserResponse> getUserList(UserSearchRequest request);

    UserResponse getUser(Long userId);
}
