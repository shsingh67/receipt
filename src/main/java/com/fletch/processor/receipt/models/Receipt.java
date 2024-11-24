package com.fletch.processor.receipt.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String receiptId;

    @NotBlank(message = "Retailer name cannot be null or empty.")
    private String retailer;

    @NotBlank(message = "Purchase date cannot be null or empty.")
    private String purchaseDate;

    @NotBlank(message = "Purchase time cannot be null or empty.")
    private String purchaseTime;

    @Valid
    @NotNull(message = "Items list cannot be null.")
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @NotBlank(message = "Total cannot be null or empty.")
    @Pattern(regexp = "-?\\d+(\\.\\d+)?", message = "Total must be a valid number (whole or decimal).")
    private String total;

    private Long totalPoints;

}
