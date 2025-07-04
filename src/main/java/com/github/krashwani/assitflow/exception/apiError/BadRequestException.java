package com.github.krashwani.assitflow.exception.apiError;

import com.github.krashwani.assitflow.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException{
    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST, message, HttpStatus.BAD_REQUEST);
    }
}
