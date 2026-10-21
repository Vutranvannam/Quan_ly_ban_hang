package com.example.qly_kho.service.domain;

public interface ActivityLogService {

    //ghi nhật kí hoạt động của người dùng
    void logActivity(Long userId, String action, String entityType, long entityId, String description, String ipAddress);
}
