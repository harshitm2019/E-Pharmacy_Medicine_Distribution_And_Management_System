-- Add manufacturer
ALTER TABLE medicine
ADD COLUMN manufacturer VARCHAR(100) NOT NULL;

-- Add discount
ALTER TABLE medicine
ADD COLUMN discount DECIMAL(5,2) NOT NULL DEFAULT 0;

-- Create medicine status enum
CREATE TYPE medicine_status_enum AS ENUM (
    'ACTIVE',
    'INACTIVE'
);

-- Add status
ALTER TABLE medicine
ADD COLUMN status medicine_status_enum NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE order_items
ALTER COLUMN discount TYPE DECIMAL(5,2),
ALTER COLUMN discount SET NOT NULL,
ALTER COLUMN discount SET DEFAULT 0;