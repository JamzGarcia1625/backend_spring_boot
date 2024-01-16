package com.tps.jurados.domain.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class HttpCryptoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;
    private String idError;

    public HttpCryptoException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpCryptoException(HttpStatus httpStatus, String message, String idError) {
        super(message);
        this.httpStatus = httpStatus;
        this.idError = idError;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, Object> getErrorData() {
        Map<String, Object> response = Map.of(
                "type", "error",
                "message", getMessage());
        if (this.idError != null)
            response.put("idError", this.idError);

        return response;
    }

}
