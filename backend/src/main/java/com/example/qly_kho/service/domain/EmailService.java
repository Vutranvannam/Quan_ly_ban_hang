package com.example.qly_kho.service.domain;

public interface EmailService {
    void sendUserCreatedEmail(String toEmail, String username, String password);

    void sendResetPasswordEmail(String toEmail, String username, String newPassword);
}
