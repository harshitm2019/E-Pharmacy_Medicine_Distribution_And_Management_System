-- User Profile
ALTER TABLE user_profile
ALTER COLUMN created_date TYPE TIMESTAMP,
ALTER COLUMN updated_date TYPE TIMESTAMP;

-- Category
ALTER TABLE category_med
ALTER COLUMN created_date TYPE TIMESTAMP,
ALTER COLUMN updated_date TYPE TIMESTAMP;

-- Medicine
ALTER TABLE medicine
ALTER COLUMN created_date TYPE TIMESTAMP,
ALTER COLUMN updated_date TYPE TIMESTAMP;

-- Prescription
ALTER TABLE prescription
ALTER COLUMN uploaded_date TYPE TIMESTAMP;

-- Orders
ALTER TABLE orders
ALTER COLUMN order_date TYPE TIMESTAMP;

-- Payment
ALTER TABLE payment
ALTER COLUMN paid_date TYPE TIMESTAMP;

-- Delivery Boy
ALTER TABLE delivery_boy
ALTER COLUMN created_date TYPE TIMESTAMP,
ALTER COLUMN updated_date TYPE TIMESTAMP;

-- Delivery Status
ALTER TABLE del_status
ALTER COLUMN assigned_date TYPE TIMESTAMP,
ALTER COLUMN updated_date TYPE TIMESTAMP;

-- Return Order
ALTER TABLE return_order
ALTER COLUMN processed_date TYPE TIMESTAMP;