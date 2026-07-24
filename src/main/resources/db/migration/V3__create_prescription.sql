-- ==========================================
-- ENUM TYPES
-- ==========================================

CREATE TYPE prescription_status AS ENUM (
    'PENDING',
    'APPROVED',
    'REJECTED'
);

-- ==========================================
-- PRESCRIPTION
-- ==========================================

CREATE TABLE prescription
(
    presc_id            SERIAL PRIMARY KEY,

    user_id             INTEGER NOT NULL,

    file_path           VARCHAR(200) NOT NULL,

    doctor_name         VARCHAR(200) NOT NULL,

    uploaded_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    status              prescription_status NOT NULL,

    CONSTRAINT prescription_user_id_fkey
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- ==========================================
-- INDEXES
-- ==========================================

CREATE INDEX idx_prescription_user
    ON prescription(user_id);

CREATE INDEX idx_prescription_status
    ON prescription(status);

CREATE INDEX idx_prescription_user_status
    ON prescription(user_id, status);