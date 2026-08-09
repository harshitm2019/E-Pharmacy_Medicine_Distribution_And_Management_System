CREATE TABLE user_profile
(
    up_id           SERIAL PRIMARY KEY,
    user_id         INT REFERENCES users(user_id),
    address         VARCHAR(100) NOT NULL,
    city            VARCHAR(100) NOT NULL,
    state           VARCHAR(100) NOT NULL,
    pin             VARCHAR(20) NOT NULL,
    created_date    DATE NOT NULL,
    updated_date    DATE NOT NULL
);