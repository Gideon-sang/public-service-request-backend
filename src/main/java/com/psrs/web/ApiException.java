package com.psrs.web;

import org.springframework.http.HttpStatus;

/** Error that maps directly to an HTTP status and a user-friendly message. */
public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() { return status; }
}
