package com.fletch.processor.receipt.exceptions;

public class ReceiptNotFoundException extends RuntimeException {

    String message;

    public ReceiptNotFoundException(String message) {
        super(message);
    }
}
