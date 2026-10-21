package com.example.qly_kho.exception.custom;

import com.example.qly_kho.exception.ErrorCode;

public class AppException extends RuntimeException {
    
    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
