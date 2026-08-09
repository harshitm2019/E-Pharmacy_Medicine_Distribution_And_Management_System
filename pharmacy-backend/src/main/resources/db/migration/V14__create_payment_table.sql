CREATE TABLE payment
(
    payment_id      SERIAL PRIMARY KEY,
    order_id        INT REFERENCES orders(order_id),
    pay_method      pay_method_enum NOT NULL,
    amt             DECIMAL(10,2) NOT NULL,
    pay_status      payment_status_enum NOT NULL,
    paid_date       DATE NOT NULL
);