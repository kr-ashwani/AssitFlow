package com.github.krashwani.assitflow.payload;

import com.github.krashwani.assitflow.exception.ErrorCode;

record ApiError(ErrorCode code, String message) {
}
