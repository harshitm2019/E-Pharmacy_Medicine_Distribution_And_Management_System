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
    public static final String OLD_PASSWORD_IS_REQUIRED = "Old password is required.";
    public static final String NEW_PASSWORD_IS_REQUIRED = "New password is required.";
    public static final String CONFIRM_PASSWORD_IS_REQUIRED = "Confirm password is required.";

    public static final String PRESCRIPTION_FILE_REQUIRED = "Prescription file is required.";
    public static final String PRESCRIPTION_FILE_SIZE_EXCEEDED = "Prescription file size must not exceed" ;
    public static final String INVALID_PRESCRIPTION_FILE_TYPE = "Only PDF and JPG files are allowed.";

    public static final String UNABLE_TO_UPLOAD_PRESCRIPTION = "Unable to upload prescription.";
    public static final String UNABLE_TO_DELETE_PRESCRIPTION = "Unable to delete prescription.";
    public static final String CANNOT_MODIFY_APPROVED_PRESCRIPTION = "Cannot modify prescription approved prescription";
    public static final String PRESCRIPTION_NOT_FOUND = "Prescription not found.";
    public static final String ACCESS_DENIED = "You do not have permission to access this resource.";
    public static final String UNABLE_TO_REPLACE_PRESCRIPTION = "Unable to replace prescription.";


    public static final String BATCH_NUMBER_ALREADY_EXISTS = "Batch number already exists.";
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists.";



    public static final String INVALID_PHONE_NUMBER = "Phone number must be Valid Indian Mobile Number.";
    public static final String INVALID_PIN = "Pin code must be exactly 6 digits.";
    public static final String INVALID_EMAIL = "Invalid email";
    public static final String CATEGORY_IN_USE = "Category in use";
    public static final String VALIDATION_FAILED = "Validation failed.";
    public static final String INVALID_PASSWORD = "Invalid password.";
    public static final String USER_DOES_NOT_EXIST = FieldNames.USER + FIELD_NOT_EXISTS;
    public static final String OLD_PASSWORD_INCORRECT = "Old password incorrect.";
    public static final String PASSWORDS_DO_NOT_MATCH = "Passwords do not match.";
    public static final String NEW_PASSWORD_SAME_AS_OLD = "New password same as old.";


    // Authentication / authorization
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