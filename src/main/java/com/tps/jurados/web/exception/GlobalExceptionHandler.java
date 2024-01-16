package com.tps.jurados.web.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tps.jurados.domain.dto.request.CryptoDataDtoRequest;
import com.tps.jurados.domain.exception.HttpCryptoException;
import com.tps.jurados.domain.exception.HttpGenericException;
import com.tps.jurados.domain.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CryptoUtil cryptoUtil;


    @ExceptionHandler(value = HttpCryptoException.class)
    protected ResponseEntity<Object> handleHttpCryptoException(HttpCryptoException exception, WebRequest request) throws JsonProcessingException {
        String data = objectMapper.writeValueAsString(exception.getErrorData());
        CryptoDataDtoRequest response = new CryptoDataDtoRequest();
        response.setData(cryptoUtil.encrypt(data));
        return handleExceptionInternal(exception, response, new HttpHeaders(), exception.getHttpStatus(), request);
    }
    @ExceptionHandler(value = HttpGenericException.class)
    protected ResponseEntity<Object> handleHttpGenericException(HttpGenericException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getErrorData(), new HttpHeaders(), exception.getHttpStatus(), request);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception, WebRequest request) {
        Map<String, String> errorData = Map.of(
                "type", "error",
                "message", "No se encontró el elemento solicitado");
        return handleExceptionInternal(exception, errorData, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = SQLException.class)
    protected ResponseEntity<Object> handleSQLException(SQLException exception, WebRequest request) {
        Map<String, String> errorData = Map.of(
                "type", "error",
                "message", exception.getMessage());
        return handleExceptionInternal(exception, errorData, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        Map<String, String> errorData = Map.of(
                "type", "error",
                "message", "Usted no cuenta con privilegios para ejecutar esta acción.");
        return handleExceptionInternal(exception, errorData, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = exception.getBindingResult().getAllErrors()
                .stream()
                .reduce(new HashMap<>(),
                        (errorMap, currentError) -> {
                            String key = currentError.getCode();
                            if (currentError instanceof FieldError) {
                                key = ((FieldError) currentError).getField();
                            }
                            String message = currentError.getDefaultMessage();
                            errorMap.put(key, message);
                            return errorMap;
                        },
                        (map1, map2) -> map1);

        Map<String, Object> errorData = Map.of(
                "type", "error",
                "message", "No se pudo procesar su solicitud. Por favor revise el detalle de los errores que se presentaron",
                "errors", errors);
        return handleExceptionInternal(exception, errorData, headers, status, request);
    }
}
