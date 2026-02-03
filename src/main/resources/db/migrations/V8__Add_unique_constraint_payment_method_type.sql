-- V8: Agregar constraint único para payment_method_type + cash_rendition_id en payment_method_transactions
-- Asegura que solo haya una transacción de cada tipo de medio de pago por rendimiento de caja

ALTER TABLE payment_method_transactions
    ADD CONSTRAINT UQ_payment_method_transactions_type_per_rendition UNIQUE (cash_rendition_id, payment_method_type);

