-- V13: Convertir payment_method_type de VARCHAR a ENUM en payment_method_transactions
-- Crear tipo ENUM con todos los valores de PaymentMethodType

-- Paso 1: Crear el tipo ENUM payment_method_type
CREATE TYPE payment_method_type AS ENUM (
    'AMERICAN_EXPRESS',
    'CRYPTO',
    'DINERS',
    'CABAL',
    'MAESTRO',
    'MASTER_DEBIT',
    'MASTERCARD',
    'QR_TRANSFER',
    'VISA',
    'VISA_DEBIT',
    'MERCADO_PAGO',
    'BANK_ACCOUNT_TRANSFER'
    );

-- Paso 2: Eliminar temporalmente el constraint único que usa esta columna
ALTER TABLE payment_method_transactions
    DROP CONSTRAINT IF EXISTS uq_payment_method_transactions_type_rendition;

ALTER TABLE payment_method_transactions
    DROP CONSTRAINT IF EXISTS UQ_payment_method_transactions_type_per_rendition;

-- Paso 3: Convertir la columna VARCHAR → ENUM
ALTER TABLE payment_method_transactions
    ALTER COLUMN payment_method_type TYPE payment_method_type
        USING payment_method_type::payment_method_type;

-- Paso 4: Recrear el constraint único
ALTER TABLE payment_method_transactions
    ADD CONSTRAINT UQ_payment_method_transactions_type_per_rendition
        UNIQUE (cash_rendition_id, payment_method_type);
