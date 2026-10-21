package com.example.qly_kho.service.application;

import com.example.qly_kho.dto.request.auth.LoginRequest;
import com.example.qly_kho.dto.request.auth.RegisterRequest;
import com.example.qly_kho.dto.response.auth.AuthResponse;

public interface AuthService {
    
    //xử lý đăng nhập
    AuthResponse login(LoginRequest loginRequest );

    //xử lý đăng xuất
    void logout(String refreshToken);

    //xử lý đăng ký
    void register(RegisterRequest registerRequest);

    //xử lý đổi mật khẩu về email
    void recoverPassword(String email);

    //xử lý xin cấp access token từ refresh token
    AuthResponse refreshToken(String refreshToken);

}