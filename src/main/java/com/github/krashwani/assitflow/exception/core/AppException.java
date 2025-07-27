package com.github.krashwani.assitflow.exception.core;

import com.github.krashwani.assitflow.exception.enums.ErrorCode;

interface AppException {
    ErrorCode getErrorCode();
    String getMessage();
    Throwable getCause();
}