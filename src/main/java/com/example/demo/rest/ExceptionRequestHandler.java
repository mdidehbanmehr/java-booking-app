package com.example.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionRequestHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return handleUnexpectedError(e);
    }

    private ResponseEntity<ErrorResponse> handleUnexpectedError(Exception e) {
        return response(
                new ErrorResponse(
                        "An unexpected error occurred" + (e.getMessage() != null ? ": " + e.getMessage() : "."),
                        "INTERNAL_SERVER_ERROR"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<ErrorResponse> response(ErrorResponse errorResponse, HttpStatus status) {
        return ResponseEntity.status(status).body(errorResponse);
    }

    public static class ErrorResponse {
        private final String message;
        private final String cause;
        private final Object details;

        public ErrorResponse(String message, String cause) {
            this(message, cause, null);
        }

        public ErrorResponse(String message, String cause, Object details) {
            this.message = message;
            this.cause = cause;
            this.details = details;
        }

        public String getMessage() {
            return message;
        }

        public String getCause() {
            return cause;
        }

        public Object getDetails() {
            return details;
        }
    }
}
