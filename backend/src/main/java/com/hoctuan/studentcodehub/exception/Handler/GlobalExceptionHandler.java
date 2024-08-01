package com.hoctuan.studentcodehub.exception.Handler;

import com.hoctuan.studentcodehub.common.BaseResponse;
import com.hoctuan.studentcodehub.exception.CustomException;
import com.hoctuan.studentcodehub.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
