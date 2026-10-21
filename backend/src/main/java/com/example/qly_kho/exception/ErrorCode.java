package com.example.qly_kho.exception;

import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    BAD_REQUEST(400),        // 400 Bad Request – request sai format / thiếu dữ liệu
    NOT_FOUND(404),          // 404 Not Found – không tìm thấy tài nguyên
    UNAUTHORIZED(401),       // 401 Unauthorized – chưa đăng nhập / token không hợp lệ
    FORBIDDEN(403),          // 403 Forbidden – không có quyền truy cập
    DUPLICATE(409),          // 409 Conflict – dữ liệu bị trùng (vd: username đã tồn tại)
    VALIDATION_ERROR(422),   // 422 Unprocessable Entity – dữ liệu không hợp lệ (validate fail)
    INTERNAL_ERROR(500);      // 500 Internal Server Error – lỗi hệ thống
   
    private final int status;

    ErrorCode(int status) {
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return HttpStatusCode.valueOf(status);
    }

    public int getStatusValue() {
        return status;
    }
}

