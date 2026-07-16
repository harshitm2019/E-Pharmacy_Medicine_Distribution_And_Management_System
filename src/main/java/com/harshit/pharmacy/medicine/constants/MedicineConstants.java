package com.harshit.pharmacy.medicine.constants;

public final class MedicineConstants {

    private MedicineConstants() {}

    // Field length limits
    public static final int MEDICINE_NAME_MIN_LENGTH = 3;
    public static final int MEDICINE_NAME_MAX_LENGTH = 100;
    public static final int MANUFACTURER_MAX_LENGTH = 100;
    public static final int BATCH_NUMBER_MAX_LENGTH = 100;
    public static final int DESCRIPTION_MAX_LENGTH = 300;

    // Price / discount bounds
    public static final String MIN_PRICE = "0.01";
    public static final String MIN_DISCOUNT = "0.00";
    public static final String MAX_DISCOUNT = "100.00";

    // Stock
    public static final int MIN_STOCK_QUANTITY = 0;

    // Prescription need
    public static final String PRESCRIPTION_NEEDED = "YES";
    public static final String PRESCRIPTION_NOT_NEEDED = "NO";
    public static final String PRESCRIPTION_NEED_PATTERN = "^(YES|NO)$";
}