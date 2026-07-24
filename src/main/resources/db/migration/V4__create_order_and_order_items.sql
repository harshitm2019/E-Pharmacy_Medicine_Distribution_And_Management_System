-- ==========================================
-- ENUM TYPES
-- ==========================================

CREATE TYPE order_status AS ENUM (
    'PENDING',
    'CONFIRMED',
    'PACKED',
    'OUT_FOR_DELIVERY',
    'DELIVERED',
    'CANCELLED'
);

CREATE TYPE order_payment_status AS ENUM (
    'PENDING',
    'PAID'
);

-- ==========================================
-- ORDERS
-- ==========================================

CREATE TABLE orders
(
    order_id             SERIAL PRIMARY KEY,

    user_id              INTEGER NOT NULL,

    total_amt            DECIMAL(10,2) NOT NULL,

    order_date           TIMESTAMP NOT NULL,

    presc_id             INTEGER,

    order_status         order_status NOT NULL,

    shipping_address     VARCHAR(200) NOT NULL,

    payment_status       order_payment_status NOT NULL,

    updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT orders_user_id_fkey
        FOREIGN KEY (user_id)
        REFERENCES users(user_id),

    CONSTRAINT orders_presc_id_fkey
        FOREIGN KEY (presc_id)
        REFERENCES prescription(presc_id),

    CONSTRAINT chk_order_total
        CHECK (total_amt >= 0)
);

-- ==========================================
-- ORDER ITEMS
-- ==========================================

CREATE TABLE order_items
(
    order_item_id        SERIAL PRIMARY KEY,

    order_id             INTEGER NOT NULL,

    med_id               INTEGER NOT NULL,

    quantity             INTEGER NOT NULL,

    sub_total            DECIMAL(10,2) NOT NULL,

    discount             DECIMAL(5,2) NOT NULL,

    tax                  DECIMAL(10,2) NOT NULL,

    CONSTRAINT order_items_order_id_fkey
        FOREIGN KEY (order_id)
        REFERENCES orders(order_id)
        ON DELETE CASCADE,

    CONSTRAINT order_items_med_id_fkey
        FOREIGN KEY (med_id)
        REFERENCES medicine(med_id),

        CONSTRAINT uk_order_medicine
            UNIQUE (order_id, med_id),

    CONSTRAINT chk_order_item_quantity
        CHECK (quantity > 0),

    CONSTRAINT chk_order_item_sub_total
        CHECK (sub_total >= 0),

    CONSTRAINT chk_order_item_discount
        CHECK (discount >= 0),

    CONSTRAINT chk_order_item_tax
        CHECK (tax >= 0)
);

-- ==========================================
-- INDEXES
-- ==========================================

CREATE INDEX idx_orders_user
    ON orders(user_id);

CREATE INDEX idx_orders_prescription
    ON orders(presc_id);

CREATE INDEX idx_orders_status
    ON orders(order_status);

CREATE INDEX idx_orders_payment_status
    ON orders(payment_status);

CREATE INDEX idx_orders_order_date
    ON orders(order_date);

CREATE INDEX idx_order_items_order
    ON order_items(order_id);

CREATE INDEX idx_order_items_medicine
    ON order_items(med_id);