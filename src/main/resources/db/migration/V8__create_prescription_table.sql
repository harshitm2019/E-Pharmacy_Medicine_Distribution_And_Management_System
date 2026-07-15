CREATE TABLE prescription
(
    presc_id        SERIAL PRIMARY KEY,
    user_id         INT REFERENCES users(user_id),
    file_path       VARCHAR(200) NOT NULL,
    doctor_name     VARCHAR(200) NOT NULL,
    uploaded_date   DATE NOT NULL,
    status          prescription_status_enum NOT NULL DEFAULT 'PENDING'
);