package com.github.krashwani.assitflow.exception;

import com.github.krashwani.assitflow.exception.enums.ErrorCode;

public abstract class BaseAppException extends RuntimeException implements AppException {

    private final ErrorCode errorCode;

    public BaseAppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseAppException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public Throwable getCause() {
        return super.getCause();
    }
}

