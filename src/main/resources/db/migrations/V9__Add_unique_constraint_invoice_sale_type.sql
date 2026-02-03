-- V9: Agregar constraint único para type + cash_rendition_id en invoice_sales
-- Asegura que solo haya una venta con factura de cada tipo por rendimiento de caja

ALTER TABLE invoice_sales
    ADD CONSTRAINT UQ_invoice_sales_type_per_rendition UNIQUE (cash_rendition_id, type);

