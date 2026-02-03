-- Add payment_method_transactions table
CREATE TABLE payment_method_transactions
(
    id                  UUID           NOT NULL,
    payment_method_type VARCHAR(50)    NOT NULL,
    amount              DECIMAL(19, 2) NOT NULL,
    cash_rendition_id   UUID           NOT NULL,
    CONSTRAINT pk_payment_method_transactions PRIMARY KEY (id),
    CONSTRAINT fk_payment_method_transactions_cash_rendition
        FOREIGN KEY (cash_rendition_id)
            REFERENCES cash_renditions (id)
            ON DELETE CASCADE,
    CONSTRAINT uq_payment_method_transactions_type_rendition
        UNIQUE (cash_rendition_id, payment_method_type)
);

-- Create index for foreign key
CREATE INDEX idx_payment_method_transactions_cash_rendition_id
    ON payment_method_transactions (cash_rendition_id);

