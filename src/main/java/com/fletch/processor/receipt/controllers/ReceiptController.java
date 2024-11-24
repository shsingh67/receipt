package com.fletch.processor.receipt.controllers;

import com.fletch.processor.receipt.dto.ApiResponse;
import com.fletch.processor.receipt.exceptions.ReceiptNotFoundException;
import com.fletch.processor.receipt.models.Receipt;
import com.fletch.processor.receipt.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/receipts")
public class ReceiptController {

    ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    /**
     * Handles the submission of receipt data for processing.
     * @param receipt object containing receipt detials such as retailer, purchase date, time, etc....
     * @return the unique identifier (ID) of the processed receipt
     */
    @PostMapping(value = "/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createReceipt(@Valid @RequestBody Receipt receipt) {
        ApiResponse response = new ApiResponse();
        String receiptId = receiptService.createReceipt(receipt);

        response.setId(receiptId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the total points associated with a receipt
     * @param id the unique identifier of the receipt.
     * @return a ResponseEntity containing an ApiResponse with the receipt's total points,
     *         or an error response if the receipt is not found.
     */
    @GetMapping(value = "/{id}/points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getPoints(@PathVariable String id) {
        Optional<Receipt> receiptOptional = receiptService.getReceipt(id);

        if (receiptOptional.isEmpty()) {
            throw new ReceiptNotFoundException("No receipt found for that id");
        }

        Receipt receipt = receiptOptional.get();
        ApiResponse response = new ApiResponse();
        response.setPoints(receipt.getTotalPoints());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
