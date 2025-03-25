package com.hoctuan.codingforum.constant;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNAUTHORIZED("Unauthorized", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("Access denied", HttpStatus.NOT_ACCEPTABLE),
    WRONG_AUTHOR("Your are not the author", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTED("Email already existed", HttpStatus.BAD_REQUEST),
    NOT_FOUND("No % found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    POST_NOT_FOUND("Post not found", HttpStatus.NOT_FOUND),
    COMMENT_NOT_FOUND("Comment not found", HttpStatus.NOT_FOUND),
    PROBLEM_NOT_FOUND("Problem not found", HttpStatus.NOT_FOUND),
    SUBMISSION_NOT_FOUND("Submission not found", HttpStatus.NOT_FOUND),
    INVALID_TEST_CASE("Invalid test case", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_SUBMIT_TYPE("Unsupported submit type", HttpStatus.BAD_REQUEST),
    INVALID_ACCOUNT("Invalid account", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME_OR_PASSWORD("Account or password is incorrect.", HttpStatus.UNAUTHORIZED),
    NOT_ACCEPTABLE("Your account has been denied access.", HttpStatus.NOT_ACCEPTABLE),
    IS_DELETED_ACCOUNT("Your account has been deleted.", HttpStatus.BAD_REQUEST),
    IS_NOT_ACTIVE_ACCOUNT("Your account has not been activated.", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_FILE_TYPE("Unsupported file type.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
