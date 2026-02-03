-- V10: Agregar constraint único para platform + cash_rendition_id en delivery_sales
-- Asegura que solo haya una venta por delivery de cada plataforma por rendimiento de caja

ALTER TABLE delivery_sales
    ADD CONSTRAINT UQ_delivery_sales_platform_per_rendition UNIQUE (cash_rendition_id, platform);

