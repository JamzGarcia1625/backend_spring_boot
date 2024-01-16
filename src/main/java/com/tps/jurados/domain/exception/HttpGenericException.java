package com.tps.jurados.domain.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class HttpGenericException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;

    public HttpGenericException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, Object> getErrorData() {
        return Map.of(
                "type", "error",
                "message", getMessage());
    }

}
