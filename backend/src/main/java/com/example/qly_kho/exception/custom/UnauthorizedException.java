package com.example.qly_kho.exception.custom;

import com.example.qly_kho.exception.ErrorCode;

public class UnauthorizedException extends AppException {
    
    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }  
}
