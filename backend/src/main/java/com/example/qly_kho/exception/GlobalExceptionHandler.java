package com.example.qly_kho.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.qly_kho.exception.custom.AppException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {

        HttpStatusCode status = ex.getErrorCode().getStatus();
        ErrorResponse errorResponse = new ErrorResponse(
            status.value(), 
            ex.getErrorCode().name(), 
            ex.getMessage(), 
            System.currentTimeMillis()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }    

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {

        return ResponseEntity.status(ErrorCode.FORBIDDEN.getStatus()).body(new ErrorResponse(
            ErrorCode.FORBIDDEN.getStatus().value(), 
            ErrorCode.FORBIDDEN.name(), 
            ex.getMessage(), 
            System.currentTimeMillis()
        ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getStatus())
            .body(new ErrorResponse(
                ErrorCode.UNAUTHORIZED.getStatus().value(),
                ErrorCode.UNAUTHORIZED.name(),
                "Unauthorized", 
                System.currentTimeMillis()
            ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.getStatus()).body(new ErrorResponse(
            ErrorCode.INTERNAL_ERROR.getStatus().value(), 
            ErrorCode.INTERNAL_ERROR.name(), 
            ex.getMessage(), 
            System.currentTimeMillis()
        ));
    }
}
