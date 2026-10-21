package com.example.qly_kho.exception.custom;

import com.example.qly_kho.exception.ErrorCode;

public class ForbiddenException  extends AppException{
    
    public ForbiddenException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }   
}
