-- V14: Eliminar DEFAULT gen_random_uuid() de todas las columnas ID
-- Ahora los UUIDs se generarán siempre desde el código de la aplicación

-- Tabla: users
ALTER TABLE users
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: cash_renditions
ALTER TABLE cash_renditions
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: reliefs
ALTER TABLE reliefs
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: credit_notes
ALTER TABLE credit_notes
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: invoice_sales
ALTER TABLE invoice_sales
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: delivery_sales
ALTER TABLE delivery_sales
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: big_box_sales
ALTER TABLE big_box_sales
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: payment_method_transactions
ALTER TABLE payment_method_transactions
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: big_boxes
ALTER TABLE big_boxes
    ALTER COLUMN id DROP DEFAULT;

-- Tabla: exchange_rates
ALTER TABLE exchange_rates
    ALTER COLUMN id DROP DEFAULT;
