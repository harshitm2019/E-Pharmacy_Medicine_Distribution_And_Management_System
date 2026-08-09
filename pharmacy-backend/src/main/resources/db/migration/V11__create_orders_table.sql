CREATE TABLE orders
(
    order_id            SERIAL PRIMARY KEY,
    user_id             INT REFERENCES users(user_id),
    total_amt           DECIMAL(10,2) NOT NULL,
    order_date          DATE NOT NULL,
    presc_id            INT REFERENCES prescription(presc_id),
    order_status        order_status_enum NOT NULL DEFAULT 'PENDING',
    shipping_address    VARCHAR(200) NOT NULL,
    payment_status      order_payment_status_enum NOT NULL DEFAULT 'PENDING'
);