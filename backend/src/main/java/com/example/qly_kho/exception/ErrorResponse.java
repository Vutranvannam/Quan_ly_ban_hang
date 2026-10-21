package com.example.qly_kho.exception;

public record ErrorResponse(
    int status,
    String code,
    String message,
    long timestamp
) {}