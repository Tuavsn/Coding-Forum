package com.hoctuan.codingforum.exception;

public class CustomException extends RuntimeException{
    private int statusCode;

    public CustomException() {}

    public CustomException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public CustomException(Throwable cause, int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public CustomException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    protected CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
