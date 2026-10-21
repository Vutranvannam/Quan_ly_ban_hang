package com.example.qly_kho.exception.custom;

import com.example.qly_kho.exception.ErrorCode;

public class NotFoundException extends AppException {

    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }
}
