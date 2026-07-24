-- ==========================================
-- ENUM TYPES
-- ==========================================

CREATE TYPE payment_method AS ENUM (
    'COD',
    'UPI',
    'DEBIT_CARD',
    'CREDIT_CARD'
);

CREATE TYPE payment_status AS ENUM (
    'PAID',
    'FAILED',
    'PENDING'
);

-- ==========================================
-- PAYMENT
-- ==========================================

CREATE TABLE payment
(
    payment_id         SERIAL PRIMARY KEY,

    order_id           INTEGER NOT NULL UNIQUE,

    pay_method         payment_method NOT NULL,

    amt                DECIMAL(10,2) NOT NULL,

    pay_status         payment_status NOT NULL,

    paid_date          TIMESTAMP NOT NULL,

    CONSTRAINT payment_order_id_fkey
        FOREIGN KEY (order_id)
        REFERENCES orders(order_id)
        ON DELETE CASCADE,

    CONSTRAINT chk_payment_amount
        CHECK (amt >= 0)
);

-- ==========================================
-- INDEXES
-- ==========================================

CREATE INDEX idx_payment_status
    ON payment(pay_status);

CREATE INDEX idx_payment_method
    ON payment(pay_method);

CREATE INDEX idx_payment_paid_date
    ON payment(paid_date);