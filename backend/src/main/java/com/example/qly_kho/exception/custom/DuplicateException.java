package com.example.qly_kho.exception.custom;

import com.example.qly_kho.exception.ErrorCode;

public class DuplicateException extends AppException {

    public DuplicateException(String message) {
        super(ErrorCode.DUPLICATE, message);
    }
}
