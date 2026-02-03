-- V15: Restaurar DEFAULT gen_random_uuid() a todas las columnas ID
-- Volvemos al approach estándar donde la BD genera los UUIDs automáticamente

-- Tabla: users
ALTER TABLE users
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: cash_renditions
ALTER TABLE cash_renditions
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: reliefs
ALTER TABLE reliefs
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: credit_notes
ALTER TABLE credit_notes
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: invoice_sales
ALTER TABLE invoice_sales
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: delivery_sales
ALTER TABLE delivery_sales
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: big_box_sales
ALTER TABLE big_box_sales
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: payment_method_transactions
ALTER TABLE payment_method_transactions
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: big_boxes
ALTER TABLE big_boxes
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabla: exchange_rates
ALTER TABLE exchange_rates
    ALTER COLUMN id SET DEFAULT gen_random_uuid();
