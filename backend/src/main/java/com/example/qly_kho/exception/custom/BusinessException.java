package com.example.qly_kho.exception.custom;

import com.example.qly_kho.exception.ErrorCode;

public class BusinessException extends AppException {
    
    public BusinessException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
    }
}
