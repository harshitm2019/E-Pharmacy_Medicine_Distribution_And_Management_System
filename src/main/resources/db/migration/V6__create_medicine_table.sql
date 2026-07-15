CREATE TABLE medicine
(
    med_id              SERIAL PRIMARY KEY,
    med_name            VARCHAR(100) NOT NULL,
    cat_id              INT REFERENCES category_med(cat_id),
    manufacture         DATE NOT NULL,
    batch_no            VARCHAR(100) NOT NULL,
    price               DECIMAL(10,2) NOT NULL,
    stock_qty           INT NOT NULL,
    description         VARCHAR(300) NOT NULL,
    prescription_need   prescription_need_enum NOT NULL DEFAULT 'NO',
    created_date        DATE NOT NULL,
    updated_date        DATE NOT NULL,
    med_img             VARCHAR(100) NOT NULL,
    expiry_date         DATE NOT NULL
);