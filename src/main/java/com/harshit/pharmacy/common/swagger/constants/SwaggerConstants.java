package com.harshit.pharmacy.common.swagger.constants;

public class SwaggerConstants {



    private SwaggerConstants() {}

    public static final String BEARER_AUTH = "Bearer Authentication";

    public static final String CATEGORY_TAG = "Category Management";
    public static final String MEDICINE_TAG = "Medicine Management";
    public static final String USER_TAG = "User Management";
    public static final String AUTH_TAG = "Authentication";
    public static final String ADMIN_USER_TAG = "Administrative User Management";

    public static final String USER_TAG_DESCRIPTION =
            "APIs for authenticated users to manage their profile, email and password.";

    public static final String ADMIN_USER_TAG_DESCRIPTION =
            "Administrative APIs for creating, searching and managing system users.";
}
