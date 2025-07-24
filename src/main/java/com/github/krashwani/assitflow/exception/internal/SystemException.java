package com.github.krashwani.assitflow.exception.internal;

import com.github.krashwani.assitflow.exception.BaseAppException;
import com.github.krashwani.assitflow.exception.enums.ErrorCode;

public class SystemException extends BaseAppException {

    public SystemException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
