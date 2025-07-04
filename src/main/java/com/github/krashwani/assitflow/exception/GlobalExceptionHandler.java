package com.github.krashwani.assitflow.exception;

import com.github.krashwani.assitflow.exception.apiError.BadRequestException;
import com.github.krashwani.assitflow.exception.apiError.ResourceNotFoundException;
import com.github.krashwani.assitflow.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.failure(ex.getErrorCode(), ex.getMessage())
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(BadRequestException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.failure(ex.getErrorCode(), ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        // Enhanced error message formatting
        String errorMessage = fieldErrors.entrySet().stream()
                .map(entry -> String.format("'%s': %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                ApiResponse.failure(ErrorCode.VALIDATION_FAILED, "Validation failed: " + errorMessage)
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());

        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity.badRequest().body(
                ApiResponse.failure(ErrorCode.VALIDATION_FAILED, "Constraint violation: " + errorMessage)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
        log.warn("JSON parse error: {}", ex.getMessage());

        String userFriendlyMessage = "Invalid JSON format: please check your request body";

        Throwable rootCause = ex.getCause();
        if (rootCause != null) {
            String causeMsg = rootCause.getMessage();

            if (causeMsg.contains("Cannot deserialize")) {
                userFriendlyMessage = "Invalid field type: expected different data type for one or more fields";
            } else if (causeMsg.contains("Unrecognized field")) {
                userFriendlyMessage = "Unknown field detected: request contains unexpected field names";
            } else if (causeMsg.contains("Cannot construct instance")) {
                userFriendlyMessage = "Invalid object structure: nested object format is incorrect";
            } else if (causeMsg.contains("required")) {
                userFriendlyMessage = "Missing required field: one or more mandatory fields are missing";
            }
        }

        return ResponseEntity.badRequest().body(
                ApiResponse.failure(ErrorCode.BAD_REQUEST, userFriendlyMessage)
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Type mismatch: {}", ex.getMessage());

        String message = String.format("Invalid parameter type: '%s' should be of type %s",
                ex.getName(), ex.getRequiredType().getSimpleName());

        return ResponseEntity.badRequest().body(
                ApiResponse.failure(ErrorCode.BAD_REQUEST, message)
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingParameter(MissingServletRequestParameterException ex) {
        log.warn("Missing parameter: {}", ex.getMessage());

        String message = String.format("Missing required parameter: '%s' of type %s",
                ex.getParameterName(), ex.getParameterType());

        return ResponseEntity.badRequest().body(
                ApiResponse.failure(ErrorCode.BAD_REQUEST, message)
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("Method not supported: {}", ex.getMessage());

        String message = String.format("HTTP method '%s' not supported for this endpoint. Supported methods: %s",
                ex.getMethod(), String.join(", ", ex.getSupportedMethods()));

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                ApiResponse.failure(ErrorCode.METHOD_NOT_ALLOWED, message)
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        log.warn("No handler found: {}", ex.getMessage());

        String message = String.format("Endpoint not found: %s %s", ex.getHttpMethod(), ex.getRequestURL());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.failure(ErrorCode.NOT_FOUND, message)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());

        String message = "Data integrity violation";

        // Check for common constraint violations
        if (ex.getCause() instanceof SQLIntegrityConstraintViolationException) {
            String rootMessage = ex.getRootCause().getMessage();
            if (rootMessage.contains("Duplicate entry")) {
                message = "Duplicate entry: record already exists";
            } else if (rootMessage.contains("foreign key constraint")) {
                message = "Foreign key constraint violation: referenced record does not exist";
            }
        }


        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.failure(ErrorCode.CONFLICT, message)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(
                ApiResponse.failure(ErrorCode.BAD_REQUEST, ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);

        boolean isDev = activeProfile.equalsIgnoreCase("dev");
        String message = isDev ? ex.getMessage() : "An unexpected error occurred";

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR, message)
        );
    }
}