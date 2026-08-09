CREATE TABLE users
(
    user_id     SERIAL PRIMARY KEY,
    uname       VARCHAR(100) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    phone       VARCHAR(15) UNIQUE NOT NULL,
    role        user_role_enum NOT NULL
);