CREATE TABLE return_order
(
    return_id          SERIAL PRIMARY KEY,
    order_id           INT REFERENCES orders(order_id),
    return_reason      VARCHAR(500) NOT NULL,
    return_status      return_status_enum NOT NULL DEFAULT 'PENDING',
    return_date        DATE NOT NULL,
    processed_date     DATE
);