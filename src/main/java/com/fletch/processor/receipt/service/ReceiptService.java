package com.fletch.processor.receipt.service;

import com.fletch.processor.receipt.models.Receipt;

import java.util.Optional;

/**
 * Service interface for handling operations related to receipts.
 */
public interface ReceiptService {

    public String createReceipt(Receipt receipt);

    public Optional<Receipt> getReceipt(String id);
}
