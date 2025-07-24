package com.github.krashwani.assitflow.payload;

import com.github.krashwani.assitflow.exception.enums.ErrorCode;

record ApiError(ErrorCode code, String message) {
}
