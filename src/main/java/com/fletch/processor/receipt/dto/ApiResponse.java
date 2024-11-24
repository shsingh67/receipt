package com.fletch.processor.receipt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String id;
    private Long points;
}
