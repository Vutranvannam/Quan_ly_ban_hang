package com.example.qly_kho.exception.custom;

import com.example.qly_kho.exception.ErrorCode;

public class BadRequestException extends AppException{
    
    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }
}
