-- ==========================================
-- ENUM TYPES
-- ==========================================

CREATE TYPE user_role AS ENUM (
    'ADMIN',
    'CUSTOMER',
    'DELIVERY_BOY'
);

CREATE TYPE user_status AS ENUM (
    'ACTIVE',
    'INACTIVE'
);

-- ==========================================
-- USERS
-- ==========================================

CREATE TABLE users
(
    user_id     SERIAL PRIMARY KEY,

    uname       VARCHAR(100) NOT NULL,

    password    VARCHAR(255) NOT NULL,

    email       VARCHAR(100) NOT NULL UNIQUE,

    phone       VARCHAR(15) NOT NULL UNIQUE,

    role        user_role NOT NULL,

    status      user_status NOT NULL
);

-- ==========================================
-- USER PROFILE
-- ==========================================

CREATE TABLE user_profile
(
    up_id           SERIAL PRIMARY KEY,

    user_id         INTEGER NOT NULL UNIQUE,

    address         TEXT NOT NULL,

    city            VARCHAR(100) NOT NULL,

    state           VARCHAR(100) NOT NULL,

    pin             VARCHAR(20) NOT NULL,

    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT user_profile_user_id_fkey
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- ==========================================
-- CATEGORY
-- ==========================================

CREATE TABLE category_med
(
    cat_id          SERIAL PRIMARY KEY,

    cat_name        VARCHAR(100) NOT NULL UNIQUE,

    description     VARCHAR(500) NOT NULL,

    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- INDEXES
-- ==========================================

CREATE INDEX idx_users_email
    ON users(email);

CREATE INDEX idx_users_phone
    ON users(phone);

CREATE INDEX idx_user_profile_user_id
    ON user_profile(user_id);

CREATE INDEX idx_category_name
    ON category_med(cat_name);