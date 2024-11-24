package com.fletch.processor.receipt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorResponse {
    Map<String, String> errors;
    private String description;

    public ErrorResponse(Map<String, String> errorMessage, String description) {
        this.errors = errorMessage;
        this.description = description;
    }

}
