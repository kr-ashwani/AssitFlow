package com.github.krashwani.assitflow.exception.domain;

import com.github.krashwani.assitflow.exception.apiError.ApiException;
import com.github.krashwani.assitflow.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class AgentNotFoundException extends ApiException {
    public AgentNotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message, HttpStatus.NOT_FOUND);
    }
}
