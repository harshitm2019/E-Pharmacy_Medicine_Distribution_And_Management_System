package com.harshit.pharmacy.common.constants;



public final class ErrorMessages {



    private ErrorMessages() {}

    // Generic field validation
    private static final String FIELD_IS_REQUIRED = " is required.";
    public static final String FIELD_ALREADY_EXISTS = "%s already exists.";
    public static final String FIELD_NOT_EXISTS = " does not exist.";

    public static final String USERNAME_IS_REQUIRED = FieldNames.USERNAME + FIELD_IS_REQUIRED;
    public static final String PASSWORD_IS_REQUIRED = FieldNames.PASSWORD + FIELD_IS_REQUIRED;
    public static final String PHONE_NUMBER_IS_REQUIRED = FieldNames.PHONE_NUMBER + FIELD_IS_REQUIRED;
    public static final String EMAIL_IS_REQUIRED = FieldNames.EMAIL + FIELD_IS_REQUIRED;
    public static final String CITY_IS_REQUIRED = FieldNames.CITY + FIELD_IS_REQUIRED;
    public static final String ADDRESS_IS_REQUIRED = FieldNames.ADDRESS + FIELD_IS_REQUIRED;
    public static final String STATE_IS_REQUIRED = FieldNames.STATE + FIELD_IS_REQUIRED;
    public static final String PIN_IS_REQUIRED = FieldNames.PIN + FIELD_IS_REQUIRED;
    public static final String ROLE_IS_REQUIRED = FieldNames.ROLE + FIELD_IS_REQUIRED;
    public static final String CATEGORY_DOES_NOT_EXIST = FieldNames.CATEGORY + FIELD_NOT_EXISTS;
    public static final String MEDICINE_DOES_NOT_EXIST = FieldNames.MEDICINE + FIELD_NOT_EXISTS;
    public static final String INVALID_EXPIRY_DATE = "Expiry date must be after manufacture date.";



    public static final String PHONE_NUMBER_MUST_BE_10_DIGITS = "Phone number must be 10 digits";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String CATEGORY_IN_USE = "Category in use";


    // Authentication / authorization
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String AUTHENTICATION_REQUIRED = "Authentication required to access this resource";
    public static final String ACCOUNT_DISABLED = "Account disabled";

    // JWT
    public static final String JWT_TOKEN_EXPIRED = "Your session has expired. Please log in again.";
    public static final String INVALID_JWT_TOKEN = "Invalid authentication token.";
    public static final String INVALID_JWT_SIGNATURE = "Authentication token signature is invalid.";
    public static final String UNSUPPORTED_JWT_TOKEN = "Authentication token format is not supported.";
    public static final String JWT_TOKEN_EMPTY = "Authentication token is missing or empty.";
}