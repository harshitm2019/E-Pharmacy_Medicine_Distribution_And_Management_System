CREATE TABLE order_items
(
    order_item_id       SERIAL PRIMARY KEY,
    order_id            INT REFERENCES orders(order_id),
    med_id              INT REFERENCES medicine(med_id),
    quantity            INT NOT NULL,
    sub_total           DECIMAL(10,2) NOT NULL,
    discount            DECIMAL(10,2) DEFAULT 0,
    tax                 DECIMAL(10,2) NOT NULL
);