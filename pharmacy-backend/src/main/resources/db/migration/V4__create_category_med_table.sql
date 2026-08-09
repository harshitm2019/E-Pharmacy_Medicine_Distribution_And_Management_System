CREATE TABLE category_med
(
    cat_id          SERIAL PRIMARY KEY,
    cat_name        VARCHAR(100) NOT NULL,
    description     VARCHAR(500) NOT NULL,
    created_date    DATE NOT NULL,
    updated_date    DATE NOT NULL
);