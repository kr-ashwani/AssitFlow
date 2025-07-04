package com.github.krashwani.assitflow.exception;

interface AppException {
    ErrorCode getErrorCode();
    String getMessage();
    Throwable getCause();
}