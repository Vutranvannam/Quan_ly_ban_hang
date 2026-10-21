package com.example.qly_kho.service.domain;


public interface RefreshTokenService {

    //thêm refresh token khi user đăng nhập vào db
    public void addRefreshTokenFromUsername(String token, String username);

    //kiểm tra refresh token hợp lệ, chưa bị revoked, chưa hết hạn
    public void validateRefreshToken(String token);

    //vô hiệu tất cả các refresh token của user
    public void revokeRefreshTokenFromUserId(Long userId);
}
