package com.github.krashwani.assitflow.exception.apiError;

import com.github.krashwani.assitflow.exception.core.BaseAppException;
import com.github.krashwani.assitflow.exception.enums.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends BaseAppException {
    private final HttpStatus httpStatus;

    public ApiException(ErrorCode errorCode, String message, HttpStatus status) {
        super(errorCode, message);
        this.httpStatus = status;
    }
}
