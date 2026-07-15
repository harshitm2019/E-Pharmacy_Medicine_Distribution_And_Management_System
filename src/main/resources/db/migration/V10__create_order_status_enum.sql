CREATE TYPE order_status_enum AS ENUM
(
    'PENDING',
    'CONFIRMED',
    'PACKED',
    'OUT_FOR_DELIVERY',
    'DELIVERED',
    'CANCELLED'
);