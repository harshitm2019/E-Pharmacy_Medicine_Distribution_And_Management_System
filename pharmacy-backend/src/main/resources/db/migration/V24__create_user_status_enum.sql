CREATE TYPE user_status_enum AS ENUM
(
    'ACTIVE',
    'INACTIVE'
);

ALTER TABLE users
    ADD COLUMN status user_status_enum NOT NULL DEFAULT 'ACTIVE';