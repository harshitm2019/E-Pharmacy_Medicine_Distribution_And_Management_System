CREATE TYPE delivery_status_enum AS ENUM
(
    'PENDING',
    'ASSIGNED',
    'PACKED',
    'OUT_FOR_DELIVERY',
    'DELIVERED'
);