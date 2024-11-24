package com.fletch.processor.receipt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceiptError extends ErrorResponse {


    public ReceiptError(Map<String, String> errors, String description) {
        super(errors, description);

    }
}
