-- ==========================================
-- ENUM TYPES
-- ==========================================

CREATE TYPE prescription_need AS ENUM (
    'YES',
    'NO'
);

CREATE TYPE medicine_status AS ENUM (
    'ACTIVE',
    'INACTIVE'
);

CREATE TYPE batch_status AS ENUM (
    'ACTIVE',
    'EXHAUSTED',
    'EXPIRED'
);

-- ==========================================
-- MEDICINE
-- ==========================================

CREATE TABLE medicine
(
    med_id              SERIAL PRIMARY KEY,

    med_name            VARCHAR(100) NOT NULL UNIQUE,

    cat_id              INTEGER NOT NULL,

    manufacturer        VARCHAR(100) NOT NULL,

    description         VARCHAR(300) NOT NULL,

    prescription_need   prescription_need NOT NULL,

    status              medicine_status NOT NULL,

    med_img             TEXT,

    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT medicine_cat_id_fkey
        FOREIGN KEY (cat_id)
        REFERENCES category_med(cat_id)
);

-- ==========================================
-- MEDICINE BATCH
-- ==========================================

CREATE TABLE medicine_batch
(
    batch_id            SERIAL PRIMARY KEY,

    med_id              INTEGER NOT NULL,

    batch_no            VARCHAR(100) NOT NULL UNIQUE,

    manufacture_date    DATE NOT NULL,

    expiry_date         DATE NOT NULL,

    price               DECIMAL(10,2) NOT NULL,

    discount            DECIMAL(5,2) NOT NULL,

    stock_qty           INTEGER NOT NULL,

    status              batch_status NOT NULL,

    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT medicine_batch_med_id_fkey
        FOREIGN KEY (med_id)
        REFERENCES medicine(med_id)
        ON DELETE CASCADE,

    CONSTRAINT chk_batch_price
        CHECK (price >= 0),

    CONSTRAINT chk_batch_discount
        CHECK (discount >= 0 AND discount <= 100),

    CONSTRAINT chk_batch_stock
        CHECK (stock_qty >= 0),

    CONSTRAINT chk_batch_dates
        CHECK (expiry_date > manufacture_date)
);

-- ==========================================
-- INDEXES
-- ==========================================

CREATE INDEX idx_medicine_category
    ON medicine(cat_id);

CREATE INDEX idx_batch_medicine
    ON medicine_batch(med_id);

CREATE INDEX idx_batch_status
    ON medicine_batch(status);

CREATE INDEX idx_batch_expiry
    ON medicine_batch(expiry_date);

CREATE INDEX idx_batch_med_status
    ON medicine_batch(med_id, status);

CREATE INDEX idx_batch_med_expiry
    ON medicine_batch(med_id, expiry_date);