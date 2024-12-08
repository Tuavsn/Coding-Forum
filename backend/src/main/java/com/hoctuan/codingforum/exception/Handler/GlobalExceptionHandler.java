package com.hoctuan.codingforum.exception.Handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hoctuan.codingforum.common.BaseEntity;
import com.hoctuan.codingforum.common.BaseResponse;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.exception.NotFoundException;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> handleCustomException(CustomException e) {
        BaseResponse response = BaseResponse.builder()
                .status(e.getStatusCode())
                .message(e.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Authentication Exeption
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<BaseResponse> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {
        BaseResponse response = BaseResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Yêu cầu của bạn không được xác thực, vui lòng thử lại.")
                .data(null)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // JWT Exception
    @ExceptionHandler({AuthenticationException.class, JwtException.class})
    public ResponseEntity<BaseResponse> handleAuthenticationException(AuthenticationException e) {
        BaseResponse response = BaseResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Thông tin xác thực không hợp lệ hoặc đã hết hạn. Vui lòng đăng nhập và thử lại.")
                .data(null)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Access Denied Exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleAccessDeniedException(AccessDeniedException e) {
        BaseResponse response = BaseResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("Bạn không có quyền truy cập vào tài nguyên này.")
                .data(null)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException e) {
        BaseResponse response = BaseResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationException(MethodArgumentNotValidException e) {
        BaseResponse response = BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getBindingResult().getFieldError().getDefaultMessage())
                .data(null)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
