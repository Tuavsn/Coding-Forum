package com.hoctuan.codingforum.constant;

import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    // Common
    SUCCESS(0, "Success"),
    FAIL(1, "Fail"),
    NOT_FOUND(2, "Not found"),
    UNAUTHORIZED(3, "Unauthorized"),
    FORBIDDEN(4, "Forbidden"),
    INVALID_PARAM(5, "Invalid param"),
    INVALID_TOKEN(6, "Invalid token"),
    INVALID_PASSWORD(7, "Invalid password"),
    INVALID_EMAIL(8, "Invalid email"),
    INVALID_USERNAME(9, "Invalid username"),
    INVALID_PHONE(10, "Invalid phone"),
    INVALID_CAPTCHA(11, "Invalid captcha"),
    INVALID_FILE(12, "Invalid file"),
    INVALID_GROUP(13, "Invalid group"),
    INVALID_TOPIC(14, "Invalid topic"),
    INVALID_POST(15, "Invalid post"),
    INVALID_COMMENT(16, "Invalid comment"),
    INVALID_MESSAGE(17, "Invalid message"),
    INVALID_USER(18, "Invalid user"),
    INVALID_ROLE(19, "Invalid role"),
    INVALID_PERMISSION(20, "Invalid permission"),
    INVALID_STATUS(21, "Invalid status"),
    INVALID_TYPE(22, "Invalid type"),
    INVALID_DATE(23, "Invalid date"),
    INVALID_TIME(24, "Invalid time"),
    INVALID_URL(25, "Invalid url"),
    INVALID_TITLE(26, "Invalid title"),
    INVALID_CONTENT(27, "Invalid content"),
    INVALID_DESCRIPTION(28, "Invalid description"),
    INVALID_IMAGE(29, "Invalid image"),
    INVALID_AVATAR(30, "Invalid avatar"),
    INVALID_NAME(31, "Invalid name"),
    INVALID_FULLNAME(32, "Invalid fullname"),
    INVALID_BIRTHDAY(33, "Invalid birthday");

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatus;

    ErrorCode(int code, String message, HttpStatusCode httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
