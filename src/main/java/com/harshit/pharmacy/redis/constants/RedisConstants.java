package com.harshit.pharmacy.redis.constants;

public class RedisConstants {



    private RedisConstants() {}

    public static final String STOCK_KEY = "stock:";

    public static final String RESERVATION_KEY = "reservation:";

    public static final Long RESERVATION_TTL = 15L;

    public static final String ACTIVE_RESERVATIONS_KEY = "active_reservations";

    public static final String STOCK_RESERVED_MESSAGE = "Stock Reserved Successfully!";

}
