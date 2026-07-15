CREATE TABLE delivery_boy
(
    del_boy_id      SERIAL PRIMARY KEY,
    user_id         INT REFERENCES users(user_id),
    vehicle_no      VARCHAR(100) NOT NULL,
    status          delivery_boy_status_enum NOT NULL DEFAULT 'ACTIVE',
    created_date    DATE NOT NULL,
    updated_date    DATE NOT NULL
);