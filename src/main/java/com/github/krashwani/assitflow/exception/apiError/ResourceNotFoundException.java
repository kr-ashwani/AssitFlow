package com.github.krashwani.assitflow.exception.apiError;

import com.github.krashwani.assitflow.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException{
    public ResourceNotFoundException( String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message, HttpStatus.NOT_FOUND);
    }
}
