package com.github.krashwani.assitflow.exception.domain;

import com.github.krashwani.assitflow.exception.apiError.ApiException;
import com.github.krashwani.assitflow.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class TicketAssignmentException extends ApiException {
    public TicketAssignmentException(String message) {
        super(ErrorCode.ASSIGNMENT_FAILURE, message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

