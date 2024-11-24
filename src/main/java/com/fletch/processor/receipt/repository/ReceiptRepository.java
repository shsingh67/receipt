package com.fletch.processor.receipt.repository;

import com.fletch.processor.receipt.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReceiptRepository extends JpaRepository<Receipt, String> {

    Receipt getReceiptsByReceiptId(String receiptId);
}
