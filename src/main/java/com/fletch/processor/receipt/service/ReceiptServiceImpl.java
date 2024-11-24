package com.fletch.processor.receipt.service;

import com.fletch.processor.receipt.models.Item;
import com.fletch.processor.receipt.models.Receipt;
import com.fletch.processor.receipt.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ReceiptService} interface.
 * Provides business logic for creating, calculating points, and retrieving receipt data.
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {

    private static final int PURCHASE_DATE_POINTS = 6;

    private final static int PURCHASE_TIME_POINTS   = 10;

    private final static int NO_POINTS = 0;

    private final static int ROUND_DOLLAR_POINTS = 50;

    private static final LocalTime AFTER_2_PM = LocalTime.of(14, 0);

    private static final LocalTime BEFORE_4_PM = LocalTime.of(16, 0);

    private final static double PRICE_MULTIPLIER = 0.2;

    private final static String ROUND__DOLLAR_REGEX   = "-?\\d+(\\.00)?";

    private static final int DESCRIPTION_LENGTH_DIVISOR = 3;

    private static final int EVEN_LENGTH_DIVISOR = 2;

    private static final int ITEM_LENGTH_MULTIPLIER = 5;

    private static final double PRICE_THRESHOLD_DIVISOR = 0.25;

    private static final int PRICE_THRESHOLD_POINTS  = 25;

    private ReceiptRepository receiptRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }


    @Override
    public String createReceipt(Receipt receipt) {

        long retailerNamePoints  = calculateRetailerNamePoints(receipt.getRetailer());
        long retailerTotalPoints  = calculateRetailerTotalPoints(receipt.getTotal());
        long itemLengthPoints  = calculateItemLengthPoints(receipt.getItems().size());
        long descriptionLengthPoints  = calculateDescriptionLengthPoints(receipt.getItems());
        long purchaseDatePoints  = calculatePurchaseDatePoints(receipt.getPurchaseDate());
        long purchaseTimePoints  = calculatePurchaseTimePoints(receipt.getPurchaseTime());

        Long totalPoints = retailerNamePoints + retailerTotalPoints + itemLengthPoints
                + descriptionLengthPoints + purchaseDatePoints + purchaseTimePoints;

        receipt.setTotalPoints(totalPoints);

        Receipt savedReceipt = receiptRepository.save(receipt);
        return savedReceipt.getReceiptId();
    }

    public long calculateRetailerNamePoints(String retailer) {
        return retailer.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetterOrDigit)
                .mapToLong(c -> 1)
                .sum();
    }

    public long calculateRetailerTotalPoints(String total) {
        boolean isWholeNumber = total.matches(ROUND__DOLLAR_REGEX);
        long wholeNumberPoints = isWholeNumber ? ROUND_DOLLAR_POINTS : NO_POINTS;

        double totalPrice = Double.parseDouble(total);
        long priceThresholdPoints = (totalPrice % PRICE_THRESHOLD_DIVISOR == 0) ? PRICE_THRESHOLD_POINTS : NO_POINTS;

        return wholeNumberPoints + priceThresholdPoints;
    }

    public long calculateDescriptionLengthPoints(List<Item> items) {
        return items.stream()
                .filter(item -> item.getShortDescription().trim().length() % DESCRIPTION_LENGTH_DIVISOR == 0)
                .mapToLong(item -> (long) Math.ceil(Double.parseDouble(item.getPrice()) * PRICE_MULTIPLIER))
                .sum();
    }


    public long calculateItemLengthPoints(int itemCount) {
        return (long) itemCount / EVEN_LENGTH_DIVISOR * ITEM_LENGTH_MULTIPLIER;
    }

    public int calculatePurchaseDatePoints(String purchaseDate) {
        LocalDate date = LocalDate.parse(purchaseDate);
        return (date.getDayOfMonth() % EVEN_LENGTH_DIVISOR == 0) ? NO_POINTS : PURCHASE_DATE_POINTS;
    }


    public int calculatePurchaseTimePoints(String purchaseTime) {
        LocalTime time = LocalTime.parse(purchaseTime);
        return (time.isAfter(AFTER_2_PM) && time.isBefore(BEFORE_4_PM)) ? PURCHASE_TIME_POINTS : NO_POINTS;
    }

    @Override
    public Optional<Receipt> getReceipt(String id) {
        return receiptRepository.findById(id);
    }
}
