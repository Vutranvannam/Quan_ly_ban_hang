package com.example.qly_kho.service.domain.impl;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.qly_kho.exception.ErrorCode;
import com.example.qly_kho.exception.custom.AppException;
import com.example.qly_kho.service.domain.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @Async
    public void sendUserCreatedEmail(String toEmail, String username, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("PosDash");

            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("password", password);

            String html = templateEngine.process("user-created-email", context);

            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Email build failed");
        } catch (MailException e) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Email send failed");
        }
    }

    @Override
    @Async
    public void sendResetPasswordEmail(String toEmail, String username, String newPassword) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Thông tin đăng nhập mới của bạn từ PosDash");

            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("password", newPassword);

            String html = templateEngine.process("reset-password-email", context);

            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Email build failed");
        } catch (MailException e) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Email send failed");
        }
    }

    
}
