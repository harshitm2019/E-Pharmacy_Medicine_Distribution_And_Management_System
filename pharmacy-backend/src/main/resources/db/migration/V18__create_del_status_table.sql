CREATE TABLE del_status
(
    del_status_id       SERIAL PRIMARY KEY,
    order_id            INT REFERENCES orders(order_id),
    del_boy_id          INT REFERENCES delivery_boy(del_boy_id),
    current_status      delivery_status_enum NOT NULL DEFAULT 'PENDING',
    assigned_date       DATE NOT NULL,
    expected_del_date   DATE NOT NULL,
    updated_date        DATE NOT NULL
);