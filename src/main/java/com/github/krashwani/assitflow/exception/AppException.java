package com.github.krashwani.assitflow.exception;

import com.github.krashwani.assitflow.exception.enums.ErrorCode;

interface AppException {
    ErrorCode getErrorCode();
    String getMessage();
    Throwable getCause();
}