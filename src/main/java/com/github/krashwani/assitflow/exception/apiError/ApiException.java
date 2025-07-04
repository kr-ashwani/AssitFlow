package com.github.krashwani.assitflow.exception.apiError;

import com.github.krashwani.assitflow.exception.BaseAppException;
import com.github.krashwani.assitflow.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ApiException extends BaseAppException {
    private final HttpStatus httpStatus;

    public ApiException(ErrorCode errorCode, String message, HttpStatus status) {
        super(errorCode, message);
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
