package com.tps.jurados.domain.exception;

public class ImportFileValidationException extends RuntimeException {
    private final String key;

    public ImportFileValidationException(String key, String message) {
        super(message);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
