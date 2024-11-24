package com.fletch.processor.receipt.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String itemId;

    @NotBlank(message = "Item short description cannot be null or empty.")
    private String shortDescription;

    @NotBlank(message = "Item price cannot be null or empty.")
    @Pattern(regexp = "-?\\d+(\\.\\d+)?", message = "Item price must be a valid number (whole or decimal).")
    private String price;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
}
