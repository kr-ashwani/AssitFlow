package com.github.krashwani.assitflow.payload;

import com.github.krashwani.assitflow.exception.ErrorCode;
import lombok.Builder;
import lombok.Value;
import java.time.Instant;

@Value
@Builder
public class ApiResponse<T> {
    boolean success;
    T data;
    ApiError error;
    String message;
    Instant timestamp;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(data.toString())
                .timestamp(Instant.now())
                .build();
    }

    public static <T> ApiResponse<T> failure(ErrorCode code, String msg) {
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .error(new ApiError(code, msg))
                .message(msg)
                .timestamp(Instant.now())
                .build();
    }
}
