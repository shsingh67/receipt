package com.fletch.processor.receipt.exceptions;

import com.fletch.processor.receipt.dto.ErrorResponse;
import com.fletch.processor.receipt.dto.ReceiptError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides centralized error handling for validation and other custom exceptions in the receipt processing service.
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReceiptExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage(),
                        (existing, replacement) -> existing
                ));

        ErrorResponse errorResponse = new ReceiptError(errors, "The receipt is invalid.");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReceiptNotFoundException.class)
    protected ResponseEntity<Object> handleReceiptNotFoundException(ReceiptNotFoundException ex, WebRequest request) {

        ErrorResponse errorResponse = new ReceiptError(null, ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
